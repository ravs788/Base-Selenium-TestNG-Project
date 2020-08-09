package test.java.basePackage;

import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import main.java.framework.BaseClass;
import main.java.framework.ElementOperations;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterClass;

public class TestOrangeHRM extends BaseClass{
		
	/**
	 * Function to Launch Browser
	 * @param URL - URL of the Application
	 * @param strBrowserType - Browser to be Used
	 * Return Type : Void
	 * @throws IOException 
	 */
	@BeforeClass (alwaysRun = true)
	@Parameters({"URL","BrowserType"})
	public void launchBrowser(@Optional ("https://opensource-demo.orangehrmlive.com/") String URL, @Optional ("chrome") String strBrowserType) throws IOException {
		logger.info("------------------------------------------------------");
		logger.info("------------Started Test on Browser {} ---------------",strBrowserType);
		driver = setup(URL, strBrowserType);
	}
	
	/**
	 * Function to Log In to the App
	 * @param userName - UserName for log in
	 * @param password - Password for Log In
	 * @param searchUser - Not Used
	 * @param nextMenu - Not Used
	 * Return Type : Void
	 */
	@Test(description = "Login to the application", groups = {"Smoke", "Sanity"}, dataProviderClass = TestData.class, dataProvider = "testDataMethod")
	@Description("Login to the application")
	@Epic("Epic: EP001")
	@Feature("Feature: Main Feature")
	@Story(value = "Base Story")
	@Severity(SeverityLevel.CRITICAL)
	public void loginApp(String userName, String password, String searchUser) {
		ElementOperations operations = new ElementOperations(driver);
		WebElement userNameField = driver.findElement(By.id("txtUsername")); 
		WebElement passwordField = driver.findElement(By.id("txtPassword"));
		WebElement loginButton = driver.findElement(By.id("btnLogin"));
				
		operations.EnterValue(userNameField, userName);
		operations.EnterValue(passwordField, password);
		operations.ClickOperation(loginButton);
		Assert.assertTrue(driver.findElement(By.xpath("//ul[@id='mainMenuFirstLevelUnorderedList']/li//b[contains(text(),'"+userName+"')]")).isDisplayed(),"Login successful");
		logger.info("Login successful");
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
	@Test(description = "Searching for user",dependsOnMethods="loginApp", groups = "Smoke", dataProviderClass = TestData.class, dataProvider = "testDataMethod")
	@Description("Searching for user")
	@Epic("Epic: EP001")
	@Feature("Feature: Search User")
	@Story(value = "Search User")
	@Severity(SeverityLevel.NORMAL)
	public void searchUser(String userName, String password, String searchUser){
		ElementOperations operations = new ElementOperations(driver);
		WebElement firstMenu = driver.findElement(By.xpath("//ul[@id='mainMenuFirstLevelUnorderedList']/li//b[contains(text(),'Admin')]"));
		
		operations.ClickOperation(firstMenu);
		List<WebElement> searchTextField = driver.findElements(By.id("searchSystemUser_userName"));
		if(searchTextField.isEmpty())
			operations.ClickOperation(firstMenu);	
		
		
		WebElement searchTextField1 = driver.findElement(By.id("searchSystemUser_userName"));
		WebElement searchButton = driver.findElement(By.id("searchBtn"));
		
		operations.EnterValue(searchTextField1, userName);
		operations.ClickOperation(searchButton);
		Assert.assertTrue(driver.findElement(By.xpath(("//table[@id='resultTable']/tbody/tr[1]/td[2]/a[contains(text(),'"+userName+"')]"))).isDisplayed(),"Search successful");
		logger.info("Search User successful");
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
	
	@Test(description = "Searching for employee", dependsOnMethods="loginApp", groups = "Sanity", dataProviderClass = TestData.class, dataProvider = "testDataMethod")
	@Description("Searching for employee")
	@Epic("Epic: EP001")
	@Feature("Feature: Search Employee")
	@Story(value = "Search Employee")	
	@Severity(SeverityLevel.NORMAL)
	public void searchEmployee(String userName, String password, String searchEmployee){

			ElementOperations operations = new ElementOperations(driver);
			WebElement nextMenuField = driver.findElement(By.xpath("//ul[@id='mainMenuFirstLevelUnorderedList']/li//b[contains(text(),'PIM')]"));
			
			operations.ClickOperation(nextMenuField);
			
			List<WebElement> searchTextField = driver.findElements(By.id("empsearch_employee_name_empName"));
			if(searchTextField.isEmpty())
				operations.ClickOperation(nextMenuField);	
			
			WebElement searchButton = driver.findElement(By.id("searchBtn")); 
			
			WebElement searchTextField1 = driver.findElement(By.id("empsearch_employee_name_empName"));
			operations.ClickOperation(searchTextField1);
			operations.EnterValue(searchTextField1, searchEmployee);
			
			WebElement searchResultDropDown = driver.findElement(By.xpath("//div[@class='ac_results']/ul/li[1]"));
			
			operations.ClickOperation(searchResultDropDown);
			operations.ClickOperation(searchButton);
			String firstName = searchEmployee.split(" ")[0];
			Assert.assertTrue(driver.findElement(By.xpath(("//table[@id='resultTable']/tbody/tr[1]/td[3]/a[contains(text(),'"+firstName+"')]"))).isDisplayed(),"Search successful");
			logger.info("Search Employee successful");
	}

	/**
	 * Function to log out of the application
	 * Return: Void
	 */
	@Test(description = "Log out of the application", dependsOnMethods="searchUser", groups = {"Smoke", "Sanity"})
	@Description("Log out of the application")
	@Epic("Epic: EP001")
	@Feature("Feature: Main Feature")
	@Story(value = "Base Story")
	@Severity(SeverityLevel.MINOR)
	public void logOut() {
		ElementOperations operations = new ElementOperations(driver);
		WebElement welcomeAdminElement = driver.findElement(By.xpath("//a[contains(text(),'Welcome Admin')]"));
		
		operations.ClickOperation(welcomeAdminElement);
		
		WebElement logOutLink = driver.findElement(By.xpath("//a[contains(text(),'Logout')]"));
				
		operations.ClickOperation(logOutLink);
		Assert.assertTrue(driver.findElement(By.id("txtUsername")).isDisplayed(),"Logout successful");
		logger.info("Logout successful");
	}
	
	/**
	 * Function to close the browser
	 * Return: Void
	 */
	@AfterClass(alwaysRun = true)
	public void afterClass() {
		tearDown();
		
		logger.info("-----------------------Ended Test --------------------");
		logger.info("------------------------------------------------------");
	}
	
}