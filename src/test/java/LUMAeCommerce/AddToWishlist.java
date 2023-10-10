package LUMAeCommerce;

import java.io.IOException;
import java.lang.invoke.MethodHandles;

import org.openqa.selenium.By;
import org.testng.annotations.Test;

public class AddToWishlist
{
	public static boolean codeReuse = false;
	
	@Test
	public static void add2Wishlist() throws IOException, InterruptedException
	{
		// Initializations
		String thisClassName = MethodHandles.lookup().lookupClass().toString();
		if(!codeReuse)
			MainScript.initializations(thisClassName, codeReuse);
		
		// Login (code reuse)
		Login.codeReuse = true;
		Login.loggingIn();
		
		// Adding to cart (Men > Jacket > XS > Black)
		MainScript.driver.findElement(By.xpath(MainScript.getLoc("Home_men"))).click();
		MainScript.driver.findElement(By.xpath(MainScript.getLoc("Men_jackets"))).click();
		MainScript.driver.findElement(By.xpath(MainScript.getLoc("Jackets_first"))).click();
		Thread.sleep(2000);
		MainScript.driver.findElement(By.xpath(MainScript.getLoc("Product_XS"))).click();
		MainScript.driver.findElement(By.xpath(MainScript.getLoc("Product_black"))).click();
		MainScript.driver.findElement(By.xpath(MainScript.getLoc("Product_add2Wishlist"))).click();
		
		if(!codeReuse)
		{
			// Validation
			Thread.sleep(2000);
			int colCount = MainScript.sheet.getRow(0).getLastCellNum();
			int rowNum = MainScript.getRowIndex(thisClassName);
			
			Thread.sleep(3000);
			String val = MainScript.driver.findElement(By.xpath(MainScript.getLoc("Wishlist_prodName"))).getText();
			
			if(val.equals(MainScript.getActions(rowNum, colCount-1)))
			{
				MainScript.myLogger.info("Item added to Wishlist successfully");
				MainScript.data.pass("Item added to Wishlist successfully");
			}
			else
			{
				MainScript.myLogger.info("Failed to validate 'Add to wishlist'");
				MainScript.data.fail("Failed to validate 'Add to wishlist'");
			}
			
			MainScript.takeScreenshot(thisClassName);
			MainScript.closeAndQuit();
		}
		else
			codeReuse = false;
	}
}
