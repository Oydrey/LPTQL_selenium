package com.automation.selenium.practice;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import static org.junit.Assert.assertEquals;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

/**
 * Basic test suite used to launch a simple test case.
 * 
 * @author thedoux
 */
public class BrowserTest {

	/** Defined the target browser **/
	private static final DesiredCapabilities CAPABILITY = DesiredCapabilities.firefox();

	/** URL of Selenium node **/
	private static final String SELENIUM_SERVER_URL = "http://127.0.0.1:4444/wd/hub";

	/** URL opened in first **/
	private static final String BASIC_URL = "https://www.qwant.com";

	/** Driver used to link with Selenium API **/
	private WebDriver mDriver = null;

	/**
	 * Setup phase, it will be launched before any test
	 * 
	 * @throws MalformedURLException If based url is not the correct one
	 * @throws InterruptedException  Exception thrown if the thread is interrupted
	 */
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

	/**
	 * Simple scenario: - Select the search field - Fill it - Launch a search -
	 * Check if there is a result
	 * 
	 * @throws InterruptedException Exception thrown if the thread is interrupted
	 */
	@Test
	public void qwantSearch() throws InterruptedException {

		//Thread.sleep(500); // IT IS BAD. NEVER DO THIS

		// Find search field
		WebElement searchField = mDriver.findElement(By.name("q"));

		//Thread.sleep(500); // IT IS BAD. NEVER DO THIS

		// Fill it
		searchField.sendKeys("IUT de LAVAL");

		//Thread.sleep(500); // IT IS BAD. NEVER DO THIS

		// Launch search
		mDriver.findElement(By.className("search_bar__form__search")).click();

		//Thread.sleep(500); // IT IS BAD. NEVER DO THIS

		// Check if the URL of Laval university is really presented
		WebElement vLink = mDriver.findElement(By.xpath("/html/body/div[1]/div/div[3]/div[4]/div/div/div[3]/div[2]/div[3]/div[1]/h3/a"));

		//Thread.sleep(500); // IT IS BAD. NEVER DO THIS

		// Click on the first link
		vLink.click();

		//Thread.sleep(500); // IT IS BAD. NEVER DO THIS

		// Switch tab
		Iterator<String> iterator = mDriver.getWindowHandles().iterator();
		iterator.next();
		mDriver.switchTo().window(iterator.next());

		//Thread.sleep(500); // IT IS BAD. NEVER DO THIS

		String vCurrentURL = mDriver.getCurrentUrl();

		// Check the current URL
		assertEquals("http://iut-laval.univ-lemans.fr/fr/index.html", vCurrentURL);
	}

	/**
	 * Kill the driver once all the tests are run
	 */
	@After
	public void teardown() {
		mDriver.quit();
	}

}
