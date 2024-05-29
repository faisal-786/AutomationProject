package qa.tests;

import base.baseTest;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;
import pageEvents.LoginPageEvents;
import utils.ElementFetch;

public class LoginPage extends baseTest {
    ElementFetch ele = new ElementFetch();
    LoginPageEvents loginpage = new LoginPageEvents();

    @Test(description = "Verify Dandy Login page is loaded completely")
    public void VerifyloginPageLoad() {
        loginpage.verifyIfLoginPageIsLoaded();

        logger.info("Verify that Dandy Logo is displayed in Login Page");
        loginpage.verifyIfGetDandyLogoIsVisible();

        logger.info("Verify that Dandy Title is displayed in Login Page");
        loginpage.verifyIfGetDandyTitleIsPresent();

        logger.info("Verify that no link on the login page is broken");
        loginpage.verifyAllLinks();
    }
    @Test(description = "Masking and unmasking of password")
    public void maskingUnmasking() {
        loginpage.verifyPasswordMaskingAndUnMasking();
    }

    @Test(description = "Verify toaster is shown when logging in with incorrect credentials")
    public void VerifyIncorrectLogin() {
        loginpage.loginWithIncorrectCredentials();
    }

    @Test(description = "Verify Remember Me functionality")
    public void verifyRememberMeFunctionality() {
        loginpage.verifyRememberMeFunctionality();
    }


    @Test(description ="User successfully logged in")
    public void VerifyCorrectLogin() {
        loginpage.login();

    }
}


