package com.deltax.automation.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.Wait;

import io.github.bonigarcia.wdm.ChromeDriverManager;
import io.github.bonigarcia.wdm.EdgeDriverManager;
import io.github.bonigarcia.wdm.FirefoxDriverManager;
import io.github.bonigarcia.wdm.InternetExplorerDriverManager;

public class LoadDriver {
	
	public static WebDriver driver = null;
	public static final Logger logger = Logger.getLogger(LoadDriver.class);
	public Wait<WebDriver> wait;
	Properties prop = new Properties();
	
	private String defaultBrowser = "Firefox";
	String filePath = "\\src\\main\\resources\\com\\deltax\\automation\\uielementlocators\\properties\\configuration.properties";
	FileInputStream file;

	public LoadDriver() {
		//PropertyConfigurator.configure("log4j.properties");
		
		try {
			file = new FileInputStream(System.getProperty("user.dir")+ filePath);
			logger.info("Loading configuration file " + filePath);
			prop.load(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if(prop.containsKey("browser")) {
			logger.info("Using Browser : " + prop.getProperty("browser"));
			
			this.defaultBrowser=prop.getProperty("browser");
		}
		
		loadWebDriver(defaultBrowser);
		driver.get(getBaseURL().toString());
		
		driver.manage().window().maximize();
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		if(this.defaultBrowser.equalsIgnoreCase("IE"))
			driver.navigate().to("javascript:document.getElementById('overridelink').click()");  
		
	}	

	public LoadDriver(String browser) {
		//PropertyConfigurator.configure("log4j.properties");
		
		loadWebDriver(browser);
		driver.get(getBaseURL().toString());
		
		driver.manage().window().maximize();
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		if(browser.equalsIgnoreCase("IE"))
			driver.navigate().to("javascript:document.getElementById('overridelink').click()");  
		
	}

	private void loadWebDriver(String browser) {
		
		if(browser.equalsIgnoreCase("Firefox"))
		{
			FirefoxDriverManager.getInstance().setup();
			
			FirefoxProfile testprofile = new FirefoxProfile();
			testprofile.setAcceptUntrustedCertificates(true); 
			FirefoxOptions option = new FirefoxOptions();
			option.setProfile(testprofile);

			driver = new FirefoxDriver(option);	 
		}
		if(browser.equalsIgnoreCase("IE"))
		{
			InternetExplorerDriverManager.getInstance().setup();
			driver = new InternetExplorerDriver( );
		}
		if(browser.equalsIgnoreCase("EDGE"))
		{
			//Some issue with using EdgeDriverManager
			System.setProperty("wedriver.edge.driver", System.getProperty("user.dir"));
			driver = new EdgeDriver();
		}
		if(browser.equalsIgnoreCase("Chrome"))
		{
			ChromeDriverManager.getInstance().setup();
				 
			driver =new ChromeDriver();
		}

		//Maximize the browser launched from default size
		driver.manage().window().maximize();  
		
	}
	
	public static WebDriver getDriver() {
		return driver;
	}

	public URL getBaseURL() {
		URL url = null;
		
		try {
			
			url = new URL(prop.getProperty("scheme"), prop.getProperty("url"),"");
			logger.info("url to load : " + url.toString());
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return url;
	}
	
	public void closeBrowser() {
		driver.quit();
	}
	
	public static void main(String[] args) {
		LoadDriver d = new LoadDriver();
		d.getBaseURL();
	}
}
