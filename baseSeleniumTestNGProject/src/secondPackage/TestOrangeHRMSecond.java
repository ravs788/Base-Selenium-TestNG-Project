package secondPackage;

import org.testng.annotations.Test;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;

public class TestOrangeHRMSecond extends InheritedDataClass{

	WebDriver driver;
			
	/**
	 * Function to Launch Browser
	 * @param URL - URL of the Application
	 * @param strBrowserType - Browser to be Used
	 * Return Type : Void
	 */
	@BeforeClass (alwaysRun = true)
	@Parameters({"URL","BrowserType"})
	public void launchBrowser(@Optional ("https://opensource-demo.orangehrmlive.com/") String URL, @Optional ("Chrome") String strBrowserType) {
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
	}
	
	/**
	 * Function to Log In to the App
	 * @param userName - UserName for log in
	 * @param password - Password for Log In
	 * @param searchUser - Not Used
	 * @param nextMenu - Not Used
	 * Return Type : Void
	 */
	@Test(groups = {"Smoke", "Sanity"}, dataProvider = "testDataMethod")
	public void loginApp(String userName, String password, String searchUser, String nextMenu) {
		
		driver.findElement(By.id("txtUsername")).sendKeys(userName);
		driver.findElement(By.id("txtPassword")).sendKeys(password);
		driver.findElement(By.id("btnLogin")).click();
		Assert.assertTrue(driver.findElement(By.xpath("//ul[@id='mainMenuFirstLevelUnorderedList']/li//b[contains(text(),'"+userName+"')]")).isDisplayed(),"Login successful");
	
	}

	/**
	 * Function to Search for a User
	 * @param userName - Not Used
	 * @param password - Not Used
	 * @param searchUser - Not Used
	 * @param nextMenu - Not Used
	 * Return Type : Void
	 * @throws InterruptedException
	 */
	@Test(dependsOnMethods="loginApp", groups = "Smoke", dataProvider = "testDataMethod")
	public void searchUser(String userName, String password, String searchUser, String nextMenu) throws InterruptedException {
		Thread.sleep(2000);
		driver.findElement(By.xpath("//ul[@id='mainMenuFirstLevelUnorderedList']/li//b[contains(text(),'"+userName+"')]")).click();
		driver.findElement(By.id("searchSystemUser_userName")).sendKeys(userName);
		driver.findElement(By.id("searchBtn")).click();
		Assert.assertTrue(driver.findElement(By.xpath(("//table[@id='resultTable']/tbody/tr[1]/td[2]/a[contains(text(),'"+userName+"')]"))).isDisplayed(),"Search successful");
	}
	
	/**
	 * Function to Search for an employee
	 * @param userName - Not Used
	 * @param password - Not Used
	 * @param searchEmployee - Employee to be searched for
	 * @param nextMenu - Next menu item to be used
	 * Return Type : Void
	 * @throws InterruptedException
	 */
	@Test(dependsOnMethods="loginApp", groups = "Sanity", dataProvider = "testDataMethod")
	public void searchEmployee(String userName, String password, String searchEmployee, String nextMenu) throws InterruptedException {
		Thread.sleep(2000);
		driver.findElement(By.xpath("//ul[@id='mainMenuFirstLevelUnorderedList']/li//b[contains(text(),'"+nextMenu+"')]")).click();		
		driver.findElement(By.id("empsearch_employee_name_empName")).click();
		driver.findElement(By.id("empsearch_employee_name_empName")).sendKeys(searchEmployee);
		Thread.sleep(2000);
		driver.findElement(By.xpath("//div[@class='ac_results']/ul/li[1]")).click();
		driver.findElement(By.id("searchBtn")).click();
		Thread.sleep(2000);
		String firstName = searchEmployee.split(" ")[0];
		Assert.assertTrue(driver.findElement(By.xpath(("//table[@id='resultTable']/tbody/tr[1]/td[3]/a[contains(text(),'"+firstName+"')]"))).isDisplayed(),"Search successful");
	}

	/**
	 * Function to log out of the application
	 * Return: Void
	 */
	@Test(dependsOnMethods="searchUser", groups = {"Smoke", "Sanity"})
	public void logOut() {
		driver.findElement(By.xpath("//a[contains(text(),'Welcome Admin')]")).click();
		driver.findElement(By.xpath("//a[contains(text(),'Logout')]")).click();
		Assert.assertTrue(driver.findElement(By.id("txtUsername")).isDisplayed(),"Logout successful");		
	}
	
	/**
	 * Function to close the browser
	 * Return: Void
	 */
	@AfterClass(alwaysRun = true)
	public void afterClass() {
		driver.quit();
	}
	
}
