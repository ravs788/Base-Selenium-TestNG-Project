package main.framework;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ElementOperations extends BaseClass {
	
	private WebDriver driver;
	private WebDriverWait wait;
	
	public ElementOperations(WebDriver driver){
		this.driver = driver;	
		this.wait = new WebDriverWait(this.driver, 30);		
	}
	
	public void ClickOperation(WebElement element)
	{
		try {
			wait.until(ExpectedConditions.visibilityOf(element));
			wait.until(ExpectedConditions.elementToBeClickable(element));
			element.click();
			logger.debug("Click on element - {} - successful",element);
		} catch (Exception e) {
			logger.debug("Click on element - {} - failed with exception - {} ",element, e.getMessage());
		}
	}

	public void EnterValue(WebElement element, String value)
	{
		try {
			wait.until(ExpectedConditions.visibilityOf(element));
			wait.until(ExpectedConditions.elementToBeClickable(element));
			element.clear();
			element.click();
			element.sendKeys(value);
			logger.debug("Enter value - {} - in element - {} - successful",value,element);
		} catch (Exception e) {
			logger.debug("Enter value - {} - in element - {} - failed with exception - {} ",value,element,e.getMessage());
		}
	}
	
	public boolean ElementExists(WebElement element)
	{
		if (element.isDisplayed())
			return true;
		else
			return false;
	
	}
	
}
