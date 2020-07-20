package test.secondPackage;

import org.testng.annotations.DataProvider;

public class InheritedDataClass{
	@DataProvider(name="testDataMethod")
	public static Object[][] testData()
	{
		Object[][] data = new Object[1][3];
		
		data[0][0] = "Admin"; data[0][1] ="admin123"; data[0][2] = "Linda Anderson";
		
		return data;
	}
}
