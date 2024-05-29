package pageObjects;

public interface LoginPageElements {

    String emailAddress= "//input[@id='email']";
    String passwordField= "//input[@id='password']";
    String loginButton= "//button[@class='sc-gtsrHT sc-bQCEYZ hRtsoF jeVsCo undefined variant-primary admin-bro_Button undefined variant-primary']";
    String anyWhereOnLoginPage = "//section[@class='sc-dIsUp ikYpBW admin-bro_Box']";
    String getDandyLogo ="//img[@alt='GetDandy']";
    String passwordUnMasking = "//span[@class='sc-bsatvv iKbWtE sc-htmcrh blPcbQ icon-Visibility-on']";
    String createAccount="//a[normalize-space()='Create an Account']";
    String logoutButton="//span[@class='sc-bsatvv crSbPp sc-larqGv kFOLPn icon-Sign-out']";
    String toasterMessage = "//div[@class='sc-ihRHuF jLTxGf']";
    String rememberMeCheckbox ="//a[@class='sc-gKAaRy ckRrlV']";
}
