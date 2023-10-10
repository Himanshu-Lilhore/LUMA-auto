package LUMAeCommerce;

import java.io.IOException;
import java.lang.invoke.MethodHandles;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

public class Unsubscribe
{
	public static boolean codeReuse = false;
	
	@Test
	public static void unsubLUMA() throws IOException, InterruptedException
	{
		// Initializations
		String thisClassName = MethodHandles.lookup().lookupClass().toString();
		if(!codeReuse)
			MainScript.initializations(thisClassName, codeReuse);
		
		// Logging in (code reuse)
		Login.codeReuse = true;
		Login.loggingIn();
		
		// Navigation : Home > My Account > Newsletter subscription
		MainScript.driver.findElement(By.xpath(MainScript.getLoc("Home_greetingToggle"))).click();
		MainScript.driver.findElement(By.xpath(MainScript.getLoc("Home_myAcc"))).click();
		MainScript.driver.findElement(By.xpath(MainScript.getLoc("MyAcc_newsletterEdit"))).click();
		
		// Unsubscribing
		WebElement cBox = MainScript.driver.findElement(By.id(MainScript.getLoc("Newsletter_sub")));
		if(cBox.isSelected())
			cBox.click();
		MainScript.driver.findElement(By.xpath(MainScript.getLoc("Newsletter_save"))).click();
		
		if(!codeReuse)
		{
			// Validation
			int colCount = MainScript.sheet.getRow(0).getLastCellNum();
			int rowNum = MainScript.getRowIndex(thisClassName);
			String val = MainScript.driver.findElement(By.xpath(MainScript.getLoc("Val_unsub"))).getText();
			if(val.contains(MainScript.getActions(rowNum, colCount-1)))
			{
				MainScript.myLogger.info("Unsubscribed successfully");
				MainScript.data.pass("Unsubscribed successfully");
			}
			else
			{
				MainScript.myLogger.info("Failed to validate Unsubscribe");
				MainScript.data.fail("Failed to validate Unsubscribe");
			}
			
			MainScript.takeScreenshot(thisClassName);
			MainScript.closeAndQuit();
		}
		else
			codeReuse = false;
	}
}
