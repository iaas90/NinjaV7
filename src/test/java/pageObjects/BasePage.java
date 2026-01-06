package pageObjects;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePage {

	WebDriver driver;
	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	//this is the constructor for the class
	//It takes a webdriver as an argument, which is used to interact with the browser
	public BasePage(WebDriver driver) {
		this.driver = driver;
		
		//The passed driver is assigned to the instance variable driver. This allows
		//the class and its subclass to use it for browser interactions
		
		
		PageFactory.initElements(driver, this);
		//The above line initializes the web element defined in the class using
		//Selenium PageFactory
		//Pagefactory.initElements() tells  Selenium to scan the current class (this)
		//for any @findBy annotations
		//and connect them to actual elements on the page using the provided driver
	}
}
