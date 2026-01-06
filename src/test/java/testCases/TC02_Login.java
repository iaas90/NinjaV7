package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import pageObjects.AccountPage;
import pageObjects.HomePage;
import pageObjects.LoginPage;
import testBase.BaseClass;
import utilities.DataProviders;
import utilities.RetryAnalyzer;   // make sure this exists and implements IRetryAnalyzer

public class TC02_Login extends BaseClass {

    // Logger instance for this test class
    private static final Logger logger = LogManager.getLogger(TC02_Login.class);

    /**
     * Data-driven login test using credentials from DataProviders.LoginData.
     * Includes Log4j logging, debug statements, try-catch for assertion logic,
     * and RetryAnalyzer to rerun failed iterations.
     */
    @Test(
        groups = {"sanity", "regression", "datadriven"},
        dataProvider = "LoginData",
        dataProviderClass = DataProviders.class,
        retryAnalyzer = utilities.RetryAnalyzer.class
    )
    public void testLogin(String email, String pwd) {

        logger.info("==== Starting testLogin for email: {} ====", email);
        logger.debug("Thread ID: {}", Thread.currentThread().getId());
        logger.debug("Input data -> email: {}, password: {}", email, pwd);

        HomePage hp = null;
        LoginPage lp = null;
        AccountPage ap = null;
        boolean status = false;

        try {
            // Initialize HomePage object to access its elements and actions
            logger.debug("Creating HomePage object");
            hp = new HomePage(getDriver());

            // Navigate to Login page from Home page
            logger.debug("Clicking on My Account menu");
            hp.clickMyAccount();

            logger.debug("Navigating to Login page");
            hp.goToLogin();

            // Initialize LoginPage object to perform login actions
            logger.debug("Creating LoginPage object");
            lp = new LoginPage(getDriver());

            logger.debug("Entering email: {}", email);
            lp.sendEmail(email);

            logger.debug("Entering password for email: {}", email);
            lp.sendPassword(pwd);

            logger.debug("Clicking Login button");
            lp.clickLogin();

            // Initialize AccountPage to verify successful login
            logger.debug("Creating AccountPage object");
            ap = new AccountPage(getDriver());

            logger.debug("Checking if My Account confirmation element is displayed");
            status = ap.getMyAccountConfirmation().isDisplayed();
            logger.info("Login status for email {} -> {}", email, status);

            // Assertion and logout logic wrapped in try-catch to log assertion failures
            try {
                if (status) {
                    logger.info("Login successful for email: {}", email);
                    logger.debug("Performing logout steps after successful login");

                    ap.clickMyAccountDropDown();
                    logger.debug("Clicked My Account dropdown");

                    ap.clickLogout();
                    logger.debug("Clicked Logout");

                    // Assert true when login is successful
                    Assert.assertTrue(status, "Expected login to be successful, but status is false");
                    logger.info("Assertion passed for successful login for email: {}", email);
                } else {
                    logger.warn("Login failed for email: {}", email);
                    // Force a failure when login status is false
                    Assert.fail("Login was expected to be successful, but My Account confirmation is not displayed");
                }
            } catch (AssertionError ae) {
                logger.error("Assertion failed in testLogin for email: {}", email, ae);
                // Rethrow to ensure TestNG marks this iteration as failed and RetryAnalyzer can retry
                throw ae;
            }

        } catch (Exception e) {
            // Catch any unexpected runtime exceptions in the test flow
            logger.error("Unexpected exception in testLogin for email: {}", email, e);
            throw e;  // rethrow so TestNG records the failure and triggers retry

        } finally {
            logger.info("==== Finished testLogin for email: {} ====", email);
        }
    }
}
