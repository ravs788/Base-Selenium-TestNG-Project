package main.java.framework;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ReadConfig {
	
	InputStream inputStream;
	
	public String ReadFile() throws IOException
	{
		
		String BROWSER1 = null;
		String BROWSER2 = null;
		String BROWSER3 = null;
		String CHROME_HEADLESS = null;
		String FIREFOX_HEADLESS = null;
		String WEBDRIVER_WAIT=null;
		String IMPLICIT_WAIT=null;
		String PAGELOAD_TIMEOUT=null;
		
		try {
			Properties property = new Properties();
			String propertyFile = "config.properties";
			
			inputStream = getClass().getClassLoader().getResourceAsStream(propertyFile);
			
			if(inputStream!=null)
				property.load(inputStream);
			else
				throw new FileNotFoundException("property file '" + propertyFile + "' not found in the classpath");
			
			BROWSER1 = property.getProperty("BROWSER1");
			BROWSER2 = property.getProperty("BROWSER2");
			BROWSER3 = property.getProperty("BROWSER3");
			CHROME_HEADLESS = property.getProperty("CHROME.HEADLESS");
			FIREFOX_HEADLESS = property.getProperty("FIREFOX.HEADLESS");
			WEBDRIVER_WAIT=property.getProperty("WEBDRIVER.WAIT");
			IMPLICIT_WAIT=property.getProperty("IMPLICIT.WAIT");
			PAGELOAD_TIMEOUT=property.getProperty("PAGELOAD.TIMEOUT");
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally
		{
			inputStream.close();
		}
		
		return BROWSER1+":"+BROWSER2+":"+BROWSER3+":"+CHROME_HEADLESS+":"+FIREFOX_HEADLESS+":"+WEBDRIVER_WAIT+":"+IMPLICIT_WAIT+":"+PAGELOAD_TIMEOUT;
	}

}
