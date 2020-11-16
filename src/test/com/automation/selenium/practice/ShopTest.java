package com.automation.selenium.practice;

import static org.junit.Assert.assertEquals;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By.ByXPath;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Select;

public class ShopTest {

	private static final DesiredCapabilities CAPABILITY = DesiredCapabilities.firefox();

	private static final String SELENIUM_SERVER_URL = "http://127.0.0.1:4444/wd/hub";

	private static final String BASIC_URL = "http://automationpractice.com/index.php";

	private WebDriver mDriver = null;
	
	@Before
	public void setup() throws MalformedURLException, InterruptedException {
		// Create a new instance of the driver
		mDriver = new RemoteWebDriver(new URL(SELENIUM_SERVER_URL), CAPABILITY);

		// And now use this to open base url
		mDriver.navigate().to(BASIC_URL);

		// Ask to the driver to wait for 3s when an element is not found
		mDriver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		mDriver.manage().window().maximize();
	}
	
	@Test
	public void initTest() throws InterruptedException {
		//Cliquer bouton Sign In
		WebElement vSignInButton = mDriver.findElement(ByXPath.xpath("//a[@class='login']"));
		vSignInButton.click();
		
		//Completer champ email
		WebElement emailField = mDriver.findElement(By.name("email_create"));
		emailField.sendKeys("goran.sarribouette.etu@iut-lemans.fr");
		
		//Cliquer bouton Create an account
		WebElement vCreateAnAccountButton = mDriver.findElement(By.name("SubmitCreate"));
		vCreateAnAccountButton.click();
		
		//Check Mr. title
		WebElement vCheckMrTitle = mDriver.findElement(By.id("id_gender1"));
		vCheckMrTitle.click();
		
		//Completer champ first name
		WebElement firstNameField = mDriver.findElement(By.name("customer_firstname"));
		firstNameField.sendKeys("Goran");
		
		//Completer date of birth
		Select dayDateOfBirth = new Select(mDriver.findElement(By.id("days")));
		Select monthDateOfBirth = new Select(mDriver.findElement(By.id("months")));
		Select yearDateOfBirth = new Select(mDriver.findElement(By.id("years")));
		
		dayDateOfBirth.selectByValue("17");
		monthDateOfBirth.selectByValue("9");
		yearDateOfBirth.selectByValue("2000");
		
		//Check newsletter
		WebElement vCheckNewsLetter = mDriver.findElement(By.name("newsletter"));
		vCheckNewsLetter.click();
		
		//verification first name
		assertEquals("Goran", firstNameField.getAttribute("value"));
		WebElement verifyName = mDriver.findElement(ByXPath.xpath("//*[@id=\"account-creation_form\"]/div[1]/div[2]"));
		assertEquals("required form-group form-ok", verifyName.getAttribute("class"));
		
		//verification date of birth
		assertEquals("17", dayDateOfBirth.getFirstSelectedOption().getAttribute("value"));
		assertEquals("9", monthDateOfBirth.getFirstSelectedOption().getAttribute("value"));
		assertEquals("2000", yearDateOfBirth.getFirstSelectedOption().getAttribute("value"));
		
		//verification newletter
		assertEquals(true, vCheckNewsLetter.isSelected());
	}
	
	@After
	public void teardown() {
		mDriver.quit();
	}
	
}
