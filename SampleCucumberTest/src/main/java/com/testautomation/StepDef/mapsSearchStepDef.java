package com.testautomation.StepDef;

import static org.testng.Assert.assertTrue;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


import com.testautomation.Utility.AddDataToTextFile;
import com.testautomation.Utility.PropertiesFileReader;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;

public class mapsSearchStepDef {
	
	private static WebDriver driver ;
	PropertiesFileReader prop = new PropertiesFileReader();
	By SEARCH_TEXTBOX = By.cssSelector("#searchboxinput");
	By DIRECTION_IMAGE = By.cssSelector("button[data-value='Directions']");
	By CAR_IMAGE = By.cssSelector("img[aria-label='Driving']");
	By STARTING_POINT_TEXTBOX =By.cssSelector("#directions-searchbox-0 input");
	By DESTINATION_TEXTBOX = By.cssSelector("#directions-searchbox-1 input");
	By SEARCH_RESULT = By.xpath("//div[contains(@aria-labelledby,'section-directions-trip-travel-mode')]");
	String DYNAMIC_SEARCH_RESULT = "//div[contains(@aria-labelledby,'section-directions-trip-travel-mode')][%s]";
	By SEARCH_RESULTS_TITLE = By.xpath(".//h1//span");
	By SEARCH_RESULTS_TRAVEL_TIME = By.xpath(".//div[contains(@class,'duration')]//span[contains(.,'min')]");
	By SEARCH_RESULTS_DISTANCE = By.xpath(".//div[contains(@class,'distance')]//div[contains(.,'miles')]");
	
	public void WaitforElementVisisble(By ele) {
	WebDriverWait wait = new WebDriverWait(driver, 15);
	WebElement element = driver.findElement(ele);
	wait.until(ExpectedConditions.visibilityOf(element));
	}
	
	public static WebDriver getDriver() {
		return driver;
	}
	
	@Given("^Open the browser and access Google Map Site$")
	public void open_browser_and_access_Google_Map_Site() throws Throwable {
		String browser = prop.getProperty().getProperty("browser");
		String baseUrl = prop.getProperty().getProperty("baseURL");
		if(browser.equalsIgnoreCase("chrome")) {
			WebDriverManager.chromedriver().version("91.0.4472.101").setup(); // using the Specific Version just for office laptop restriction
			 driver = new ChromeDriver();
			 System.out.println("*****Execution On Chrome Browser****");
			 driver.manage().window().maximize();
			 driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			 driver.get(baseUrl);
		}if(browser.equalsIgnoreCase("FireFox")) {
			WebDriverManager.firefoxdriver().setup();
			 driver = new FirefoxDriver();
			 System.out.println("*****Execution On Firefox Browser******");
			 driver.manage().window().maximize();
			 driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			 System.setProperty(FirefoxDriver.SystemProperty.DRIVER_USE_MARIONETTE,"true");
			 driver.get(baseUrl);
		}if(browser.equalsIgnoreCase("IE")) {
			WebDriverManager.iedriver().setup(); 
			
			System.out.println("******Execution On IE Browser********");
			InternetExplorerOptions options = new InternetExplorerOptions();
			options.setCapability("disable-popup-blocking", true);
			options.setCapability("ignoreProtectedModeSettings", true);
			options.setCapability("ignoreZoomSetting", true);
			options.setCapability("requireWindowFocus", true);
			driver = new InternetExplorerDriver(options);
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			driver.get(baseUrl);
		}

		
	}
	   

	@When("^Search \"([^\"]*)\" from the search field$")
	public void search_from_the_search_field(String arg1) throws Throwable {
		WaitforElementVisisble(SEARCH_TEXTBOX);
		driver.findElement(SEARCH_TEXTBOX).sendKeys(arg1);
		driver.findElement(By.cssSelector("#searchbox-searchbutton")).click();
		WaitforElementVisisble(DIRECTION_IMAGE);
	   
	}

	@Then("^verify \"([^\"]*)\" coordinates matches$")
	public void verify_coordinates_matches(String arg1) throws Throwable {
	   String actualcoordinates = driver.getCurrentUrl().split("@")[1].split("/")[0].split(",[0-9]")[0];
	   System.out.println(actualcoordinates);
	   assertTrue(actualcoordinates.equalsIgnoreCase(arg1), "Actual Corrdinates "+actualcoordinates+" Should match With Expected Coordinates"+arg1);
	

}
	
	
	@When("^Search for driving direction between \"([^\"]*)\" and \"([^\"]*)\" by \"([^\"]*)\"$")
	public void search_for_driving_direction_between_and_by(String arg1, String arg2, String arg3) throws Throwable {
		driver.findElement(DIRECTION_IMAGE).click();
		if(arg3.equalsIgnoreCase("car")) {
		WaitforElementVisisble(CAR_IMAGE);
		driver.findElement(CAR_IMAGE).click();
		}
		WaitforElementVisisble(STARTING_POINT_TEXTBOX);
		driver.findElement(STARTING_POINT_TEXTBOX).sendKeys(arg1);
		driver.findElement(DESTINATION_TEXTBOX).sendKeys(arg2);
		driver.findElement(DESTINATION_TEXTBOX).sendKeys(Keys.ENTER);
	}
	
	@Then("^verify more than (\\d+) results are displaying in Reults$")
	public void verify_more_than_results_are_displaying_in_Reults(int arg1) throws Throwable {
	  WaitforElementVisisble(SEARCH_RESULT);
	  int actualresultSize = driver.findElements(SEARCH_RESULT).size();
	  assertTrue(actualresultSize >= arg1 , "Actual Results "+actualresultSize+" Should be Greater Than or equal to the Expected Count "+arg1);
	  System.out.println("closing browser");
	}
	
	@Given("^Save the title, distance and travel time in \"([^\"]*)\" file$")
	public void save_the_title_distance_and_travel_time_in_file(String arg1) throws Throwable {
		List<String> searchResultData = new LinkedList<String>();
		int count = 1;
		for(WebElement results : driver.findElements(SEARCH_RESULT)) {
			searchResultData.add("Result "+count+ " Title is "+driver.findElement(By.xpath(String.format(DYNAMIC_SEARCH_RESULT, count))).findElement(SEARCH_RESULTS_TITLE).getText().trim());
			searchResultData.add("Result "+count+ " Travel Time is "+driver.findElement(By.xpath(String.format(DYNAMIC_SEARCH_RESULT, count))).findElement(SEARCH_RESULTS_TRAVEL_TIME).getText().trim());
			searchResultData.add("Result "+count+ " Distance in Miles is "+driver.findElement(By.xpath(String.format(DYNAMIC_SEARCH_RESULT, count))).findElement(SEARCH_RESULTS_DISTANCE).getText().trim());
			count++;
		}
		AddDataToTextFile.addDatatoTextFile(searchResultData, arg1);
	}

	
	public static File takeScreenShotAndReturnFile() {
		return (((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE));
	}
	
	public static void tearDown() {
		driver.quit();
	}

}