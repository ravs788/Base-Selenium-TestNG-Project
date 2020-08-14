package main.java.framework;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class ListenerClass extends BaseClass implements ITestListener {
	
	@Override		
	public void onFinish(ITestContext Result) {					

	}		

	@Override		
	public void onStart(ITestContext Result) {					
	}		

	@Override		
	public void onTestFailedButWithinSuccessPercentage(ITestResult Result) {					
		
	}		

	@Override		
	public void onTestFailure(ITestResult Result) {					
		logger.error("Test {} failed",Result.getName());				
	}		

	@Override		
	public void onTestSkipped(ITestResult Result) {					
		logger.info("Test {} skipped",Result.getName());        		
	}		

	@Override		
	public void onTestStart(ITestResult Result) {					
		logger.info("Test {} started",Result.getName());				

	}		

	@Override		
	public void onTestSuccess(ITestResult Result) {					
		logger.info("Test {} passed",Result.getName());	
	}	

}
