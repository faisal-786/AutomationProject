package qa.tests;

import base.baseTest;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;
import pageEvents.ForgotPasswordEvents;
import utils.ElementFetch;

public class ForgotPassword extends baseTest {
    ElementFetch ele = new ElementFetch();
    ForgotPasswordEvents forgotPasswordPage = new ForgotPasswordEvents();

    @Test(description = "Verify Password Reset page")
    public void PuppyTestCasesForgotPassword() {
        forgotPasswordPage.navigateToForgotPasswordPage();
        forgotPasswordPage.verifyUIElements();

        logger.info("Verify all the links in Password Reset page is active");
        forgotPasswordPage.verifyAllLinks();

        forgotPasswordPage.backtoLoginScreen();
    }
    @Test(description = "Verify Password Reset functionality for invalid email")
    public void InvalidEmail(){
        forgotPasswordPage.navigateToForgotPasswordPage();
        forgotPasswordPage.emailValidationError();
    }

    @Test(description = "Verify success page shown after sending Password Reset email")
    public void ValidEmail(){
        forgotPasswordPage.navigateToForgotPasswordPage();
        forgotPasswordPage.submitValidEmail();
        forgotPasswordPage.BacktoLoginFromResetEmailPage();
    }

    @Test(description = "Verify Password Reset email is successfully sent along with its content")
    public void VerifyResetPasswordEmail(){
        forgotPasswordPage.navigateToForgotPasswordPage();
        forgotPasswordPage.submitValidEmail();
        forgotPasswordPage.passwordResetEmail();

        logger.info("Verify all the links present in Password Reset Email is active");
        forgotPasswordPage.verifyAllLinks();
    }

}

