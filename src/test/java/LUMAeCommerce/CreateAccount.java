package LUMAeCommerce;

import java.io.IOException;
import java.lang.invoke.MethodHandles;

import org.openqa.selenium.By;
import org.testng.annotations.Test;
import java.util.Random;

public class CreateAccount
{
	public static boolean codeReuse = false;
	
	@Test
	public void newAcc() throws InterruptedException, IOException
	{ 
		// Initializations
		String thisClassName = MethodHandles.lookup().lookupClass().toString();
		if(!codeReuse)
			MainScript.initializations(thisClassName, codeReuse);
		
		// Navigating to create account page
		MainScript.driver.findElement(By.xpath(MainScript.getLoc("Home_createAcc"))).click();
		
		// ignore
		String myAction = "";
		String myData = "";
		int colCount = MainScript.sheet.getRow(0).getLastCellNum();
		Random rand = new Random();
		int randomNo = rand.nextInt(10000, 100000);
		
		// Filling data
		int rowNum = MainScript.getRowIndex(thisClassName);
		for(int i = 1; i<colCount; i++)
		{
			myAction = MainScript.getActions(0, i);
			myData = MainScript.getActions(rowNum, i);
			switch(myAction)
			{
				case "FirstName":
					MainScript.driver.findElement(By.id(MainScript.getLoc("CreateAcc_fname"))).sendKeys(myData);
					break;
				
				case "LastName":
					MainScript.driver.findElement(By.id(MainScript.getLoc("CreateAcc_lname"))).sendKeys(myData);
					break;
				
				case "Email":
					myData = myData + randomNo + "@gmail.com";
					MainScript.driver.findElement(By.id(MainScript.getLoc("CreateAcc_email"))).sendKeys(myData);
					MainScript.email = myData;
					break;
				
				case "Password":
					MainScript.driver.findElement(By.id(MainScript.getLoc("CreateAcc_pass"))).sendKeys(myData);
					MainScript.driver.findElement(By.id(MainScript.getLoc("CreateAcc_cnfPass"))).sendKeys(myData);
					break;
			}
			
		}
		
		// Check-box
		MainScript.driver.findElement(By.id("is_subscribed")).click();
		
		// Create account button
		MainScript.driver.findElement(By.xpath(MainScript.getLoc("CreateAcc_createAcc"))).click();
		
		if(!codeReuse)
		{
			// Validation
			Thread.sleep(2000);
			String val = MainScript.driver.findElement(By.xpath(MainScript.getLoc("CreateAcc_ty"))).getText();
			if(val.equals(MainScript.getActions(rowNum, colCount-1)))
			{
				MainScript.myLogger.info("Account created successfully");
				MainScript.data.pass("Account created successfully");
			}
			else
			{
				MainScript.myLogger.info("Failed to validate account creation");
				MainScript.data.fail("Failed to validate account creation");
			}
			
			MainScript.takeScreenshot(thisClassName);
			MainScript.closeAndQuit();
		}
		else
			codeReuse = false;
	}
}
