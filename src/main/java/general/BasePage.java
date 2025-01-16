package general;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

public abstract class BasePage {

    protected IOSDriver driver;
    private final WebDriverWait wait;
    private final int staticTimeOut;

    // Constructor
    public BasePage(IOSDriver driver) {
        this.driver = driver;
        this.staticTimeOut = MobileDriverManager.getStaticTime();
        int dynamicTimeOut = MobileDriverManager.getDynamicTime();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(dynamicTimeOut));
    }

    /****** GENERIC METHODS ******/

    public abstract boolean verifyLoads();

    protected boolean waitForElementToBeVisible(WebElement element) {
        boolean flag = false;
        try {
            flag = wait.until(ExpectedConditions.visibilityOf(element)) != null;
        } catch (NoSuchElementException | StaleElementReferenceException e) {
            ErrorHandler.handle(e);
        } catch (Exception e) {
            ErrorHandler.handle(e); // Handle other types of exceptions
        }
        return flag;
    }

    protected boolean waitForElementToBeClickable(WebElement element) {
        boolean flag = false;
        try {
            flag = wait.until(ExpectedConditions.elementToBeClickable(element)) != null;
        } catch (Exception e) {
            ErrorHandler.handle(e);
        }
        return flag;
    }

    protected boolean tapElement(WebElement element) {
        boolean flag = false;
        try {
            flag = waitForElementToBeVisible(element) && waitForElementToBeClickable(element) &&
                    wait.until(arg0 -> {
                        element.click();
                        return true;
                    }) != null;
        } catch (Exception e) {
            ErrorHandler.handle(e);
        }
        return flag;
    }

    protected boolean sendTextOnCleanElement(WebElement element, String txt) {
        boolean validationReturn = false;
        try {
            if (waitForElementToBeClickable(element)) {
                element.click();
                element.clear();
                validationReturn = typeOnTxtElement(element, txt);
            }
        } catch (Exception e) {
            ErrorHandler.handle(e);
        }
        return validationReturn;
    }

    private boolean typeOnTxtElement(WebElement element, String txt) {
        try {
            element.sendKeys(txt);
            return element.isEnabled();
        } catch (Exception e) {
            ErrorHandler.handle(e);
        }
        return false;
    }

    protected boolean verifyTextOnElement(WebElement element, String text) {
        boolean flag = false;
        try {
            flag = waitForElementToBeVisible(element) && wait.until(arg0 -> element.getText().contains(text)) != null;
        } catch (Exception e) {
            ErrorHandler.handle(e);
        }
        return flag;
    }

    protected boolean implicityWaitTimeOnScreen() {
        try {
            TimeUnit.SECONDS.sleep(this.staticTimeOut);
            return true;
        } catch (Exception e) {
            ErrorHandler.handle(e);
        }
        return false;
    }

    protected String getTextFromElement(WebElement element) {
        String text = "";
        try {
            if (waitForElementToBeVisible(element)) {
                text = element.getText();
            }
        } catch (Exception e) {
            ErrorHandler.handle(e);
        }
        return text;
    }
}