package com.qa.freeCRM.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.qa.freeCRM.base.TestBase;


public class MyUtility 
{

	public static int IMPLICIT_WAIT = 60;
	public static int PAGE_LOAD_WAIT = 60;
	public static JavascriptExecutor jsObj;
	public static Actions actObj;
	
	 public static String sourceFilePath = "C://Users//HK-SONY//Selenium_Eclipse_Workspace//FreeCRM_NaveenAutomation_POM//src//main//java//com//crm//qa//testdata//FreeCRM_DataDriven_Sheet.xlsx";
	 public static XSSFWorkbook workbook;
	 public static XSSFSheet sheet1;
	 public static FileInputStream fis;
	 public static FileOutputStream fos;
	 public static File srcFile;
	 public static short lastCellNo;
	 public static String data;
	 public static int lastRow; 
	 public static XSSFCell cell;
	 
	
	 
	public static int getTotalRowCount(String sheetName)
	{
		sheet1 = workbook.getSheet(sheetName);
		lastRow = sheet1.getLastRowNum();
		//System.out.println("|=== Method | getTotalRowCount() | Pass | Return <"+lastRow+">===|");
		return lastRow;
	}
	
	
	public static String getCellData(String sheetName, int row, int column) 
	{
		sheet1 = workbook.getSheet(sheetName);
		cell = sheet1.getRow(row).getCell(column);
		cell.setCellType(CellType.STRING);
		String data = cell.getStringCellValue();
		//System.out.println("#getCellData() = " +data);
		return data;
	}
	
	
	
	public static int getTotalColumnCount(String sheetName)
	{
		sheet1 = workbook.getSheet(sheetName);
		lastCellNo =	sheet1.getRow(1).getLastCellNum();
		//System.out.println("|=== Method | getTotalColumnCount() | Pass | Return <"+lastCellNo+">===|");
		return lastCellNo;
	}
	
	
	
	
	public static Object[][]  getTestData(String sheetName)
	{
		try {
			srcFile = new File(sourceFilePath);
			fis = new FileInputStream(srcFile);
			workbook = new XSSFWorkbook(fis);
			System.out.println("Pass !!! === Method | readExcel() | Pass | Return Void ===|");
		}  
	catch (Exception e) 
	{
		System.out.println("\n===Exception Occured-->"+e.getMessage());
		e.printStackTrace();
	}
		
		int rowCount = MyUtility.getTotalRowCount("Contacts_TestDataSheet");
		System.out.println("-----ROW COUNT----"+rowCount); // 2
		
		int columnCount = MyUtility.getTotalColumnCount("Contacts_TestDataSheet");
		System.out.println("-----COLUMN COUNT----"+columnCount);
	
		
		Object[][] data = new Object[rowCount][columnCount];  // Object[2][6]
		System.out.println("===> "+data);
		
//		for (int rowNo = 0; rowNo < rowCount; rowNo++) 
//		{
//			for(int k=0; k<columnCount; k++)
//				{
//					data[rowNo][k] = sheet1.getRow(rowNo+1).getCell(k).toString();
//					System.out.println(" Data at cell " +rowNo+k+" is "+data[rowNo][k]);  // 10,11,12,13,14,15,16 | 20,21,22,23,24,25,26 | 30,31,32,33,34,35,36
// 				}
//		}
		
		
		
		for (int rowNo = 0; rowNo < rowCount; rowNo++) 
		{
			for(int k=0; k<columnCount; k++)
				{
					data[rowNo][k] = sheet1.getRow(rowNo+1).getCell(k).toString();
					System.out.println(" Data at cell " +rowNo+k+" is "+data[rowNo][k]);  // 10,11,12,13,14,15,16 | 20,21,22,23,24,25,26 | 30,31,32,33,34,35,36
 				}
		}
		
		
		return data;
		
	}
	
	
	
	
 	public static void scrollDown(int verticalPixels)
	{
		jsObj = (JavascriptExecutor)
		jsObj.executeScript("scroll(0, "+verticalPixels+")");
	}
	
	public static void clickElementByAction(WebElement ele) 
	{
		actObj = new Actions(TestBase.driver);
		//TestUtil.flashElement(ele);
		actObj.moveToElement(ele).click().build().perform();
	}
	
	public static void sendKeysByAction(WebElement ele, String text)
	{
		actObj = new Actions(TestBase.driver);
		actObj.moveToElement(ele).click().sendKeys(text).build().perform();
	}

	public static void flashElement(WebElement ele) throws InterruptedException
	{
		jsObj = (JavascriptExecutor)TestBase.driver;
		
		String elementDefautbgColor = ele.getCssValue("background-color");
		//System.out.println("-----elementDefautbgColor of WebElement is "+elementDefautbgColor);
		
		jsObj.executeScript("arguments[0].setAttribute('style' , 'background: yellow; border: 2px solid red;'); ", ele);
		Thread.sleep(500);
		jsObj.executeScript("arguments[0].setAttribute('style' , 'backgroundColor= "+elementDefautbgColor+"'); ", ele);
	
	}

	
}
