package main.java.framework;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ElementOperations extends BaseClass {

	private WebDriverWait wait;
	
	public ElementOperations(WebDriver driver){
		this.driver = driver;	
		this.wait = new WebDriverWait(this.driver, 30);				
	}

	public void ClickOperation(WebElement element)
	{
		try {
			waitForJSandJQueryToLoad(wait);
			wait.until(ExpectedConditions.visibilityOf(element));
			wait.until(ExpectedConditions.elementToBeClickable(element));			
			JavascriptExecutor js= (JavascriptExecutor)this.driver;
			js.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');", element);
			element.click();
			logger.debug("Click on element - {} - successful",element);
		} catch (Exception e) {
			logger.debug("Click on element - {} - failed with exception - {} ",element, e.getMessage());
		}
	}
	
	public void EnterValue(WebElement element, String value)
	{
		try {
			waitForJSandJQueryToLoad(wait);
			wait.until(ExpectedConditions.visibilityOf(element));
			wait.until(ExpectedConditions.elementToBeClickable(element));
			JavascriptExecutor js= (JavascriptExecutor)this.driver;
			js.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');", element);
			element.clear();
			element.click();
			element.sendKeys(value);
			logger.debug("Enter value - {} - in element - {} - successful",value,element);
		} catch (Exception e) {
			logger.debug("Enter value - {} - in element - {} - failed with exception - {} ",value,element,e.getMessage());
		}
	}
	
	public void MouseMoveOperation(WebElement element)
	{
		try
		{
			waitForJSandJQueryToLoad(wait);
			wait.until(ExpectedConditions.visibilityOf(element));	
			JavascriptExecutor js= (JavascriptExecutor)this.driver;
			js.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');", element);
			Actions action = new Actions(driver);
			action.moveToElement(element).build().perform();
			logger.debug("Move to element - {} - successful",element);
		}catch (Exception e) {
			logger.debug("Move to element - {} - failed with exception - {} ",element,e.getMessage());
		}
	}

	public boolean ElementExists(WebElement element)
	{
		JavascriptExecutor js= (JavascriptExecutor)this.driver;
		js.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');", element);
		return element.isDisplayed();
	}

	public WebElement FindElement(By locator)
	{
		WebElement element = this.driver.findElement(locator);
		JavascriptExecutor js= (JavascriptExecutor)this.driver;
		js.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');", element);		
		return element;
	}
	
	public List<WebElement> FindElements(By locator)
	{
		List<WebElement> elements = this.driver.findElements(locator);
		JavascriptExecutor js= (JavascriptExecutor)this.driver;
		for (WebElement element : elements) 			
		{
			js.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');", element);
		}
		return elements;
	}
	
	public WebElement FindElement(WebElement parentElement, By locator)
	{
		WebElement element = parentElement.findElement(locator);
		JavascriptExecutor js= (JavascriptExecutor)this.driver;
		js.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');", element);		
		return element;
	}
	
	public List<WebElement> FindElements(WebElement parentElement, By locator)
	{
		List<WebElement> elements = parentElement.findElements(locator);
		JavascriptExecutor js= (JavascriptExecutor)this.driver;
		for (WebElement element : elements) 			
		{
			js.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');", element);
		}
		return elements;
	}
}
