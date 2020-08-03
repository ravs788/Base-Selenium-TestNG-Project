package main.framework;

import java.io.IOException;
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

	public WebDriver setup(String URL, String strBrowserType) throws IOException
	{
		ReadConfig config = new ReadConfig();
		String[] Config_Keys = config.ReadFile().split(":");
		String BROWSER1 = Config_Keys[0];
		String BROWSER2 = Config_Keys[1];
		String BROWSER3 = Config_Keys[2];
		String CHROME_HEADLESS = Config_Keys[3];
		String FIREFOX_HEADLESS = Config_Keys[4];
//		String WEBDRIVER_WAIT = Config_Keys[5];
		String IMPLICIT_WAIT = Config_Keys[6];
		String PAGELOAD_TIMEOUT = Config_Keys[7];
		
		
		if(strBrowserType.toLowerCase().equals(BROWSER1.toLowerCase()))
		{			
			String webDriverPath = System.setProperty("webdriver.chrome.driver", "C:\\Users\\ravshan\\OneDrive - Microsoft\\Software\\chromedriver_win32\\chromedriver.exe");
			ChromeOptions options = new ChromeOptions();
			if (!CHROME_HEADLESS.equals(""))
				options.addArguments(CHROME_HEADLESS);
			driver = new ChromeDriver(options);
		}
		else if(strBrowserType.toLowerCase().equals(BROWSER2.toLowerCase()))
		{
			String webDriverPath = System.setProperty("webdriver.gecko.driver", "C:\\Users\\ravshan\\OneDrive - Microsoft\\Software\\geckodriver-v0.26.0-win64\\geckodriver.exe");
			FirefoxOptions options = new FirefoxOptions();
			options.setHeadless(Boolean.parseBoolean(FIREFOX_HEADLESS));
			driver = new FirefoxDriver(options);
		}
		else if(strBrowserType.toLowerCase().equals(BROWSER3.toLowerCase()))
		{
			String webDriverPath = System.setProperty("webdriver.edge.driver", "C:\\Users\\ravshan\\OneDrive - Microsoft\\Software\\edgedriver_win64\\msedgedriver.exe");
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

}
