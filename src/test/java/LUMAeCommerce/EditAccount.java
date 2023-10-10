package LUMAeCommerce;

import java.io.IOException;
import java.lang.invoke.MethodHandles;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

public class EditAccount
{
	public static boolean codeReuse = false;
	
	@Test
	public void editAccountInfo() throws InterruptedException, IOException
	{
		// Initializations
		String thisClassName = MethodHandles.lookup().lookupClass().toString();
		if(!codeReuse)
			MainScript.initializations(thisClassName, codeReuse);
		
		// Login (code reuse)
		Login.codeReuse = true;
		Login.loggingIn();
		
		// Navigation : Home > My Account > Edit Account Information
		MainScript.driver.findElement(By.xpath(MainScript.getLoc("Home_greetingToggle"))).click();
		Thread.sleep(1000);
		MainScript.driver.findElement(By.xpath(MainScript.getLoc("Home_myAcc"))).click();
		MainScript.driver.findElement(By.xpath(MainScript.getLoc("MyAcc_contactInfoEdit"))).click();
		
		// ignore
		String myAction = "";
		String myData = "";
		int colCount = MainScript.sheet.getRow(0).getLastCellNum();
		WebElement myEle;
		
		// Changing details
		MainScript.driver.findElement(By.id(MainScript.getLoc("EditAcc_chEmail"))).click();
		MainScript.driver.findElement(By.id(MainScript.getLoc("EditAcc_chPass"))).click();
		int rowNum = MainScript.getRowIndex(thisClassName);
		for(int i = 1; i<colCount; i++)
		{
			myAction = MainScript.getActions(0, i);
			myData = MainScript.getActions(rowNum, i);
			
			switch(myAction)
			{
				case "FirstName":
					myEle = MainScript.driver.findElement(By.id(MainScript.getLoc("EditAcc_fname")));
					myEle.clear();
					myEle.sendKeys(myData);
					break;
					
				case "LastName":
					myEle = MainScript.driver.findElement(By.id(MainScript.getLoc("EditAcc_lname")));
					myEle.clear();
					myEle.sendKeys(myData);
					break;
					
				case "Email":
					myEle = MainScript.driver.findElement(By.id(MainScript.getLoc("EditAcc_email")));
					myEle.clear();
					myEle.sendKeys(myData);
					break;
				
				case "Password":
					MainScript.driver.findElement(By.id(MainScript.getLoc("EditAcc_curPass"))).sendKeys(myData);
					break;
					
				case "NewPassword":
					MainScript.driver.findElement(By.id(MainScript.getLoc("EditAcc_newPass"))).sendKeys(myData);
					MainScript.driver.findElement(By.id(MainScript.getLoc("EditAcc_cnfPass"))).sendKeys(myData);
					break;
			}
		}
		MainScript.driver.findElement(By.id(MainScript.getLoc("EditAcc_chEmail"))).click();
		MainScript.driver.findElement(By.id(MainScript.getLoc("EditAcc_chPass"))).click();
		MainScript.driver.findElement(By.xpath(MainScript.getLoc("EditAcc_saveBtn"))).click();
		
		
		if(!codeReuse)
		{
			// Validation
			Thread.sleep(2000);
			String val = MainScript.driver.findElement(By.xpath(MainScript.getLoc("Val_editAcc"))).getText();
			if(val.contains(MainScript.getActions(rowNum, colCount-1)))
			{
				MainScript.myLogger.info("Account edited successfully");
				MainScript.data.pass("Account edited successfully");
			}
			else
			{
				MainScript.myLogger.info("Failed to validate 'Edit account'");
				MainScript.data.fail("Failed to validate 'Edit account'");
			}
						
			MainScript.takeScreenshot(thisClassName);
			MainScript.closeAndQuit();
		}
		else
			codeReuse = false;
	}
}
