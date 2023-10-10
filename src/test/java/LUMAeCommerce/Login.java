package LUMAeCommerce;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.Random;

import org.openqa.selenium.By;
import org.testng.annotations.Test;

public class Login
{
	public static boolean codeReuse = false;
	
	@Test
	public static void loggingIn() throws IOException, InterruptedException
	{
		// Initializations
		String thisClassName = MethodHandles.lookup().lookupClass().toString();
		if(!codeReuse)
			MainScript.initializations(thisClassName, codeReuse);
		
		
		// Navigating to Login page
		MainScript.driver.findElement(By.xpath(MainScript.getLoc("Home_signIn"))).click();
		
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
					MainScript.driver.findElement(By.id(MainScript.getLoc("Login_email"))).sendKeys(myData);
					break;
				
				case "Password":
					MainScript.driver.findElement(By.id(MainScript.getLoc("Login_pass"))).sendKeys(myData);
					break;
			}
		}
		
		// SignIn button
		MainScript.driver.findElement(By.id(MainScript.getLoc("Login_signIn"))).click();

		
		if(!codeReuse)
		{
			// Validation
			Thread.sleep(3000);
			String val = MainScript.driver.findElement(By.xpath(MainScript.getLoc("Val_greet"))).getText();
			if(val.equals(MainScript.getActions(rowNum, colCount-1)))
			{
				MainScript.myLogger.info("Logged in successfully");
				MainScript.data.pass("Logged in successfully");
			}
			else
			{
				MainScript.myLogger.info("Failed to validate login");
				MainScript.data.fail("Failed to validate login");
			}
			
			MainScript.takeScreenshot(thisClassName);
			MainScript.closeAndQuit();
		}
		else
			codeReuse = false;
	}
}
