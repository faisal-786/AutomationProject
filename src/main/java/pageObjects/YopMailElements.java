package pageObjects;

public interface YopMailElements {
    String email= "//input[@placeholder='Enter your inbox here']";
    String checkInbox = "//i[contains(text(),'\uE5C8')]";
    String forgotPasswordSubject = "//body/header/div[3]/div[1]";
    String emailContent = "//main[@class='yscrollbar']";
    String ResetPasswordbutton = "//button[normalize-space()='Reset Your Password']";
    String showPictureButton = "//div[@class='noprint']//span[1]";
    String dandyLogoHeader = "//img[@src='https://staging.getdandy.com/assets/images/mail_header.png']";
    String dandyLogoFooter = "//img[@src='https://staging.getdandy.com/assets/images/mail_footer.png']";
    String signupSubject="//div[@class='ellipsis nw b f18']";
}
