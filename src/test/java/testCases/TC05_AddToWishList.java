package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import pageObjects.CategoryPage;
import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.ProductPage;
import testBase.BaseClass;
import utilities.RetryAnalyzer;   // implements IRetryAnalyzer

public class TC05_AddToWishList extends BaseClass {

    // Logger instance for this test class
    private static final Logger logger = LogManager.getLogger(TC05_AddToWishList.class);

    /**
     * Logs in, navigates to a laptop product, adds it to wishlist,
     * and verifies that the wishlist success message is shown.
     * Uses Log4j logging, debug statements, assertion try‑catch, and RetryAnalyzer.
     */
    @Test(
        groups = {"regression"},
        retryAnalyzer = utilities.RetryAnalyzer.class
    )
    void testAddToWishList() throws InterruptedException {
        logger.info("==== Starting TC05_AddToWishList.testAddToWishList ====");
        logger.debug("Thread ID: {}", Thread.currentThread().getId());

        HomePage hp = null;
        LoginPage lp = null;
        CategoryPage cp = null;
        ProductPage pp = null;

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
            logger.debug("Entering login credentials email: {}", email);

            lp.sendEmail(email);
            lp.sendPassword(password);

            logger.debug("Clicking 'Login' button");
            lp.clickLogin();

            // Step 3: Navigate to product via category
            logger.debug("Creating CategoryPage object");
            cp = new CategoryPage(getDriver());

            logger.debug("Clicking on 'Laptops & Notebooks' category");
            cp.clickLaptopsAndNotebooks();

            logger.debug("Clicking on 'Show All' under Laptops & Notebooks");
            cp.clickShowAll();

            logger.debug("Waiting briefly for product list to load");
            Thread.sleep(500);   // consider replacing with explicit wait in page object

            logger.debug("Selecting HP product from category listing");
            cp.selectHPProduct();

            // Step 4: Add selected product to wishlist
            logger.debug("Creating ProductPage object");
            pp = new ProductPage(getDriver());

            logger.debug("Clicking 'Add to Wishlist' button");
            pp.addToWishlist();

            // Step 5: Verify wishlist success message
            logger.debug("Checking if wishlist success message is displayed");
            boolean success = pp.isSuccessMessageDisplayed();
            logger.info("Wishlist success flag: {}", success);

            // Assertion wrapped in try-catch to log failure clearly
            try {
                logger.debug("Performing assertion on wishlist success message visibility");
                Assert.assertTrue(success, "Wishlist message not shown.");
                logger.info("Assertion passed: Wishlist success message is displayed");
            } catch (AssertionError ae) {
                logger.error("Assertion failed in TC05_AddToWishList - wishlist success message not displayed", ae);
                // Rethrow so TestNG marks the test as failed and RetryAnalyzer can retry
                throw ae;
            }

        } catch (Exception e) {
            // Catch any unexpected runtime exceptions during the flow
            logger.error("Unexpected exception in TC05_AddToWishList.testAddToWishList", e);
            throw e;  // rethrow so the test is recorded as failed and retried if configured

        } finally {
            logger.info("==== Finished TC05_AddToWishList.testAddToWishList ====");
        }
    }
}
