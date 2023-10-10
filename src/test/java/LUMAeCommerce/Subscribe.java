package LUMAeCommerce;

import java.io.IOException;
import java.lang.invoke.MethodHandles;

import org.openqa.selenium.By;
import org.testng.annotations.Test;

public class Subscribe 
{
	public static boolean codeReuse = false;
	
	@Test
	public void subToLUMA() throws InterruptedException, IOException
	{
		// Initializations
		String thisClassName = MethodHandles.lookup().lookupClass().toString();
		if(!codeReuse)
			MainScript.initializations(thisClassName, codeReuse);
		
		// Unsubscribe (code reuse)
		Unsubscribe.codeReuse = true;
		Unsubscribe.unsubLUMA();
		
		// ignore
		String myAction = "";
		String myData = "";
		int colCount = MainScript.sheet.getRow(0).getLastCellNum();
		
		// Filling data
		Thread.sleep(2000);
		int rowNum = MainScript.getRowIndex(thisClassName);
		for(int i = 1; i<colCount; i++)
		{
			myAction = MainScript.getActions(0, i);
			myData = MainScript.getActions(rowNum, i);
			switch(myAction)
			{
				case "Email":
					MainScript.driver.findElement(By.id(MainScript.getLoc("Home_subTxFld"))).sendKeys(myData);
					break;
			}
			
		}
		
		// Subscribe button
		MainScript.driver.findElement(By.xpath(MainScript.getLoc("Home_subBtn"))).click();
		
		if(!codeReuse)
		{
			// Validation
			String val = MainScript.driver.findElement(By.xpath(MainScript.getLoc("Val_sub"))).getText();
			if(val.contains(MainScript.getActions(rowNum, colCount-1)))
			{
				MainScript.myLogger.info("Subscribed successfully");
				MainScript.data.pass("Subscribed successfully");
			}
			else
			{
				MainScript.myLogger.info("Failed to validate Subscription");
				MainScript.data.fail("Failed to validate Subscription");
			}
			
			MainScript.takeScreenshot(thisClassName);
			MainScript.closeAndQuit();
		}
		else
			codeReuse = false;
	}
}
