package pageObjects;

public interface ForgotPasswordElements {

    String forgotPasswordLink = "//a[normalize-space()='Forgot password?']";
    String emailField = "//input[@id='email']";
    String anywhereOnForgotPasswordPage = "//section[@class='sc-dIsUp sc-bvRlFw kKLVyn iTGZHL admin-bro_Box']";
    String sendLinkButton = "//button[normalize-space()='Send Link']";
    String backToLoginButton = "//a[normalize-space()='Go back to login screen']";
    String PasswordResetText = "//h6[@class='sc-dIvrsQ dIUgUM sc-bCwfaz sc-dYlIJu hzzSzX MQIBD admin-bro_Header admin-bro_H6']";
    String PasswordResetInstruction = "//div[@class='sc-jrsJWt sc-jFtgtd ezHxnG qNQfs admin-bro_Text']";
    String emailValidationError = "//span[@class='sc-gVFcvn kxpwkt']";
    String backToLoginScreenButton = "//button[normalize-space()='Go back to login screen']";
    String successMessage ="//div[contains(text(),'An email has been sent.')]";


}

