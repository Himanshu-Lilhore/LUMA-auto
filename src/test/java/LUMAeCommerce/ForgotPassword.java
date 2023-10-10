package LUMAeCommerce;

import java.io.IOException;
import java.lang.invoke.MethodHandles;

import org.openqa.selenium.By;
import org.testng.annotations.Test;

public class ForgotPassword
{
	public static boolean codeReuse = false;
	
	@Test
	public static void forgotPass() throws IOException, InterruptedException
	{
		// Initializations
		String thisClassName = MethodHandles.lookup().lookupClass().toString();
		if(!codeReuse)
			MainScript.initializations(thisClassName, codeReuse);
		
		// Navigation : Home page > Login > Forgot Password
		MainScript.driver.findElement(By.xpath(MainScript.getLoc("Home_signIn"))).click();
		MainScript.driver.findElement(By.xpath(MainScript.getLoc("Login_forgotPass"))).click();
		
		
		// ignore
		String myAction = "";
		String myData = "";
		int colCount = MainScript.sheet.getRow(0).getLastCellNum();
		
		// Filling data
		int rowNum = MainScript.getRowIndex(thisClassName);
		for(int i = 1; i<colCount; i++)
		{
			myAction = MainScript.getActions(0, i);
			myData = MainScript.getActions(rowNum, i);
			
			switch(myAction)
			{
				case "Email":
					MainScript.driver.findElement(By.id(MainScript.getLoc("ForgotPass_email"))).sendKeys(myData);
					Thread.sleep(11000);
					break;
			}
		}
		
		// Reset button
		MainScript.driver.findElement(By.xpath(MainScript.getLoc("ForgotPass_resetBtn"))).click();
		
		// The end
		MainScript.myLogger.info("Failed to validate Forgot password functionality");
		MainScript.data.fail("Failed to validate Forgot password functionality");
		MainScript.takeScreenshot(thisClassName);
		MainScript.closeAndQuit();
		
	}
}
