package basePackage;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
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
	
	@Test(groups = {"Smoke", "Sanity"}, dataProviderClass = TestData.class, dataProvider = "testDataMethod")
	public void loginApp(String userName, String password, String searchUser, String nextMenu) {
		
		driver.findElement(By.id("txtUsername")).sendKeys(userName);
		driver.findElement(By.id("txtPassword")).sendKeys(password);
		driver.findElement(By.id("btnLogin")).click();
		Assert.assertTrue(driver.findElement(By.xpath("//ul[@id='mainMenuFirstLevelUnorderedList']/li//b[contains(text(),'"+userName+"')]")).isDisplayed(),"Login successful");
	
	}

	@Test(dependsOnMethods="loginApp", groups = "Smoke", dataProviderClass = TestData.class, dataProvider = "testDataMethod")
	public void searchUser(String userName, String password, String searchUser, String nextMenu) throws InterruptedException {
		Thread.sleep(2000);
		driver.findElement(By.xpath("//ul[@id='mainMenuFirstLevelUnorderedList']/li//b[contains(text(),'"+userName+"')]")).click();
		driver.findElement(By.id("searchSystemUser_userName")).sendKeys(userName);
		driver.findElement(By.id("searchBtn")).click();
		Assert.assertTrue(driver.findElement(By.xpath(("//table[@id='resultTable']/tbody/tr[1]/td[2]/a[contains(text(),'"+userName+"')]"))).isDisplayed(),"Search successful");
	}
	
	@Test(dependsOnMethods="loginApp", groups = "Sanity", dataProviderClass = TestData.class, dataProvider = "testDataMethod")
	public void searchEmployee(String userName, String password, String searchUser, String nextMenu) throws InterruptedException {
		Thread.sleep(2000);
		driver.findElement(By.xpath("//ul[@id='mainMenuFirstLevelUnorderedList']/li//b[contains(text(),'"+nextMenu+"')]")).click();
//		driver.findElement(By.id("menu_pim_viewEmployeeList")).click();
		driver.findElement(By.id("empsearch_employee_name_empName")).clear();
		driver.findElement(By.id("empsearch_employee_name_empName")).sendKeys(searchUser);
		driver.findElement(By.xpath("//div[@class='ac_results']/ul/li/strong[contains(text(),'"+searchUser+"')]")).click();
		driver.findElement(By.id("searchBtn")).click();
		String firstName = searchUser.split(" ")[0];
		Assert.assertTrue(driver.findElement(By.xpath(("//table[@id='resultTable']/tbody/tr[1]/td[3]/a[contains(text(),'"+firstName+"')]"))).isDisplayed(),"Search successful");
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
