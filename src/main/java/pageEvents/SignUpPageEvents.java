package pageEvents;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import pageObjects.LoginPageElements;
import pageObjects.SignupPageElements;
import pageObjects.YopMailElements;
import utils.ElementFetch;
import utils.constants;

import java.time.Duration;
import java.util.ArrayList;

import static base.baseTest.*;

public class SignUpPageEvents {
	
	ElementFetch ele = new ElementFetch();
	
	
	public void VerifyifPageLoaded(String pageSource, String searchText) 
	{
		Assert.assertTrue(pageSource.contains(searchText), "Unable to signup.");
	}
	public void Signup(String name, String companyname, String email, String phone, String password, String confirmpassword)
	{
		
		ele.getWebElement("XPATH", SignupPageElements.fullname).sendKeys(name);
		ele.getWebElement("XPATH", SignupPageElements.companyname).sendKeys(companyname);
		ele.getWebElement("XPATH", SignupPageElements.EmailAddress).sendKeys(email);
		ele.getWebElement("XPATH", SignupPageElements.phonenumber).sendKeys(phone);
		ele.getWebElement("XPATH", SignupPageElements.password).sendKeys(password);
		ele.getWebElement("XPATH", SignupPageElements.confirmpassword).sendKeys(confirmpassword);
		ele.getWebElement("XPATH", SignupPageElements.SignupButton).click();
		
	}
	
	public void VerifyErrorMessagesOnEmptyFormSubmission(String pagesource)
	{
		logger.info("Verify Full name empty field error message");
		VerifyifPageLoaded(pagesource, "Full Name is required");
		logger.info("Verify Company name empty field error message");
		VerifyifPageLoaded(pagesource, "Company name is required");
		logger.info("Verify Email empty field error message");
		VerifyifPageLoaded(pagesource, "Email is required");
		logger.info("Verify Phone number empty field error message");
		VerifyifPageLoaded(pagesource, "Phone Number is required");
		logger.info("Verify Password empty field error message");
		VerifyifPageLoaded(pagesource, "Password is required");
		logger.info("Verify Confirm Password empty field error message");
		VerifyifPageLoaded(pagesource, "Password is required");
	}
	
	public void VerifyErrorMessagesOnInvalidData(String pagesource)
	{
		logger.info("Verify Full name field gives error on entering only spaces");
		VerifyifPageLoaded(pagesource, "Full Name is required");
		logger.info("Verify company name field gives error on entering only spaces");
		VerifyifPageLoaded(pagesource, "Company name is required");
		logger.info("Verify on entering invalid email valid error message should be shown");
		VerifyifPageLoaded(pagesource, "Invalid email");
		logger.info("Verify on entering invalid  Phone number error message should be shown");
		VerifyifPageLoaded(pagesource, "Phone number must be at least 3 characters");
		logger.info("Verify on entering invalid password valid error message should be shown");
		VerifyifPageLoaded(pagesource, "Password must be at least 8 characters");
		logger.info("Verify on entering invalid confirm password valid error message should be shown");
		VerifyifPageLoaded(pagesource, "Confirm Password must be at least 8 characters");
		
	}
	
	public void SignupEmail(String pagesource, String email, String companyname, String number,String fullname)
	{
        // Open Yopmail in a new tab
        // Execute JavaScript to open a new tab
        ((JavascriptExecutor) driver).executeScript("window.open();");

        // Switch to the new tab
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));


        // Navigate to Yopmail
        logger.info("Switch yopmail in newtab");
        driver.get(constants.yopmailUrl);

        // Enter Yopmail email address
        logger.info("Navigate to dandytest@yopmail.com inbox" );
        WebElement yopmailField = ele.getWebElement("XPATH", YopMailElements.email);
        yopmailField.sendKeys(constants.yopmailEmail);

        // Click on "Check Inbox" button
        ele.getWebElement("XPATH", YopMailElements.checkInbox).click();


        driver.switchTo().frame("ifmail");

        // Wait for email to load
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement signupSubject = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(YopMailElements.signupSubject)));

        // Assert if email subject is present
        logger.info("Verify Subject of new signup");
        String actualSubject = signupSubject.getText();
        System.out.println("Subject of signup email " + actualSubject);
        Assert.assertTrue(actualSubject.contains("New Merchant Sign Up"),
                "Email subject does not contain the expected text");


        logger.info("click on Show pictures button in email");
        WebElement showPictureButton =ele.getWebElement("XPATH", YopMailElements.showPictureButton);
        showPictureButton.click();

        logger.info("Verify getdandy logo is present in signup Email Footer and header");
        Assert.assertTrue(ele.getWebElement("XPATH", YopMailElements.dandyLogoHeader).isDisplayed(), "dandy logo is not displayed in Header");
        logger.info("Verify the content of email, phone number, company name, full name");
        pagesource=driver.getPageSource();
        VerifyifPageLoaded(pagesource,email);
        VerifyifPageLoaded(pagesource,number);
        VerifyifPageLoaded(pagesource,fullname);
        VerifyifPageLoaded(pagesource,companyname);

    }



}

