//		tbObject = new TestBase();
//		prop = tbObject.init_Property();
//		
//		String browser = prop.getProperty("browserName");
//		String applicationUrl = prop.getProperty("applicationUrl");
//		String username = prop.getProperty("username");
//		String password = prop.getProperty("password");
		//System.out.println(browser+application+username+password);
		
//		driver = tbObject.init_Launch("chrome");
//		driver.get(applicationUrl);
//		driver.findElement(By.name("username")).sendKeys(username);
//		driver.findElement(By.name("password")).sendKeys(password);
//		driver.findElement(By.xpath("//div[@class='ui fluid large blue submit button'")).click();


package com.qa.freeCRM.engine;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.freeCRM.base.TestBase;
import com.qa.freeCRM.utility.MyUtility;

public class KeywordEngine {

	TestBase tbObject;
	Properties prop;
	WebDriver driver;
	String testDataSheetPath = "C:/Users/HK-SONY/Selenium_Eclipse_Workspace/FreeCRM_KeywordDriven/src/main/java/com/qa/freeCRM/testData/FreeCRM_KeywordDriven.xlsx"; 
	Workbook workbook;
	XSSFSheet sheet;
	XSSFWorkbook xssfworkbook;
	FileInputStream fis;
	
	TestBase baseObj;
	
	String locatorValue;
	String keywordName;
	String testData;
	String testStep;
	String executionStatus;
	
	WebElement element;
	
	String locatorType;

	
	//String testData1;double testData2;XSSFRichTextString testData3;String testData4;String testData5;
	
	public void startExecution(String sheetName)
	{
		File file = new File(testDataSheetPath);
		try 
		{
			fis = new FileInputStream(file);
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}
		try {
			xssfworkbook = new XSSFWorkbook(file);
		} 
		catch (InvalidFormatException e)
		{
			e.printStackTrace();
		} catch (IOException e) 
		{
			e.printStackTrace();
		}
		sheet = xssfworkbook.getSheet(sheetName);		
		int rowCount = sheet.getLastRowNum();		
		System.out.println("Row count = "+rowCount);
		
		for(int rowNo=2; rowNo<=rowCount; rowNo++)
		{
			executionStatus = sheet.getRow(rowNo).getCell(6).toString().trim();
			
			if(!executionStatus.equalsIgnoreCase("N"))
			{
				keywordName = sheet.getRow(rowNo).getCell(2).toString().trim();
				testData = sheet.getRow(rowNo).getCell(5).toString().trim();
				locatorType = sheet.getRow(rowNo).getCell(3).toString().trim();
				
				if(!locatorType.equalsIgnoreCase("NA"))
				{
					locatorValue = sheet.getRow(rowNo).getCell(4).toString().trim();
				}

				testStep = sheet.getRow(rowNo).getCell(1).getStringCellValue().trim();
				
				System.out.println(rowNo+"#------keywordName | locatorType | locatorValue | testData-----------");
				System.out.println(keywordName+" | "+locatorType+ " | "+locatorValue+" | "+testData);
				System.out.println("______________________________________________________________________________________________");
			
//				System.out.println("KEYWORD at "+rowNo+" is "+keywordName);
//				
//				if(keywordName.isEmpty())
//				{
//					System.out.println("!!!!!!!!!!!!!-----WARNING------!!!!!!!!!!!!! No Keyword found at row number -> "+rowNo+" for step name ->"+testStep);
//				}
								
				switch (keywordName) 
				{
				case "openBrowser":
					baseObj = new TestBase();
					prop = 	baseObj.init_Property();
					String browserName =	prop.getProperty("browserName");
					//System.out.println(browserName);
					driver = baseObj.init_Launch(browserName);
					break;

				case "enterURL":
					driver.get(testData);
					break;	
					
				case "close":
					driver.close();
					break;
					
					
				default:
					break;
				}
				
				
					
				switch (locatorType) 
				{
				case "id":
					break;
					
				case "name":
					element =	 driver.findElement(By.name(locatorValue));
				
					if(keywordName.equalsIgnoreCase("sendKeys"))
					{
						if(testData.isEmpty() || testData.equalsIgnoreCase("na"))
						{
							String testDataFromPropertyFile = prop.getProperty(locatorValue);
							element.sendKeys(testDataFromPropertyFile);
						}
						else
						{
							element.sendKeys(testData);
						}
					}
					break;

				case "xpath":
					element =	 driver.findElement(By.xpath(locatorValue));
					
					if(keywordName.equalsIgnoreCase("sendKeys"))
					{
						element.sendKeys(testData);
					}
					else if(keywordName.equalsIgnoreCase("click"))
					{
						element.click();
						
					}
					else if(keywordName.equalsIgnoreCase("clickElementByAction"))
					{
						MyUtility.clickElementByAction(element);
					}
					
					break;	
					
				default:
					break;
				}

			}
			
		}
	}
}
