package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import pageObjects.HomePage;
import testBase.BaseClass;
import utilities.RetryAnalyzer;  // make sure this exists and implements IRetryAnalyzer

public class TC01_LaunchApplication extends BaseClass {

    // Logger for this specific test class (TC01)
    private static final Logger logger = LogManager.getLogger(TC01_LaunchApplication.class);

    /**
     * Basic smoke test for application launch:
     * 1) Opens the application (done in BaseClass @BeforeClass)
     * 2) Verifies that the page title matches the expected value
     * 3) Uses RetryAnalyzer to re-run on flaky failures
     */
    @Test(
        groups = {"sanity", "regression"},
        retryAnalyzer = utilities.RetryAnalyzer.class
    )
    public void testLaunchApplication() {
        // High-level log indicating start of the test
        logger.info("==== Starting testLaunchApplication ====");
        // Helpful for debugging parallel runs
        logger.debug("Thread ID: {}", Thread.currentThread().getId());

        HomePage hp = null;
        String title = null;

        try {
            // Initialize HomePage object; this also validates that the driver
            // session is active and the Home page is reachable
            logger.debug("Initializing HomePage object");
            hp = new HomePage(getDriver());

            // Get the current page title to validate application launch
            logger.debug("Fetching page title from current driver");
            title = getDriver().getTitle();
            logger.info("Actual page title: '{}'", title);

            // Expected title for the application home page
            String expectedTitle = "Your store of fun";
            logger.debug("Expected page title: '{}'", expectedTitle);

            // Wrap assertion in try-catch so failures are logged clearly
            try {
                logger.debug("Performing assertion on page title");
                Assert.assertEquals(title, expectedTitle, "Page title mismatch");
                logger.info("Title assertion passed");
            } catch (AssertionError ae) {
                // Detailed log on assertion failure with both expected and actual
                logger.error("Title assertion failed. Expected='{}', Actual='{}'",
                             expectedTitle, title, ae);
                // Rethrow so that TestNG marks the test as failed and RetryAnalyzer can retry
                throw ae;
            }

        } catch (Exception e) {
            // Catch any unexpected runtime issues (e.g., driver problems, page not loaded)
            logger.error("Unexpected exception in testLaunchApplication", e);
            // Rethrow so the test is still reported as failed and eligible for retry
            throw e;
        } finally {
            // Always log the end of the test, regardless of pass/fail
            logger.info("==== Finished testLaunchApplication ====");
        }
    }
}
