package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import pageObjects.CategoryPage;
import pageObjects.ProductPage;
import testBase.BaseClass;
import utilities.RetryAnalyzer;   // implements IRetryAnalyzer

public class TC03_AddToCart extends BaseClass {

    // Logger instance for this test class
    private static final Logger logger = LogManager.getLogger(TC03_AddToCart.class);

    /**
     * Verifies that a laptop product (HP) can be added to the cart successfully.
     * Uses Log4j logging, debug statements, assertion try-catch, and RetryAnalyzer.
     */
    @Test(
        groups = {"sanity", "regression"},
        retryAnalyzer = utilities.RetryAnalyzer.class
    )
    public void testAddToCart() throws InterruptedException {
        logger.info("==== Starting testAddToCart ====");
        logger.debug("Thread ID: {}", Thread.currentThread().getId());

        CategoryPage cp = null;
        ProductPage pp = null;

        try {
            // Step 1: Navigate to Laptops & Notebooks category
            logger.debug("Creating CategoryPage object");
            cp = new CategoryPage(getDriver());

            logger.debug("Clicking on 'Laptops & Notebooks' category");
            cp.clickLaptopsAndNotebooks();

            logger.debug("Clicking on 'Show All' under Laptops & Notebooks");
            cp.clickShowAll();

            // Small sleep (ideally replaced with explicit wait in page object)
            logger.debug("Waiting briefly for product list to load");
            Thread.sleep(500);

            logger.debug("Selecting HP product from category listing");
            cp.selectHPProduct();

            // Step 2: On Product page, set options and add to cart
            logger.debug("Creating ProductPage object");
            pp = new ProductPage(getDriver());

            logger.debug("Setting delivery date for selected product");
            pp.setDeliveryDate();

            logger.debug("Clicking 'Add to Cart' button");
            pp.clickAddToCart();

            // Step 3: Verify success message after adding to cart
            logger.debug("Checking if success message is displayed after adding to cart");
            boolean success = pp.isSuccessMessageDisplayed();
            logger.info("Add to cart success flag: {}", success);

            try {
                logger.debug("Performing assertion on success message visibility");
                Assert.assertTrue(success, "Add to Cart Failed!");
                logger.info("Assertion passed: Product successfully added to cart");
            } catch (AssertionError ae) {
                logger.error("Assertion failed in testAddToCart - success message not displayed", ae);
                // Rethrow so TestNG marks the test as failed and RetryAnalyzer can retry
                throw ae;
            }

        } catch (Exception e) {
            // Catch any unexpected runtime exceptions during the flow
            logger.error("Unexpected exception in testAddToCart", e);
            throw e;  // rethrow so the test is recorded as failed and retried if configured

        } finally {
            logger.info("==== Finished testAddToCart ====");
        }
    }
}
