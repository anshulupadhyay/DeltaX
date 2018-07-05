package com.deltax.automation.uitask.RegistrationPage;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.deltax.automation.utils.MainHelper;

public class RegistrationPageTask{
	
	MainHelper helper;
	public static final Logger logger = Logger.getLogger(RegistrationPageTask.class);
	
	public RegistrationPageTask() {
		helper = new MainHelper();
		helper.loadConfigFile("RegistrationPage");
	}
	
	/**
	 * Returns the title of registration page
	 * @return
	 */
	public String getTitle() {
		return helper.getPageTitle();
	}
	
	//form header
	public String getFormName() {
		return helper.getElement("formname").getText();
	}
	
	//first name validation
	public String getFirstName() {
		return helper.getElement("firstname").getText();
	}
	
	public void enterFirstName(String firstName) {
		helper.getElement("firstnameinputbox").clear();
		helper.getElement("firstnameinputbox").sendKeys(firstName);
	}
	
	public String getfirstNameValue() {
		return helper.getElement("firstnamevalue").getText();
		
	}
	
	public String getFirstNameLengthError() {
		return helper.getElement("firstnamelengtherror").getText();
	}
	
	public String getFirstNameEmptryError() {
		return helper.getElement("firstnameemptyerror").getText();
	}
	
	//last name validation
	public String getLastName() {
		return helper.getElement("lastname").getText();
	}
	
	public void enterLastName(String lastName) {
		helper.getElement("lastnameinputbox").clear();
		helper.getElement("lastnameinputbox").sendKeys(lastName);
	}
	
	public String getLastNameValue() {
		return helper.getElement("lastnameinputbox").getText();
	}
	
	public String getLastNameLengthError() {
		return helper.getElement("lastnamelengtherror").getText();
	}
	
	public String getLastNameEmptryError() {
		return helper.getElement("lastnameemptyerror").getText();
	}
	
	//username validations
	public String getUserName() {
		return helper.getElement("username").getText();
	}
	
	public void enterUserName(String userName) {
		helper.getElement("usernameinputbox").clear();
		helper.getElement("usernameinputbox").sendKeys(userName);
	}
	
	public String getUserNameLengthError() {
		return helper.getElement("usernamelengtherror").getText();
	}
	
	public String getUserNameEmptyError() {
		return helper.getElement("usernameemptyerror").getText();
	}
	
	//password validations
	public String getPassword() {
		return helper.getElement("password").getText();
	}
	
	public void enterPassword(String userName) {
		helper.getElement("passwordinputbox").clear();
		helper.getElement("passwordinputbox").sendKeys(userName);
	}
	
	public String getPasswordLengthError() {
		return helper.getElement("passwordlengtherror").getText();
	}
	
	public String getPasswordEmptryError() {
		return helper.getElement("passwordemptyerror").getText();
	}
	
	//confirm password validations
	public String getConfirmPassword() {
		return helper.getElement("confirmpassword").getText();
	}
	
	public void enterConfirmPassword(String userName) {
		helper.getElement("confirmpasswordinputbox").clear();
		helper.getElement("confirmpasswordinputbox").sendKeys(userName);
	}
	
	public String getConfirmLengthError() {
		return helper.getElement("confirmpasswordlengtherror").getText();
	}
	
	public String getConfirmEmptryError() {
		return helper.getElement("confirmpasswordemptyerror").getText();
	}
	
	//email validations
	public String getEmail() {
		return helper.getElement("email").getText();
	}
	
	public void enterEmail(String userName) {
		helper.getElement("emailinputbox").clear();
		helper.getElement("emailinputbox").sendKeys(userName);
	}
	
	public String getEmailValue() {
		return 	helper.getElement("emailinputbox").getText();
	}
	
	public String getEmailLengthError() {
		return helper.getElement("emaillengtherror").getText();
	}
	
	public String getEmailEmptryError() {
		return helper.getElement("emailemptyerror").getText();
	}
	
	//Contact No validations
	public String getContactNo() {
		return helper.getElement("contactno").getText();
	}
	
	public void enterContactNo(String userName) {
		helper.getElement("contactnoinputbox").clear();
		helper.getElement("contactnoinputbox").sendKeys(userName);
	}
	
	public String getContactValue() {
		return helper.getElement("contactnoinputbox").getText();
	}
	
	public String getContactNoLengthError() {
		return helper.getElement("contactlengtherror").getText();
	}

	public void clickSubmitButton() {
		helper.clickOnElement("submitbutton");
	}
	
	public String getDepartmentName() {
		return helper.getElement("department").getText();
	}
	
	public List<String> getListOfDepartments(){
		List<String> list = new ArrayList<String>();
		
		for(WebElement e : helper.getAllDropdownOptions(helper.getElement("departmentselectoption"))) 
			list.add(e.getText());
				
		return list;
	}
	
	public String getSelectedDepartmentName() {
		return helper.firstSelectedDropDownOption(helper.getElement("departmentselectoption"));
	}
	
	public void selectDepartment(String departmentName) {
		helper.selectFromDropDown(helper.getElement("departmentselectoption"), departmentName);
	}
	
	public String getSuccessfulRegistrationMessage() {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		MainHelper.wait.until(ExpectedConditions.elementToBeClickable(helper.getElement("successmessage")));
		//logger.info("Success message : " + helper.getElement("successmessage").getText().trim());

		return helper.getElement("successmessage").getText().trim();
	}
	
	public boolean submitButtonEnabled() {
		try {
			if(helper.getElement("submitbutton").getAttribute("disabled") == null)
				return true;
		}catch(NoSuchElementException nse) {
			throw new IllegalArgumentException("Attribute disabled is not available");
		}
		
		return false;
	}
	public static void main(String[] args) {
		
		RegistrationPageTask page = new RegistrationPageTask();
		
		/*logger.info(page.getFirstName());
		logger.info(page.getLastName());
		logger.info(page.getFormName());
		logger.info(page.getUserName());
		page.enterFirstName("test");
		page.enterLastName("a");
		logger.info("First name value is " + page.getfirstNameValue());
		
		logger.info(page.getDepartmentName());
		page.getListOfDepartments();
		logger.info(page.getSelectedDepartmentName());
		//page.selectDepartment("test");
		page.selectDepartment("Engineering");
		logger.info(page.getSelectedDepartmentName());*/
	}
	
}
