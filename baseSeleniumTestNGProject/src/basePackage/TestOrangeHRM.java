package basePackage;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;

public class TestOrangeHRM {

	ChromeDriver driver;
	String webDriverPath = System.setProperty("webdriver.chrome.driver", "C:\\Users\\ravshan\\OneDrive - Microsoft\\Software\\chromedriver_win32\\chromedriver.exe");
	String URL = "https://opensource-demo.orangehrmlive.com/";
	
	@BeforeClass (alwaysRun = true)
	public void launchBrowser() {
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get(URL);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	@Test(groups = {"Smoke", "Sanity"})
	public void loginApp() {
		
		driver.findElement(By.id("txtUsername")).sendKeys("Admin");
		driver.findElement(By.id("txtPassword")).sendKeys("admin123");
		driver.findElement(By.id("btnLogin")).click();
		Assert.assertTrue(driver.findElement(By.xpath("//ul[@id='mainMenuFirstLevelUnorderedList']/li//b[contains(text(),'Admin')]")).isDisplayed(),"Login successful");
	
	}

	@Test(dependsOnMethods="loginApp", groups = "Smoke")
	public void searchUser() {
		driver.findElement(By.xpath("//ul[@id='mainMenuFirstLevelUnorderedList']/li//b[contains(text(),'Admin')]")).click();
		driver.findElement(By.id("searchSystemUser_userName")).sendKeys("Admin");
		driver.findElement(By.id("searchBtn")).click();
		Assert.assertTrue(driver.findElement(By.xpath(("//table[@id='resultTable']/tbody/tr[1]/td[2]/a[contains(text(),'Admin')]"))).isDisplayed(),"Search successful");
	}
	
	@Test(dependsOnMethods="loginApp", groups = "Sanity")
	public void searchEmployee() {
		driver.findElement(By.xpath("//ul[@id='mainMenuFirstLevelUnorderedList']/li//b[contains(text(),'PIM')]")).click();
		driver.findElement(By.id("menu_pim_viewEmployeeList")).click();
		driver.findElement(By.id("empsearch_employee_name_empName")).clear();
		driver.findElement(By.id("empsearch_employee_name_empName")).sendKeys("Linda Andersen");
		driver.findElement(By.id("searchBtn")).click();
		Assert.assertTrue(driver.findElement(By.xpath(("//table[@id='resultTable']/tbody/tr[1]/td[3]/a[contains(text(),'Linda')]"))).isDisplayed(),"Search successful");
	}

	@Test(dependsOnMethods="searchUser", groups = {"Smoke", "Sanity"})
	public void logOut() {
		driver.findElement(By.xpath("//a[contains(text(),'Welcome Admin')]")).click();
		driver.findElement(By.xpath("//a[contains(text(),'Logout')]")).click();
		Assert.assertTrue(driver.findElement(By.id("txtUsername")).isDisplayed(),"Logout successful");		
	}
	
	@AfterClass(alwaysRun = true)
	public void afterClass() {
		driver.quit();
	}

}
