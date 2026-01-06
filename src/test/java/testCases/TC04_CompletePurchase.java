package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import pageObjects.CategoryPage;
import pageObjects.CheckoutPage;
import pageObjects.ConfirmationPage;
import pageObjects.LoginPage;
import pageObjects.ProductPage;
import testBase.BaseClass;
import utilities.RetryAnalyzer;   // make sure this implements IRetryAnalyzer

public class TC04_CompletePurchase extends BaseClass {

    // Logger instance for this test class
    private static final Logger logger = LogManager.getLogger(TC04_CompletePurchase.class);

    /**
     * End‑to‑end test: add a laptop to cart, log in during checkout,
     * complete the purchase, and verify order placement.
     * Uses Log4j logging, debug statements, assertion try‑catch, and RetryAnalyzer.
     */
    @Test(
        groups = {"sanity", "regression"},
        retryAnalyzer = utilities.RetryAnalyzer.class
    )
    public void testAddToCart() throws InterruptedException {
        logger.info("==== Starting TC04_CompletePurchase.testAddToCart ====");
        logger.debug("Thread ID: {}", Thread.currentThread().getId());

        CategoryPage cp = null;
        ProductPage pp = null;
        CheckoutPage cop = null;
        LoginPage lp = null;
        ConfirmationPage confirmationPage = null;

        try {
            // Step 1: Navigate to Laptops & Notebooks and select HP product
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

            // Step 2: On Product page, set delivery date, add to cart, and go to checkout
            logger.debug("Creating ProductPage object");
            pp = new ProductPage(getDriver());

            logger.debug("Setting delivery date for selected product");
            pp.setDeliveryDate();

            logger.debug("Clicking 'Add to Cart' button");
            pp.clickAddToCart();

            logger.debug("Clicking 'Checkout' button");
            pp.clickCheckout();

            // Step 3: On Checkout page, choose login option
            logger.debug("Creating CheckoutPage object");
            cop = new CheckoutPage(getDriver());

            logger.debug("Clicking on 'Login' option at Checkout");
            cop.clickLogin();

            // Step 4: Perform login from Checkout
            logger.debug("Creating LoginPage object");
            lp = new LoginPage(getDriver());

            String email = "ivanarno90@gmail.com";
            String password = "Welcome@333";
            logger.debug("Entering checkout login credentials email: {}", email);

            lp.sendEmail(email);
            lp.sendPassword(password);

            logger.debug("Clicking 'Login' button on Checkout login");
            lp.clickLogin();

            // Step 5: Complete checkout flow
            logger.debug("Completing checkout steps");
            cop.completeCheckout();

            // Step 6: Verify confirmation that order was placed
            logger.debug("Creating ConfirmationPage object");
            confirmationPage = new ConfirmationPage(getDriver());

            logger.debug("Checking if order placed confirmation is displayed");
            boolean isOrderPlaced = confirmationPage.isOrderPlaced();
            logger.info("Order placed flag: {}", isOrderPlaced);

            // Assertion wrapped in try-catch to log failures clearly
            try {
                logger.debug("Performing assertion on order placement");
                Assert.assertTrue(isOrderPlaced, "Order placement failed!");
                logger.info("Assertion passed: Order placed successfully");
            } catch (AssertionError ae) {
                logger.error("Assertion failed in TC04_CompletePurchase - order not placed", ae);
                // Rethrow so TestNG marks the test as failed and RetryAnalyzer can retry
                throw ae;
            }

        } catch (Exception e) {
            // Catch any unexpected runtime exceptions during the flow
            logger.error("Unexpected exception in TC04_CompletePurchase.testAddToCart", e);
            throw e;  // rethrow so the test is recorded as failed and retried if configured

        } finally {
            logger.info("==== Finished TC04_CompletePurchase.testAddToCart ====");
        }
    }
}
