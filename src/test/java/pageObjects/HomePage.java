package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends BasePage{
	
	
	//Constructor
		public HomePage(WebDriver driver)
		{
			super(driver);
		}
	
	//locator
		// Link - My Account
		@FindBy(xpath = "//i[@class='fa-solid fa-user']")
		WebElement link_MyAccount;
	
		// Link - Login
		@FindBy(xpath = "//a[normalize-space()='Login']")
		WebElement link_Login;
	
	
	//Action methods
		public void clickMyAccount() {//public because i want to use it in all my project and void, because nothing is returned
			link_MyAccount.click();
		}
	
		public void goToLogin() {
			link_Login.click();
		}

}
