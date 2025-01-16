package general;

import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.options.XCUITestOptions;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.net.URL;

public class MobileDriverManager extends TestListenerAdapter {

    private final static ThreadLocal<IOSDriver> mobAppiumDriver = new ThreadLocal<>();
    private static int staticTime;
    private static int dynamicTime;

    // Logger for better logging
    private static final Logger logger = LogManager.getLogger(MobileDriverManager.class);

    public static int getStaticTime() {
        return staticTime;
    }

    public static int getDynamicTime() {
        return dynamicTime;
    }

    @Parameters({"tmStatic", "tmDynamic"})
    @BeforeMethod
    public void setMobDriverTimes(int tmStatic, int tmDynamic) {
        staticTime = tmStatic;
        dynamicTime = tmDynamic;
        logger.info("Driver timeouts set: Static = {}, Dynamic = {}", staticTime, dynamicTime);
    }

    @Parameters({"platformName", "platformVersion", "deviceName", "automationName", "appBundle", "deviceudid", "noReset", "appiumServer"})
    @BeforeMethod(alwaysRun = true)
    public final void setDriver(String platformName, String platformVersion, String deviceName,
                                String automationName, String appBundle, String deviceudid,
                                String noReset, String appiumServer) throws Exception {

        logger.info("[DRIVER MSG] ---- The mobile test driver is being initialized now");

        if (platformName == null || platformName.isEmpty()) {
            throw new IllegalArgumentException("Platform name must be specified.");
        }

        try {
            // XCUITestOptions to set capabilities for iOS
            XCUITestOptions options = new XCUITestOptions();
            options.setPlatformName(platformName);
            options.setPlatformVersion(platformVersion);
            options.setDeviceName(deviceName);
            options.setAutomationName(automationName);
            options.setApp(appBundle);
            options.setUdid(deviceudid);
            options.setNoReset(Boolean.parseBoolean(noReset));

            // Initialize iOS driver with Appium server
            mobAppiumDriver.set(new IOSDriver(new URL(appiumServer), options));
            logger.info("[DRIVER MSG] ---- iOS Driver initialized successfully.");

        } catch (Exception e) {
            logger.error("[DRIVER ERROR] ---- Error initializing iOS driver: {}", e.getMessage(), e);
            throw e; // Rethrow the exception after logging it
        }
    }

    // Get the driver instance for use in tests
    public IOSDriver getDriver() {
        return mobAppiumDriver.get();
    }

    @AfterMethod(alwaysRun = true)
    public void deleteDriver() {
        try {
            logger.info("[DRIVER MSG] ---- Closing the iOS driver now.");
            if (getDriver() != null) {
                getDriver().quit();
                logger.info("[DRIVER MSG] ---- iOS driver closed successfully.");
            }
        } catch (Exception e) {
            logger.error("[DRIVER ERROR] ---- Error while quitting iOS driver: " + e.getMessage(), e);
        }
    }

    @Override
    public void onTestFailure(ITestResult iTestResult) {
        iTestResult.setStatus(ITestResult.FAILURE); // Mark the test status as failed
        logger.error("The test failed: {}", iTestResult.getName());
    }
}