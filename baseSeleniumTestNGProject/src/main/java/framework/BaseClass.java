package main.java.framework;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.ExtentReports;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseClass {

	protected WebDriver driver;
	protected static final Logger logger = LogManager.getLogger(BaseClass.class);
	protected static ExtentReports report;

	public WebDriver setup(String URL, String strBrowserType) throws IOException
	{
		ReadConfig config = new ReadConfig();
		String[] Config_Keys = config.ReadFile().split(":");
		final String BROWSER1 = Config_Keys[0];
		final String BROWSER2 = Config_Keys[1];
		final String BROWSER3 = Config_Keys[2];
		final String CHROME_HEADLESS = Config_Keys[3];
		final String FIREFOX_HEADLESS = Config_Keys[4];
//		final String WEBDRIVER_WAIT = Config_Keys[5];
		final String IMPLICIT_WAIT = Config_Keys[6];
		final String PAGELOAD_TIMEOUT = Config_Keys[7];
		
		if(strBrowserType.equalsIgnoreCase(BROWSER1.toLowerCase()))
		{				
			WebDriverManager.chromedriver().setup();
			ChromeOptions options = new ChromeOptions();
			if (!CHROME_HEADLESS.equals(""))
				options.addArguments(CHROME_HEADLESS);
			driver = new ChromeDriver(options);
		}
		else if(strBrowserType.equalsIgnoreCase(BROWSER2.toLowerCase()))
		{
			WebDriverManager.firefoxdriver().setup();
			FirefoxOptions options = new FirefoxOptions();
			options.setHeadless(Boolean.parseBoolean(FIREFOX_HEADLESS));
			driver = new FirefoxDriver(options);
		}
		else if(strBrowserType.equalsIgnoreCase(BROWSER3.toLowerCase()))
		{
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();
		}
		driver.manage().window().maximize();
		driver.get(URL);
		driver.manage().timeouts().implicitlyWait(Long.parseLong(IMPLICIT_WAIT), TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(Long.parseLong(PAGELOAD_TIMEOUT), TimeUnit.SECONDS);
		logger.debug("Browser - {} - launched successfully",strBrowserType);
		return driver;
	}
	
	public void tearDown()
	{
		driver.quit();
		logger.debug("Browser closed and process killed successfully");
	}

	public WebDriver getDriver()
	{
		return driver;
	}
	
	public void getScreenshot(String result) throws IOException {
        File src = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(src, new File("C://s//"+result+"screenshot.png"));       
    }
	
	public boolean waitForJSandJQueryToLoad(WebDriverWait wait) {

	    // wait for jQuery to load
	    ExpectedCondition<Boolean> jQueryLoad = new ExpectedCondition<Boolean>() {
	      @Override
	      public Boolean apply(WebDriver driver) {
	        try {
	          return ((Long)((JavascriptExecutor) driver).executeScript("return jQuery.active") == 0);
	        }
	        catch (Exception e) {
	          // no jQuery present
	          return true;
	        }
	      }
	    };

	    // wait for Javascript to load
	    ExpectedCondition<Boolean> jsLoad = new ExpectedCondition<Boolean>() {
	      @Override
	      public Boolean apply(WebDriver driver) {
	        return ((JavascriptExecutor)driver).executeScript("return document.readyState")
	        .toString().equals("complete");
	      }
	    };

	  return wait.until(jQueryLoad) && wait.until(jsLoad);
	}
}
