package LUMAeCommerce;

import java.io.IOException;
import java.lang.invoke.MethodHandles;

import org.openqa.selenium.By;
import org.testng.annotations.Test;

public class Logout
{
	public static boolean codeReuse = false;
	
	@Test
	public void loggingOut() throws IOException, InterruptedException
	{
		// Initializations
		String thisClassName = MethodHandles.lookup().lookupClass().toString();
		if(!codeReuse)
			MainScript.initializations(thisClassName, codeReuse);
		
		// Logging in (code reuse)
		Login.codeReuse = true;
		Login.loggingIn();
		
		// Logging out
		MainScript.driver.findElement(By.xpath(MainScript.getLoc("Home_greetingToggle"))).click();
		MainScript.driver.findElement(By.xpath(MainScript.getLoc("Home_signOut"))).click();
		
		
		if(!codeReuse)
		{
			// Validation
			Thread.sleep(2000);
			int colCount = MainScript.sheet.getRow(0).getLastCellNum();
			int rowNum = MainScript.getRowIndex(thisClassName);
			String val = MainScript.driver.findElement(By.xpath(MainScript.getLoc("Val_signout"))).getText();
			if(val.equals(MainScript.getActions(rowNum, colCount-1)))
			{
				MainScript.myLogger.info("Logged out successfully");
				MainScript.data.pass("Logged out successfully");
			}
			else
			{
				MainScript.myLogger.info("Failed to validate logout");
				MainScript.data.fail("Failed to validate logout");
			}
			
			MainScript.takeScreenshot(thisClassName);
			MainScript.closeAndQuit();
		}
		else
			codeReuse = false;
	}
}
