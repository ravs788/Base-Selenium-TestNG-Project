package basePackage;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;

import static org.testng.Assert.assertTrue;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;

public class TestOrangeHRM {

	ChromeDriver driver;
	String webDriverPath = System.setProperty("webdriver.chrome.driver", "C:\\Users\\ravshan\\OneDrive - Microsoft\\Software\\chromedriver_win32\\chromedriver.exe");
	String URL = "https://opensource-demo.orangehrmlive.com/";
	
	@BeforeClass
	public void launchBrowser() {
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get(URL);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	@Test(priority=1)
	public void loginApp() {
		try
		{
			driver.findElement(By.id("txtUsername")).sendKeys("Admin");
			driver.findElement(By.id("txtPassword")).sendKeys("admin123");
			driver.findElement(By.id("btnLogin")).click();
			assertTrue(driver.findElement(By.xpath("//ul[@id='mainMenuFirstLevelUnorderedList']/li//b[contains(text(),'Admin')]")).isDisplayed(),"Login successful");
		} catch(Exception e)
		{
			e.printStackTrace();
			System.out.println("Login failed");
		}
	}

	@Test(priority=2)
	public void searchUser() {
		try
		{
			driver.findElement(By.xpath("//ul[@id='mainMenuFirstLevelUnorderedList']/li//b[contains(text(),'Admin')]")).click();
			driver.findElement(By.id("searchSystemUser_userName")).sendKeys("Admin");
			driver.findElement(By.id("searchBtn")).click();
			assertTrue(driver.findElement(By.xpath(("//table[@id='resultTable']/tbody/tr[1]/td[2]/a[contains(text(),'Admin')]"))).isDisplayed(),"Search successful");
		} catch(Exception e)
		{
			e.printStackTrace();
			System.out.println("Search failed");
		}
	}

	@Test(priority=3)
	public void logOut() {
		try
		{
			driver.findElement(By.xpath("//a[contains(text(),'Welcome Admin')]")).click();
			driver.findElement(By.xpath("//a[contains(text(),'Logout')]")).click();
			assertTrue(driver.findElement(By.id("txtUsername")).isDisplayed(),"Logout successful");
		} catch(Exception e)
		{
			e.printStackTrace();
			System.out.println("Logout failed");
		}
	}

	
	
	@AfterClass
	public void afterClass() {
		driver.quit();
	}

}
