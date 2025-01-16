package tests;

import general.MobileDriverManager;
import org.testng.annotations.Test;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ExampleTest extends MobileDriverManager {

    private static final Logger logger = LogManager.getLogger(ExampleTest.class);

    @Test
    public void testMethod() {
        logger.info("Starting the test method");
        System.out.println("Just launching the test and the Apple Maps app");
    }
}