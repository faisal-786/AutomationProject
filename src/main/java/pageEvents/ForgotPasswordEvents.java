package pageEvents;

import base.baseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import pageObjects.ForgotPasswordElements;
import pageObjects.LoginPageElements;
import pageObjects.YopMailElements;
import utils.ElementFetch;
import utils.constants;

import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;

import static base.baseTest.driver;
import static base.baseTest.logger;


public class ForgotPasswordEvents {
    ElementFetch ele = new ElementFetch();
    private String enteredEmail;

    public void navigateToForgotPasswordPage(){
        logger.info("Click on Forgot password? link and navigate to forgot password page");
        WebElement ForgotPasswordLink = ele.getWebElement("XPATH", ForgotPasswordElements.forgotPasswordLink);
        ForgotPasswordLink.click();
    }

    public void verifyUIElements() {
        logger.info("Verify if elements in Password Reset page is loaded successfully");
        Assert.assertTrue(ele.getWebElement("XPATH", ForgotPasswordElements.emailField).isDisplayed(), "Email input field is not displayed");
        Assert.assertTrue(ele.getWebElement("XPATH", ForgotPasswordElements.sendLinkButton).isDisplayed(), "Send Link button is not displayed");
        Assert.assertTrue(ele.getWebElement("XPATH", ForgotPasswordElements.PasswordResetText).isDisplayed(), "Send Link button is not displayed");
        Assert.assertTrue(ele.getWebElement("XPATH", ForgotPasswordElements.PasswordResetInstruction).isDisplayed(), "Send Link button is not displayed");
    }

    public void backtoLoginScreen(){
        logger.info("Verify Go back to login screen button is navigating to Login page");
        WebElement backToLoginButton = ele.getWebElement("XPATH", ForgotPasswordElements.backToLoginButton);
        backToLoginButton.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement LoginButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(LoginPageElements.loginButton)));
        Assert.assertTrue(LoginButton.isDisplayed(), "Go back to login screen button is not navigating back to login page");
    }

    public void verifyAllLinks() {
        List<WebElement> links = ele.getWebElements("TAGNAME", "a"); // Fetch all <a> tags
        for (WebElement link : links) {
            String url = link.getAttribute("href");
            if (url != null && !url.isEmpty()) {
                verifyLink(url);
            } else {
                System.out.println("URL is either not configured for anchor tag or it is empty");
            }
        }
    }

    private void verifyLink(String url) {
        try {
            URL link = new URL(url);
            HttpURLConnection httpConn = (HttpURLConnection) link.openConnection();
            httpConn.setConnectTimeout(3000);
            httpConn.connect();
            if (httpConn.getResponseCode() == 200) {
                System.out.println(url + " - " + httpConn.getResponseMessage());
            } else {
                System.out.println(url + " - " + httpConn.getResponseMessage() + " - " + httpConn.getResponseCode());
                Assert.fail(url + " is a broken link.");
            }
        } catch (IOException e) {
            e.printStackTrace();
            Assert.fail(url + " is a broken link.");
        }
    }
    public void emailValidationError() {
        logger.info("Input invalid email in email field");
        WebElement emailField= ele.getWebElement("XPATH", ForgotPasswordElements.emailField);
        emailField.sendKeys("invalidemail@yopmail.com");

        ele.getWebElement("XPATH", ForgotPasswordElements.anywhereOnForgotPasswordPage).click();

        logger.info("Click on Send Link button");
        WebElement sendLinkButton = ele.getWebElement("XPATH", ForgotPasswordElements.sendLinkButton);
        sendLinkButton.click();

        logger.info("Check for email validation error message");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement emailValidationError = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ForgotPasswordElements.emailValidationError)));
        Assert.assertTrue(emailValidationError.isDisplayed(), "Error message is not displayed for invalid email");
    }

    public void submitValidEmail() {
        logger.info("Input valid email in email field");
        WebElement emailField= ele.getWebElement("XPATH", ForgotPasswordElements.emailField);
        emailField.sendKeys("t345@yopmail.com");

        enteredEmail = emailField.getAttribute("value");
        System.out.println("Entered email: " + enteredEmail);


        ele.getWebElement("XPATH", ForgotPasswordElements.anywhereOnForgotPasswordPage).click();

        logger.info("Click on Send Link button");
        WebElement sendLinkButton = ele.getWebElement("XPATH", ForgotPasswordElements.sendLinkButton);
        sendLinkButton.click();

        logger.info("Check for password reset email sent message");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(ForgotPasswordElements.sendLinkButton)));

        WebElement successMessage = ele.getWebElement("XPATH", ForgotPasswordElements.successMessage);

        String messageText = successMessage.getText();
        System.out.println("Message Text: " + messageText); // Print out the text content

        Assert.assertTrue(messageText.contains("An email has been sent."), "Text 'An email has been sent.' is not present on the page");
        Assert.assertTrue(messageText.contains("Please click the link when you get it."), "Text 'Please click the link when you get it.' is not present on the page");
    }

    public void BacktoLoginFromResetEmailPage(){
        {
            logger.info("Click on Go back to login screen button ");
            WebElement backToLoginScreenButton = ele.getWebElement("XPATH", ForgotPasswordElements.backToLoginScreenButton);
            backToLoginScreenButton.click();

            logger.info("Verify Go back to login screen button is navigating to Login page");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement LoginButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(LoginPageElements.loginButton)));
            Assert.assertTrue(LoginButton.isDisplayed(), "Go back to login screen button is not navigating back to login page");
        }

    }

        public void passwordResetEmail(){
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
            WebElement emailSubject = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(YopMailElements.forgotPasswordSubject)));

            // Assert if email subject is present
            logger.info("Verify Subject of Password Reset Email");
            String actualSubject = emailSubject.getText();
            System.out.println("Subject of Reset Password Email" + actualSubject);
            Assert.assertTrue(actualSubject.contains("Forgot your password? Let's get you a new one"),
                    "Email subject does not contain the expected text");


            logger.info("click on Show pictures button in email");
            WebElement showPictureButton =ele.getWebElement("XPATH", YopMailElements.showPictureButton);
            showPictureButton.click();

            logger.info("Verify getdandy logo is present in Password Reset Email Footer and header");
            Assert.assertTrue(ele.getWebElement("XPATH", YopMailElements.dandyLogoHeader).isDisplayed(), "dandy logo is not displayed in Header");
            Assert.assertTrue(ele.getWebElement("XPATH", YopMailElements.dandyLogoFooter).isDisplayed(), "dandy logo is not displayed in Footer");


            logger.info("Verify the content of email and username to which email was sent");
            WebElement emailContent = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(YopMailElements.emailContent)));
            String actualContent = emailContent.getText();
            System.out.println("Subject of Reset Password Email" + actualContent);
            Assert.assertTrue(actualContent.contains("We got a request to change the password for the account with the username " + enteredEmail), "Email content does not contain expected text");

            logger.info("Verify Reset Your Password button is present");
            Assert.assertTrue(ele.getWebElement("XPATH", YopMailElements.ResetPasswordbutton).isDisplayed(), "Reset Your Password button is not displayed");


        }

}
