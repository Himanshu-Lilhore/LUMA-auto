package LUMAeCommerce;

import java.io.IOException;
import java.lang.invoke.MethodHandles;

import org.openqa.selenium.By;
import org.testng.annotations.Test;

public class WishlistToCart
{
	public static boolean codeReuse = false;
	
	@Test
	public void wishlist2Cart() throws IOException, InterruptedException
	{
		// Initializations
		String thisClassName = MethodHandles.lookup().lookupClass().toString();
		if(!codeReuse)
			MainScript.initializations(thisClassName, codeReuse);
		
		// Adding to wish list (code reuse)
		AddToWishlist.codeReuse = true;
		AddToWishlist.add2Wishlist();
		
		// Add all to cart
		Thread.sleep(1000);
		MainScript.driver.findElement(By.xpath(MainScript.getLoc("Wishlist_addAll"))).click();
		
		if(!codeReuse)
		{
			// Validation
			Thread.sleep(2000);
			int colCount = MainScript.sheet.getRow(0).getLastCellNum();
			int rowNum = MainScript.getRowIndex(thisClassName);
			String val = MainScript.driver.findElement(By.xpath(MainScript.getLoc("Val_w2c"))).getText();
			if(val.contains(MainScript.getActions(rowNum, colCount-1)))
			{
				MainScript.myLogger.info("Product successfully moved from wishlist to cart");
				MainScript.data.pass("Product successfully moved from wishlist to cart");
			}
			else
			{
				MainScript.myLogger.info("Failed to validate 'WishlistToCart'");
				MainScript.data.fail("Failed to validate 'WishlistToCart'");
			}
			
			MainScript.driver.findElement(By.xpath(MainScript.getLoc("Home_cart"))).click();
			MainScript.driver.findElement(By.xpath(MainScript.getLoc("Home_cart_seeDetails"))).click();
			
			MainScript.takeScreenshot(thisClassName);
			MainScript.closeAndQuit();
		}
		else
			codeReuse = false;
	}
}
