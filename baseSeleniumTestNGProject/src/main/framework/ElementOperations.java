package main.framework;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ElementOperations {
	
	private WebDriver driver;
	private WebDriverWait wait;
	
	public ElementOperations(WebDriver driver) {
		this.driver = driver;	
		this.wait = new WebDriverWait(this.driver, 30);
	}
	
	public void ClickOperation(WebElement element)
	{
		wait.until(ExpectedConditions.visibilityOf(element));
		wait.until(ExpectedConditions.elementToBeClickable(element));
		element.click();
	}

	public void EnterValue(WebElement element, String value)
	{
		wait.until(ExpectedConditions.visibilityOf(element));
		wait.until(ExpectedConditions.elementToBeClickable(element));
		element.clear();
		element.click();
		element.sendKeys(value);
	}
	
}
