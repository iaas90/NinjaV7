package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import pageObjects.AffiliatePage;
import pageObjects.HomePage;
import pageObjects.LoginPage;
import testBase.BaseClass;
import utilities.RetryAnalyzer;   // implements IRetryAnalyzer

public class TC06_AddAffiliate extends BaseClass {

    // Logger instance for this test class
    private static final Logger logger = LogManager.getLogger(TC06_AddAffiliate.class);

    /**
     * Logs in and adds affiliate details, then verifies that the affiliate
     * information was added successfully.
     * Uses Log4j logging, debug statements, assertion try-catch, and RetryAnalyzer.
     */
    @Test(
        groups = {"regression"},
        retryAnalyzer = utilities.RetryAnalyzer.class
    )
    void testAddAffiliate() throws InterruptedException {
        logger.info("==== Starting TC06_AddAffiliate.testAddAffiliate ====");
        logger.debug("Thread ID: {}", Thread.currentThread().getId());

        HomePage hp = null;
        LoginPage lp = null;
        AffiliatePage ap = null;

        try {
            // Step 1: Navigate to Login from Home page
            logger.debug("Creating HomePage object");
            hp = new HomePage(getDriver());

            logger.debug("Clicking on 'My Account' on Home page");
            hp.clickMyAccount();

            logger.debug("Navigating to Login page from Home page");
            hp.goToLogin();

            // Step 2: Perform login
            logger.debug("Creating LoginPage object");
            lp = new LoginPage(getDriver());

            String email = "sid@cloudberry.services";
            String password = "Test123";
            logger.debug("Entering login credentials, email: {}", email);

            lp.sendEmail(email);
            lp.sendPassword(password);

            logger.debug("Clicking 'Login' button");
            lp.clickLogin();

            // Step 3: Navigate to Affiliate page and fill form
            logger.debug("Creating AffiliatePage object");
            ap = new AffiliatePage(getDriver());

            logger.debug("Navigating to affiliate registration/update form");
            ap.navigateToAffiliateForm();

            String company = "CloudBerry";
            String website = "cloudberry.services";
            String taxId = "123456";
            String chequePayeeName = "Shadab Siddiqui";
            logger.debug("Filling affiliate details: company={}, website={}, taxId={}, payee={}",
                         company, website, taxId, chequePayeeName);

            ap.fillAffiliateDetails(company, website, taxId, chequePayeeName);

            // Step 4: Verify affiliate added
            logger.debug("Checking if affiliate details were added successfully");
            boolean affiliateAdded = ap.isAffiliateAdded();
            logger.info("Affiliate added flag: {}", affiliateAdded);

            // Assertion wrapped in try-catch for better logging
            try {
                logger.debug("Performing assertion on affiliate added flag");
                Assert.assertTrue(affiliateAdded, "Affiliate details not added successfully.");
                logger.info("Assertion passed: Affiliate details added successfully");
            } catch (AssertionError ae) {
                logger.error("Assertion failed in TC06_AddAffiliate - affiliate details not added", ae);
                // Rethrow so TestNG marks the test as failed and RetryAnalyzer can retry
                throw ae;
            }

        } catch (Exception e) {
            // Catch unexpected runtime exceptions in the test flow
            logger.error("Unexpected exception in TC06_AddAffiliate.testAddAffiliate", e);
            throw e;

        } finally {
            logger.info("==== Finished TC06_AddAffiliate.testAddAffiliate ====");
        }
    }
}
