package pageEvents;

import base.baseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import pageObjects.LoginPageElements;
import utils.ElementFetch;
import utils.constants;

import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;
import java.util.List;
import java.io.IOException;

import static base.baseTest.driver;
import static base.baseTest.logger;

public class LoginPageEvents {
    ElementFetch ele = new ElementFetch();

    public void verifyIfLoginPageIsLoaded() {

        logger.info("verify if all the elements are loaded");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(LoginPageElements.loginButton)));
        Assert.assertTrue(ele.getWebElements("XPATH", LoginPageElements.loginButton).size()>0, "Element not found");
    }


    public void verifyIfGetDandyTitleIsPresent(){
        String expectedTitle = "GetDandy | Login";
        String actualTitle = driver.getTitle();

        logger.info("check for 'GetDandy | Login' title in Login page");
        Assert.assertEquals(expectedTitle, actualTitle, "Page title is not GetDandy | Login");
        System.out.println("Page Title: " + actualTitle);
    }
 //Opening the signupPage
    public void CreateAccountLink()
    {
    	ele.getWebElement("XPATH", LoginPageElements.createAccount).click();
    }
    public void verifyIfGetDandyLogoIsVisible() {
        Assert.assertTrue(ele.getWebElement("XPATH", LoginPageElements.getDandyLogo).isDisplayed(), "Logo not found");
    }

    public void verifyPasswordMaskingAndUnMasking() {
        // Locate the password input field
        WebElement passwordField = ele.getWebElement("XPATH", LoginPageElements.passwordField);

        logger.info("Enter password in password field");
        String samplePassword = "samplePassword";
        passwordField.sendKeys(samplePassword);

         // Retrieve the value of the 'type' attribute of the password field
        String fieldType = passwordField.getAttribute("type");

        logger.info("check if the password field is masked");
        Assert.assertEquals("password", fieldType.toLowerCase(), "Password is not masked");

        logger.info("unmask the password");
        WebElement unmaskButton = ele.getWebElement("XPATH", LoginPageElements.passwordUnMasking);
        unmaskButton.click();

        logger.info("Verify if the password is unmasked");
        String unmaskedPassword = passwordField.getAttribute("value");
        Assert.assertEquals(samplePassword, unmaskedPassword, "Password is not unmasked");

        passwordField.clear();

    }

    public void login(){

        logger.info("Enter correct login credentials in email and password field");
        WebElement emailField = ele.getWebElement("XPATH", LoginPageElements.emailAddress);
        WebElement passwordField= ele.getWebElement("XPATH", LoginPageElements.passwordField);

        passwordField.clear();
        passwordField.click();
        passwordField.sendKeys(constants.password);
        emailField.sendKeys(constants.email);


        ele.getWebElement("XPATH", LoginPageElements.anyWhereOnLoginPage).click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(LoginPageElements.loginButton)));


        logger.info("Click on login button");
        loginButton.click();


        logger.info("check if logout button is present after getting logged in");
        WebElement logoutButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(LoginPageElements.logoutButton)));
        Assert.assertTrue(logoutButton.isDisplayed(), "Login failed, logout button is not visible");

        logoutButton.click();
    }

    public void loginWithIncorrectCredentials() {
        WebElement emailField = ele.getWebElement("XPATH", LoginPageElements.emailAddress);
        WebElement passwordField = ele.getWebElement("XPATH", LoginPageElements.passwordField);


        logger.info("input incorrect credentials in email and password field");
        passwordField.clear();
        passwordField.click();
        passwordField.sendKeys("incorrectPassword");
        emailField.sendKeys("incorrectEmail@example.com");

        ele.getWebElement("XPATH", LoginPageElements.anyWhereOnLoginPage).click();


        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(LoginPageElements.loginButton)));

        logger.info("click on login button");
        loginButton.click();


        logger.info("check if the toaster message is displayed for incorrect credentials");
        WebElement toaster = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(LoginPageElements.toasterMessage)));
        Assert.assertTrue(toaster.isDisplayed(), "Toaster message not displayed for incorrect credentials");
        System.out.println("Toaster message: " + toaster.getText());
    }
    public void clickRememberMe() {
        WebElement rememberMeCheckbox = ele.getWebElement("XPATH", LoginPageElements.rememberMeCheckbox);
        if (!rememberMeCheckbox.isSelected()) {
            rememberMeCheckbox.click();
        }
    }

    public void verifyRememberMeFunctionality() {
        logger.info("login with remember me checkbox selected");
        loginWithRememberMe();

        logger.info("logout and to get back to login page");
        logout();

        logger.info("Verify if login details are prefilled and attempt to login");
        VerifyLoginwithPrefilledCredentials();
    }

    private void loginWithRememberMe() {
        WebElement emailField = ele.getWebElement("XPATH", LoginPageElements.emailAddress);
        WebElement passwordField = ele.getWebElement("XPATH", LoginPageElements.passwordField);

        emailField.clear();
        emailField.sendKeys(constants.email);

        passwordField.clear();
        passwordField.sendKeys(constants.password);

        clickRememberMe();

        WebElement loginButton = ele.getWebElement("XPATH", LoginPageElements.loginButton);
        loginButton.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement logoutButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(LoginPageElements.logoutButton)));
        Assert.assertTrue(logoutButton.isDisplayed(), "Login failed, logout button is not visible");
    }

    private void logout() {
        WebElement logoutButton = ele.getWebElement("XPATH", LoginPageElements.logoutButton);
        logoutButton.click();
    }

    private void VerifyLoginwithPrefilledCredentials() {

        // Verify that the email and password fields are prefilled
        WebElement emailField = ele.getWebElement("XPATH", LoginPageElements.emailAddress);
        WebElement passwordField = ele.getWebElement("XPATH", LoginPageElements.passwordField);
        Assert.assertEquals(emailField.getAttribute("value"), constants.email, "Email field is not prefilled");
        Assert.assertEquals(passwordField.getAttribute("value"), constants.password, "Password field is not prefilled");

        // Attempt to log in
        WebElement loginButton = ele.getWebElement("XPATH", LoginPageElements.loginButton);
        loginButton.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement logoutButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(LoginPageElements.logoutButton)));

        Assert.assertTrue(logoutButton.isDisplayed(), "User is not logged in, remember me functionality failed");
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

}
