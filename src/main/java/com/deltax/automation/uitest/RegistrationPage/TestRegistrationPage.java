package com.deltax.automation.uitest.RegistrationPage;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.deltax.automation.uitask.RegistrationPage.RegistrationPageTask;
import com.deltax.automation.utils.CommonDataProvider;
import com.deltax.automation.utils.DataProviderArguments;
import com.deltax.automation.utils.LoadDriver;

public class TestRegistrationPage {

	RegistrationPageTask task ;
	//MainHelper helper ;
	public static final Logger logger = Logger.getLogger(TestRegistrationPage.class);
	LoadDriver loaddriver;
	
	@BeforeMethod
	public void initialize() {
		
		 loaddriver = new LoadDriver();
		task = new RegistrationPageTask();

	}
	
	//Validate the Registration form options
	@Test(testName = "Validate the options in the registration form", enabled = true)
	public void validateRegistrationForm() {
		
		assert task.getFormName().equals("Registration Form") : "Failed to validate the registration form";
		assert task.getFirstName().equals("First Name") : "Failed to validate First Name field";
		assert task.getLastName().equals("Last Name") : "Failed to validate Last Name field";
		assert task.getDepartmentName().equals("Department / Office") : "Failed to validate Department field";
		assert task.getUserName().equals("Username *") : "Failed to validate Username field";
		assert task.getPassword().equals("Password *") : "Failed to validate Password field";
		assert task.getConfirmPassword().equals("Confirm Password") : "Failed to validate Confirm Password field";
		assert task.getEmail().equals("E-Mail") : "Failed to validate E-Mail field";
		assert task.getContactNo().equals("Contact No.") : "Failed to validate Contact No. field";
		
	}
	
	//An un-registered user should be able to register to the site with the mandatory + desirable parameters
	
	@Test(testName = "An un-registered user should be able to register to the site with the mandatory and desirable parameters", dataProvider = "CommonCreateDelete", dataProviderClass=CommonDataProvider.class, enabled = true)
	@DataProviderArguments({"filePath=RegistrationPage/registrationpage.csv","testName=registeruser"})
	public void TestRegistrationPage(Map<String, String> testData) {
		
		logger.info("********************************************		Starting Test TestRegistrationPage		********************************************");
		
		//enter data in all fields
		if(testData.containsKey("firstname"))
			task.enterFirstName(testData.get("firstname"));
		if(testData.containsKey("lastname"))
			task.enterLastName(testData.get("lastname"));		
		if(testData.containsKey("department"))
			task.selectDepartment(testData.get("department"));
		if(testData.containsKey("username"))
			task.enterUserName(testData.get("username"));
		if(testData.containsKey("passwd"))
				task.enterPassword(testData.get("passwd"));
		if(testData.containsKey("confirmpasswd"))
				task.enterConfirmPassword(testData.get("confirmpasswd"));
		if(testData.containsKey("email"))
				task.enterEmail(testData.get("email"));		
		if(testData.containsKey("contactno"))
			task.enterContactNo(testData.get("contactno"));
		
				
		task.clickSubmitButton();
		assert task.getSuccessfulRegistrationMessage().equals("Thanks") : "Failed to validate the successful registration message";
		
	}
	
	//An error message should be displayed when mandatory parameters are not passed
	
	@Test(testName = "Un-registered user gets error when mandatory fields are not provided or the length of input parameters is not as defined", dataProvider = "CommonCreateDelete", dataProviderClass=CommonDataProvider.class, enabled = true)
	@DataProviderArguments({"filePath=RegistrationPage/registrationpage.csv","testName=invalidmandatoryparams"})
	public void testRegistrationFormWithInvalidInput(Map<String, String> testData) {
		
		logger.info("********************************************		Starting Test testRegistrationFormWithInvalidInput		********************************************");
		
		//enter data in all fields
		if(testData.containsKey("firstname"))
			task.enterFirstName(testData.get("firstname"));
		if(testData.containsKey("lastname"))
			task.enterLastName(testData.get("lastname"));		
		if(testData.containsKey("department"))
			task.selectDepartment(testData.get("department"));
		if(testData.containsKey("username"))
			task.enterUserName(testData.get("username"));
		if(testData.containsKey("passwd"))
				task.enterPassword(testData.get("passwd"));
		if(testData.containsKey("confirmpasswd"))
				task.enterConfirmPassword(testData.get("confirmpasswd"));
		if(testData.containsKey("email"))
				task.enterEmail(testData.get("email"));		
		if(testData.containsKey("contactno"))
			task.enterContactNo(testData.get("contactno"));
		
		boolean submitButtonEnabled = task.submitButtonEnabled();
		
		task.clickSubmitButton();
		
		if(testData.containsKey("firstname")) {
			if(testData.get("firstname").length() > 0 && testData.get("firstname").length() < 2 )
				assert task.getFirstNameLengthError().equals("This value is not valid") : "Failed to assert invalid error message";
		}		
		else if(submitButtonEnabled)
			assert task.getFirstNameEmptryError().equals("Please enter your First Name") : "Failed to validate the error message";
		
		if(testData.containsKey("lastname")) {
			if(testData.get("lastname").length() > 0 && testData.get("lastname").length() < 2 )
				assert task.getLastNameLengthError().equals("This value is not valid") : "Failed to assert invalid error message";
		}
		else if(submitButtonEnabled)
			assert task.getLastNameEmptryError().equals("Please enter your Last Name") : "Failed to validate the error message";
					
		if(testData.containsKey("department"))
			task.selectDepartment(testData.get("department"));
		
		if(testData.containsKey("username")) {
			if(testData.get("username").length() > 0 && testData.get("username").length() < 8 )
				assert task.getUserNameLengthError().equals("This value is not valid") : "Failed to assert invalid error message";
		}
		else if(submitButtonEnabled)
			assert task.getUserNameEmptyError().equals("Please enter your Username") : "Failed to validate the error message";
							
		if(testData.containsKey("passwd")) {
			if(testData.get("passwd").length() > 0 && testData.get("passwd").length() < 8 )
				assert task.getPasswordLengthError().equals("This value is not valid") : "Failed to assert invalid error message";	
		}
		else if(submitButtonEnabled)
			assert task.getPasswordEmptryError().equals("Please enter your Password") : "Failed to validate the error message";
			
		if(testData.containsKey("confirmpasswd")) {
			if(testData.get("confirmpasswd").length() > 0 && testData.get("confirmpasswd").length() < 8 )
				assert task.getConfirmLengthError().equals("This value is not valid") : "Failed to assert invalid error message";
			
		}
		else 
			assert task.getConfirmEmptryError().equals("Please confirm your Password") : "Failed to validate the error message";
			
		if(testData.containsKey("email")) {
			if(testData.get("email").length() > 0 && testData.get("email").length() < 8 )
				assert task.getEmailLengthError().equals("This value is not valid") : "Failed to assert invalid error message";
		}
		else if(submitButtonEnabled)
			assert task.getEmailEmptryError().equals("Please enter your Email Address") : "Failed to validate the error message";
			
		if(testData.containsKey("contactno"))
			if(testData.get("contactno").length() != 10)
				assert task.getContactNoLengthError().equals("This value is not valid") : "Failed to assert invalid error message";
		
	}
	
	//Verify the list of Department in drop down items  
	@Test(testName = "Verify the list of Department in drop down items", dataProvider = "CommonCreateDelete", dataProviderClass=CommonDataProvider.class, enabled = true)
	@DataProviderArguments({"filePath=RegistrationPage/registrationpage.csv","testName=departmenttype"})
	public void validateDepartmentType(Map<String, String> testData) {
	
		logger.info("********************************************		Starting Test validateDepartmentType		********************************************");

		String[] str = testData.get("department").split(";");
			
		List<String> list = task.getListOfDepartments();
			
		for (int i = 0; i < str.length; i++) {
			assert str[i].equals(list.get(i)) : "Failed to validate the department list";
		}
			
	}
	
	
	@AfterMethod
	public void kill() {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		loaddriver.closeBrowser();
	}
}
