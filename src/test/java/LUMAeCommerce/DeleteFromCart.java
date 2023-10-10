package LUMAeCommerce;

import java.io.IOException;
import java.lang.invoke.MethodHandles;

import org.openqa.selenium.By;
import org.testng.annotations.Test;

public class DeleteFromCart
{
	public static boolean codeReuse = false;
	
	@Test
	public void deleteDelete() throws IOException, InterruptedException
	{
		// Initializations
		String thisClassName = MethodHandles.lookup().lookupClass().toString();
		if(!codeReuse)
			MainScript.initializations(thisClassName, codeReuse);
		
		// Adding to cart (code reuse)
		AddToCart.codeReuse = true;
		AddToCart.add2Cart();
		
		// Opening cart
		Thread.sleep(3000);
		MainScript.driver.findElement(By.xpath(MainScript.getLoc("Home_cart"))).click();
		
		// Removing item from cart
		MainScript.driver.findElement(By.xpath(MainScript.getLoc("Home_rmItem"))).click();
		Thread.sleep(3000);
		MainScript.driver.findElement(By.xpath(MainScript.getLoc("Home_rmOK"))).click();
		
		if(!codeReuse)
		{
			// Validation
			Thread.sleep(3000);
			int colCount = MainScript.sheet.getRow(0).getLastCellNum();
			int rowNum = MainScript.getRowIndex(thisClassName);
			String val = MainScript.driver.findElement(By.xpath(MainScript.getLoc("Val_cartEmpty"))).getText();
			if(val.equals(MainScript.getActions(rowNum, colCount-1)))
			{
				MainScript.myLogger.info("Item Deleted from cart successfully");
				MainScript.data.pass("Item Deleted from cart successfully");
			}
			else
			{
				MainScript.myLogger.info("Failed to validate cart item deletion");
				MainScript.data.fail("Failed to validate cart item deletion");
			}
			
			MainScript.takeScreenshot(thisClassName);
			MainScript.closeAndQuit();
		}
		else
			codeReuse = false;
	}
}
