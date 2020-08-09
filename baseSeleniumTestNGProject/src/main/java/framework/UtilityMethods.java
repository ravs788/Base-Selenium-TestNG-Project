package main.java.framework;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import io.qameta.allure.Attachment;

public class UtilityMethods{

//	public String getTestMethodName(ITestResult iResult)
//	{
//		return iResult.getMethod().getConstructorOrMethod().getName();
//	}
//	
	@Attachment
	public byte[] getScreenshot(WebDriver driver)
	{
		return ((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES);		
	}
	
	@Attachment(value= "{0}",type="text/plain")
	public String saveTextLog(String message)
	{
		return message;
	}
}
