package LUMAeCommerce;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.chrome.ChromeDriver;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;


public class MainScript
{
	public static ChromeDriver driver;
	public static XSSFSheet sheet;
	public static String email = "himanshu.email@gmail.com";
	public static String localDir = System.getProperty("user.dir");
	public static Properties p = new Properties();
	public static Logger myLogger;
	public static ExtentSparkReporter reporter;
	public static ExtentReports extent;
	public static ExtentTest data;
	public static boolean reportInitialized = false;
	
	
	public static void log4jInit(String myClassName)
	{
		myLogger = Logger.getLogger(myClassName);
		PropertyConfigurator.configure(localDir + "\\src\\main\\resources\\log4j.properties");
	}
	
	
	public static void initializations(String myClassName, boolean codeReuse) throws IOException
	{
		String className = myClassName.split(" ")[1].split("\\.")[1];
		
		// Instantiations & Initializations
		log4jInit(myClassName);
		createTestReport("Checking " + className + " Functionality");
		getSheet();
		loadLocators();
		
		myLogger.info("Running " + className + " script");
		data.info("Running " + className + " script");
		
		// ChromeDriver setup
		System.setProperty("webdriver.chrome.driver", localDir + "\\src\\test\\java\\LUMAcloud" + "\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("https://magento.softwaretestingboard.com/");
		myLogger.info("Opening the browser");
		data.info("Opening the browser");
	}
	
	
	public static void closeAndQuit() throws InterruptedException, IOException
	{
//		Thread.sleep(3000);
		driver.quit();
		
		myLogger.info("Closed the browser");
		data.info("Closed the browser");
		extent.flush();
	}
	
	
	public static void getSheet() throws IOException
	{
		FileInputStream fis = new FileInputStream(localDir + "\\src\\test\\java\\LUMAcloud\\" + "LUMAdata.xlsx");
		XSSFWorkbook book = new XSSFWorkbook(fis);
		sheet = book.getSheetAt(0);
		
		myLogger.info("Fetched the required sheet from excel file");
		data.info("Fetched the required sheet from excel file");
	}

	
	public static String getActions(int row, int col) {
		String val = sheet.getRow(row).getCell(col).getStringCellValue();
		return val;
	}
	
	
	public static void loadLocators() throws IOException
	{
		FileInputStream fis = new FileInputStream(localDir + "\\src\\test\\java\\LUMAcloud\\LUMAlocators.properties");
		p.load(fis);
		
		myLogger.info("Loaded locators from .properties file");
		data.info("Loaded locators from .properties file");
	}
	
	
	public static String getLoc(String locatorName)
	{
		return p.getProperty(locatorName);
	}
	
	
	private static void reportInit() 
	{
		reporter = new ExtentSparkReporter("reports\\result.html");
		reporter.config().setDocumentTitle("LUMA Report");
		reporter.config().setTheme(Theme.DARK);
		extent = new ExtentReports();
		extent.attachReporter(reporter);
		
		myLogger.info("Extent report initialized");
	}
	
	
	public static void createTestReport(String testName)
	{
		if(!reportInitialized)
		{
			reportInit();
			reportInitialized = true;
		}
		data = extent.createTest(testName).assignAuthor("Himanshu Lilhore").assignDevice("Windows 10");
		
		myLogger.info("New report : " + testName);
		data.info("New report : " + testName);
	}
	
	
	public static int getRowIndex(String myClassName)
	{
		String className = myClassName.split(" ")[1].split("\\.")[1];
		int rowCount = MainScript.sheet.getPhysicalNumberOfRows();
		
		String tcName = "";
		int rowIdx = -1;
		for(rowIdx = 1; rowIdx<rowCount; rowIdx++)
		{
			tcName = MainScript.getActions(rowIdx, 0);
			if(tcName.equals(className))
			{
				break;
			}
		}
		return rowIdx;
	}
	
	
	
	public static void takeScreenshot(String myClassName) throws IOException, InterruptedException
	{
		Thread.sleep(2000);
		File SrcFile=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		String className = myClassName.split(" ")[1].split("\\.")[1];
		String path = localDir + "\\Screenshots\\" + className + ".jpg";
		FileUtils.copyFile(SrcFile, new File(localDir + "\\Screenshots\\" + className + ".jpg"));
		Thread.sleep(2000);
		data.addScreenCaptureFromPath(path, className);
	}
}
