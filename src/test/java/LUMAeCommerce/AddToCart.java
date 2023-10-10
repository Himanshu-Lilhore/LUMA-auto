package LUMAeCommerce;

import java.io.IOException;
import java.lang.invoke.MethodHandles;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.Test;

public class AddToCart
{
	public static boolean codeReuse = false;
	
	@Test
	public static void add2Cart() throws IOException, InterruptedException
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
		MainScript.driver.findElements(By.xpath(MainScript.getLoc("Jackets_first"))).get(0).click();
		Thread.sleep(2000);
		MainScript.driver.findElement(By.xpath(MainScript.getLoc("Product_XS"))).click();
		MainScript.driver.findElement(By.xpath(MainScript.getLoc("Product_black"))).click();
		MainScript.driver.findElement(By.id(MainScript.getLoc("Product_add2CartBtn"))).click();
		
		if(!codeReuse)
		{
			// Validation
			int colCount = MainScript.sheet.getRow(0).getLastCellNum();
			int rowNum = MainScript.getRowIndex(thisClassName);
			
			Thread.sleep(2000);
			MainScript.driver.findElement(By.xpath(MainScript.getLoc("Home_cart"))).click();
			MainScript.driver.findElement(By.xpath(MainScript.getLoc("Home_cart_seeDetails"))).click();
			String size = MainScript.driver.findElements(By.xpath(MainScript.getLoc("Home_cart_values"))).get(0).getText();
			String color = MainScript.driver.findElements(By.xpath(MainScript.getLoc("Home_cart_values"))).get(1).getText();
			
			String val = size+color;
			if(val.equals(MainScript.getActions(rowNum, colCount-1)))
			{
				MainScript.myLogger.info("Item added to cart successfully");
				MainScript.data.pass("Item added to cart successfully");
			}
			else
			{
				MainScript.myLogger.info("Failed to validate 'Add to cart'");
				MainScript.data.fail("Failed to validate 'Add to cart'");
			}
			
			MainScript.takeScreenshot(thisClassName);
			MainScript.closeAndQuit();
		}
		else
			codeReuse = false;
	}
}
