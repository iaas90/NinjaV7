package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AccountPage extends BasePage{
	
	//Constructor
		public AccountPage(WebDriver driver)
		{
			super(driver);
		}
			
	//locator
		@FindBy(xpath = "//h1[normalize-space()='My Account']")
		WebElement confirmationText_MyAccount;
		
		@FindBy(xpath = "//li[@class='list-inline-item']//i[@class='fa-solid fa-caret-down']")
		WebElement dropDown_MyAccount;
		
		@FindBy(xpath = "//a[@class='dropdown-item'][normalize-space()='Logout']")
		WebElement btn_Logout;

	//Action methods
		public WebElement getMyAccountConfirmation() {
			return confirmationText_MyAccount;
		}	
		
		public void clickMyAccountDropDown() {
			dropDown_MyAccount.click();
		}
		
		public void clickLogout() {
			btn_Logout.click();
		}	
}
