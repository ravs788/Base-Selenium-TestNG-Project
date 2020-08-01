package main.framework;

import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class BaseClass {

	private WebDriver driver;
	protected static final Logger logger = LogManager.getLogger(BaseClass.class);

	public WebDriver setup(String URL, String strBrowserType)
	{
		if(strBrowserType.toLowerCase().equals("chrome"))
		{			
			String webDriverPath = System.setProperty("webdriver.chrome.driver", "C:\\Users\\ravshan\\OneDrive - Microsoft\\Software\\chromedriver_win32\\chromedriver.exe");
			ChromeOptions options = new ChromeOptions();
			options.addArguments("headless");
			options.addArguments("window-size=1200x600");
			driver = new ChromeDriver(options);
		}
		else if(strBrowserType.toLowerCase().equals("firefox"))
		{
			String webDriverPath = System.setProperty("webdriver.gecko.driver", "C:\\Users\\ravshan\\OneDrive - Microsoft\\Software\\geckodriver-v0.26.0-win64\\geckodriver.exe");
			FirefoxOptions options = new FirefoxOptions();
			options.setHeadless(true);
			driver = new FirefoxDriver(options);
		}
		else if(strBrowserType.toLowerCase().equals("edge"))
		{
			String webDriverPath = System.setProperty("webdriver.edge.driver", "C:\\Users\\ravshan\\OneDrive - Microsoft\\Software\\edgedriver_win64\\msedgedriver.exe");
			driver = new EdgeDriver();
		}
		driver.manage().window().maximize();
		driver.get(URL);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		logger.debug("Browser - {} - launched successfull",strBrowserType);
		return driver;
	}
	
	public void tearDown()
	{
		driver.quit();
		logger.debug("Browser closed and process killed successfully");
	}

}
