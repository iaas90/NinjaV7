package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage{
	
	//Constructor
		public LoginPage(WebDriver driver)
		{
			super(driver);
		}
	
	//locator
		//Add email
		@FindBy(xpath = "//input[@name='email']")
		WebElement txt_Email;
			
		//Add password
		@FindBy(xpath = "//input[@name='password']")
		WebElement txt_Password;
		
		// Click Login button
		@FindBy(xpath = "//button[@class='btn btn-primary']")
		WebElement btn_Login;
				
	
	//Action methods
		//Action methods
		public void sendEmail(String email) {//public because i want to use it in all my project and void, because nothing is returned
			txt_Email.sendKeys(email);
		}
			
		public void sendPassword(String pwd) {
			txt_Password.sendKeys(pwd);
		}
		
		public void clickLogin() {
			btn_Login.click();
		}
}
