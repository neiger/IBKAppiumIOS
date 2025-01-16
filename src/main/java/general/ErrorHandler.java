package general;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriverException;
import org.testng.Reporter;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ErrorHandler {

    private static final Logger logger = LogManager.getLogger(ErrorHandler.class);

    public static void handle(Exception exception) {
        String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        String errorMessage = timestamp + " - Error: " + exception.getMessage();

        logger.error(errorMessage, exception);  // Logs both the error message and stack trace

        // Handle specific exception types
        if (exception instanceof NoSuchElementException) {
            String message = "Element not found. Please check your locators.";
            logger.warn(message);  // Log warning
            Reporter.log(message);
        } else if (exception instanceof TimeoutException) {
            String message = "Operation timed out. Retrying...";
            logger.warn(message);  // Log warning
            Reporter.log(message);
        } else if (exception instanceof WebDriverException) {
            String message = "WebDriverException occurred.";
            logger.error(message);  // Log error
            Reporter.log(message);
        } else {
            String message = "Unknown error occurred.";
            logger.error(message);  // Log error
            Reporter.log(message);
        }
    }

    // Uncomment and customize these methods if needed

    // Retry logic on failure (could be further customized)
    // private static void retryOnFailure() {
    //     logger.warn("Retrying the operation...");  // Log warning
    //     Reporter.log("Retrying the operation...");
    // }

    // Example function for restarting the WebDriver (customize as per your needs)
    // private static void restartDriver() {
    //     logger.info("Restarting WebDriver...");  // Log info level message
    //     Reporter.log("Restarting WebDriver...");
    //     // Implement WebDriver restart logic
    // }

    // Example function for alerting admin about the failure
    // private static void alertAdmin() {
    //     logger.error("Sending failure alert to admin...");  // Log error
    //     Reporter.log("Sending failure alert to admin...");
    //     // You can integrate an email API or alerting service here
    // }
}