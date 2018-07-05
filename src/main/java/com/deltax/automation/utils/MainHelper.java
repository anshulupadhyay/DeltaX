package com.deltax.automation.utils;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;

public class MainHelper {

	WebDriver driver;
	LoadDriver loadDriver;
	public static FluentWait<WebDriver> wait;
	public Element config = null;
	public Document configDoc = null;
	public static final Logger logger = Logger.getLogger(MainHelper.class);
	
	FileValidation fileValidation = new FileValidation();
	public static String uiElementLocatorBaseDirectory = ".//src/main/resources/com/deltax/automation/uielementlocators/";
	
	public MainHelper() {
		PropertyConfigurator.configure("log4j.properties");
		
		/*if(driver==null) {
			loadDriver = new LoadDriver();
			driver = LoadDriver.getDriver();
		}*/
		driver = LoadDriver.getDriver();

		wait = new FluentWait<WebDriver>(driver)
				.withTimeout(60, TimeUnit.SECONDS)
				.pollingEvery(5, TimeUnit.SECONDS)
				.ignoring(NoSuchElementException.class);
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='contact_form']/fieldset/legend/center/h2/b")));
		
		logger.info("Registration page loaded");
	} 
	
	public MainHelper(String browser) {
		PropertyConfigurator.configure("log4j.properties");
		
	/*	if(browser !=null)
		{			
			close();
			loadDriver = new LoadDriver(browser);
		}*/
		driver = LoadDriver.getDriver();

		wait = new FluentWait<WebDriver>(driver)
				.withTimeout(60, TimeUnit.SECONDS)
				.pollingEvery(5, TimeUnit.SECONDS)
				.ignoring(NoSuchElementException.class);
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='contact_form']/fieldset/legend/center/h2/b")));
		
		logger.info("Registration page loaded");
	}
	
	/**
	 * Load the config file using the class name and return the root element
	 * @param className - it is the class name whose config file needs to be loaded
	 */
	public void loadConfigFile(String className) {
		
		try {
			File baseDirectory = new File(uiElementLocatorBaseDirectory);
			
			File configFile = fileValidation.getConfigFile(baseDirectory, className);
			if(configFile!=null) {
				logger.info(configFile.getName());
				configDoc = (new SAXBuilder().build(configFile)); 
			}
			else
				logger.info("File not found " + className + "Config.xml " + "at location "+ uiElementLocatorBaseDirectory);
			
		}catch(Exception e) {
			e.getMessage();
		}
		
		config = configDoc.getRootElement();
				
	}
	
	/**
	 * This method will check if the element exist in the config file 
	 * @param Classname - class name whose config file is to be loaded
	 * @param Element - search the element exist the in the config file loaded based on the class name
	 * @return
	 */
	public boolean CheckElementExistinConfig(String Classname,String Element)
	{
		try{
			File F = new File(uiElementLocatorBaseDirectory);
			File ConfigFile=fileValidation.getConfigFile(F, Classname);
			
			Document configDocLocal = (new SAXBuilder()).build(ConfigFile);
			Element configLocal = configDocLocal.getRootElement();
			Element EletoFind = configLocal.getChild("elements").getChild(Element); 
			if(EletoFind!=null)
				return true;
			else
				return false;
		}
		catch(IOException IOE)
		{
			System.out.println(IOE.getMessage());
		}
		catch(JDOMException JDE)
		{
			System.out.println(JDE.getMessage());
		}
		return false;
		
	}
	
	/**
	 * Return the Web Element of the given element
	 * @param elementName
	 * @return
	 */
	public WebElement getElement(String elementName) {
		
		WebElement findElement = null;
		String findBy ;
		
		try {
			
			Element elementToFind = config.getChild("elements").getChild(elementName);
			
			if(elementToFind != null) {
				findBy = elementToFind.getAttribute("findby").getValue();
				
				if(findBy != null) {
					if(findBy.equalsIgnoreCase("id"))
						findElement = driver.findElement(By.id(elementToFind.getText()));
					if(findBy.equalsIgnoreCase("xpath")) 
						findElement = driver.findElement(By.xpath(elementToFind.getText()));  
					if(findBy.equalsIgnoreCase("classname")) 
						findElement = driver.findElement(By.className(elementToFind.getText()));  
					if(findBy.equalsIgnoreCase("css")) 
						findElement = driver.findElement(By.cssSelector(elementToFind.getText())); 
					if(findBy.equalsIgnoreCase("linktext")) 
						findElement = driver.findElement(By.linkText(elementToFind.getText())); 
					if(findBy.equalsIgnoreCase("name"))
						findElement = driver.findElement(By.name(elementToFind.getText())); 
			
				}
				
				/*Actions action = new Actions(driver);
				if(!findElement.isDisplayed())
					action.moveToElement(findElement).perform();*/
			}else {
				logger.info(elementName + " element is not present in the config file");
			}
		}catch(NoSuchElementException NSE) {
			logger.info(NSE.getMessage());
		}catch (Exception e) {
			logger.info(e.getMessage());
		}
		
		return findElement;
	}
	
	/**
	 * Changes the implicitly wait time
	 * @param timeInSecond
	 */
	public void setDelay(long timeInSecond) {
		driver.manage().timeouts().implicitlyWait(timeInSecond, TimeUnit.SECONDS);
	}

	
	/**
	 * Click on the given webelement
	 * @param elementName
	 */
	public void clickOnElement(String elementName) {
		Actions action = new Actions(driver);
		action.click(getElement(elementName)).perform();
	}
	
	/**
	 * Return the current URL
	 * @return
	 */
	public String getCurrentURL() {
		return driver.getCurrentUrl();
	}
	
	/**
	 * Returns the title of current Page
	 * @return
	 */
	public String getPageTitle() {
		return driver.getTitle().toString();
	}
	
	/**
	 * Returns the List of WebElements 
	 * @param elementName
	 * @return
	 */
	public List<WebElement> getListOfElements(String elementName){
		
		List<WebElement> findElement = null;
		String findBy ;
		
		try {
			
			Element elementToFind = config.getChild("elements").getChild(elementName);
			
			if(elementToFind != null) {
				findBy = elementToFind.getAttribute("findby").getValue();
				
				if(findBy != null) {
					if(findBy.equalsIgnoreCase("id"))
						findElement = driver.findElements(By.id(elementToFind.getText()));
					if(findBy.equalsIgnoreCase("xpath")) 
						findElement = driver.findElements(By.xpath(elementToFind.getText()));  
					if(findBy.equalsIgnoreCase("classname")) 
						findElement = driver.findElements(By.className(elementToFind.getText()));  
					if(findBy.equalsIgnoreCase("css")) 
						findElement = driver.findElements(By.cssSelector(elementToFind.getText())); 
					if(findBy.equalsIgnoreCase("linktext")) 
						findElement = driver.findElements(By.linkText(elementToFind.getText())); 
					if(findBy.equalsIgnoreCase("name"))
						findElement = driver.findElements(By.name(elementToFind.getText())); 
			
				}

			}else {
				logger.info(elementName + " element is not present in the config file");
			}
		}catch(NoSuchElementException NSE) {
			logger.info(NSE.getMessage());
		}catch (Exception e) {
			logger.info(e.getMessage());
		}
		
		return findElement;		
	}
	

	public void selectFromDropDown(WebElement webElement, String option) {
		
		Select select = new Select(webElement);
		try {
			select.selectByVisibleText(option);
		}catch(Exception e) {
			logger.info(option + " option is not available in dropdown list");
		}
		
	}
	
	public List<WebElement> getAllDropdownOptions(WebElement element) {
		Select select = new Select(element);
		return select.getOptions();
	}
	
	public String firstSelectedDropDownOption(WebElement webElement) {
		Select select = new Select(webElement);
		return select.getFirstSelectedOption().getText();
	}
	
	public void quit() {
		driver.quit();
	}
	
	public void close() 
	{
		if(driver !=null)
		{
			driver.close();
			driver=null;
				 
		}
	}
	
	public static void main(String[] args) {
		MainHelper mh = new MainHelper("Chrome");
		mh.loadConfigFile("RegistrationPage");
	}



	
}
