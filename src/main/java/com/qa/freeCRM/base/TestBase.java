package com.qa.freeCRM.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class TestBase {

	
public Properties prop;
public static WebDriver driver;
public String filePath = "C:\\Users\\HK-SONY\\Selenium_Eclipse_Workspace\\FreeCRM_KeywordDriven\\src\\main\\java\\com\\qa\\freeCRM\\config\\config.properties";
public FileInputStream fis;



	public WebDriver init_Launch(String browserName)
{
	if(browserName.equalsIgnoreCase("chrome"))
	{
		driver = new ChromeDriver();
	}
	if(browserName.equalsIgnoreCase("firefox"))
	{
		driver = new FirefoxDriver();
	}
	
	driver.manage().deleteAllCookies();
	driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
	driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
	return driver;
	
}
	
	
	
	public Properties init_Property()
	{
		prop = new Properties();
		try
		{			
			File file = new File(filePath);
			fis = new FileInputStream(file);
			prop.load(fis);
			
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		return prop;
	}
	
	
	
}
