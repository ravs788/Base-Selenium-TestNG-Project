package main.java.framework;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;


public class UtilityMethods{

	public byte[] getScreenshot(WebDriver driver)
	{
		return ((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES);		
	}
	
	public String saveTextLog(String message)
	{
		return message;
	}
}
