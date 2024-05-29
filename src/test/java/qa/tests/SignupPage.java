package qa.tests;

import base.baseTest;

import base.baseTest;

import static base.baseTest.driver;

import java.time.Duration;
import java.util.Random;

import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;
import pageEvents.LoginPageEvents;
import pageEvents.SignUpPageEvents;
import utils.ElementFetch;
import utils.constants;

public class SignupPage extends baseTest {
	ElementFetch ele = new ElementFetch();
	LoginPageEvents loginpage = new LoginPageEvents();
	SignUpPageEvents signupevents = new SignUpPageEvents();
	

	@Test(description = "Verify user is able to signup with valid credentials")
	public void HappySignup() {
		Random rand = new Random();
		logger.info("Load the Login page");
		loginpage.verifyIfLoginPageIsLoaded();
		logger.info("Click on create account link");
		loginpage.CreateAccountLink();
		logger.info("Enter the merchants details");
		int rndint = rand.nextInt(101);
		String name = "Automation test " + rndint;
		String companyname = "Automation company" + rndint;
		String email = "automaticemail+" + rndint + "@yopmail.com";
		String phone = "123456" + rndint;
		String password = "Faisal@123";
		String confirmpassword = "Faisal@123";
		signupevents.Signup(name, companyname, email, phone, password, confirmpassword);

		String pagesource = driver.getPageSource();
		String SearchText = "created with success";
		signupevents.VerifyifPageLoaded(pagesource, SearchText);
		signupevents.SignupEmail(pagesource, email, companyname, phone, name);
		
	}
	@Test(description = "Verify that user is unable to signup with a duplicated email of existing merchant")
	public void DuplicateMerchantEmailSignup()
	{
		logger.info("Load the Login page");
		loginpage.verifyIfLoginPageIsLoaded();
		logger.info("Click on create account link"); 
		loginpage.CreateAccountLink();
		logger.info("Enter duplicate merchant details");
		signupevents.Signup("duplicate", "duplicated email company", constants.DuplicateMerchantEmail, "234234234", constants.password, constants.Confirmpassword);
		String pagesource = driver.getPageSource();
		String SearchText = "Email address already registered";
		signupevents.VerifyifPageLoaded(pagesource, SearchText);
	}
	
	@Test(description = "Verify that on entering empty data in fields valid errot messages are being shown")
	public void EmptyFieldValidation()
	{
		logger.info("Load the Login page");
		loginpage.verifyIfLoginPageIsLoaded();
		logger.info("Click on create account link"); 
		loginpage.CreateAccountLink();
		logger.info("Enter Empty details in signup form");
		signupevents.Signup("", "", "", "", "", "");
		String pagesource = driver.getPageSource();
		signupevents.VerifyErrorMessagesOnEmptyFormSubmission(pagesource);
		
	}
	
	@Test(description = "Verify that on Signing up when password and confirm password doesn't match error essage should be shown")
	public void PasswordMismatch()
	{
		logger.info("Load the Login page");
		loginpage.verifyIfLoginPageIsLoaded();
		logger.info("Click on create account link"); 
		loginpage.CreateAccountLink();
		logger.info("Fill the sign up form");
		signupevents.Signup("Password", "techassoc", "myemail@gmail.com", "343434343", constants.password,"test123");
		String pagesource = driver.getPageSource();
		logger.info("Verify Full name empty field error message");
		signupevents.VerifyifPageLoaded(pagesource, "Passwords must match");
		
	}
	@Test(description = "Verify error messages on invalid data submission of signup form")

	public void InvalidSignupDatasubmission()
	{
		logger.info("Load the Login page");
		loginpage.verifyIfLoginPageIsLoaded();
		logger.info("Click on create account link"); 
		loginpage.CreateAccountLink();
		logger.info("Fill the sign up form with invalid data");
		signupevents.Signup("   ", "   ", "myemail", "1", "123","123");
		String pagesource = driver.getPageSource();
		logger.info("Verify Full name empty field error message");
		signupevents.VerifyErrorMessagesOnInvalidData(pagesource);
		
	}
	@Test(description = "Verify that merchant is unable to signup with deleted merchant email")

	public void SignupWithDeletedMerchantEmail()
	{
		logger.info("Load the Login page");
		loginpage.verifyIfLoginPageIsLoaded();
		logger.info("Click on create account link"); 
		loginpage.CreateAccountLink();
		logger.info("Fill the sign up form with duplicate data");
		signupevents.Signup("Tech vista", "Hellen", constants.deletedmerchant, constants.phone,constants.password,constants.Confirmpassword);
		String pagesource = driver.getPageSource();
		logger.info("Verify error message");
		signupevents.VerifyifPageLoaded(pagesource, "Email address already registered");
		
	}

}
