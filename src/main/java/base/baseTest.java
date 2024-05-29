package base;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.time.Duration;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.ITestResult;
import org.testng.annotations.*;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import io.github.bonigarcia.wdm.WebDriverManager;
import utils.constants;

public class baseTest {
    public static WebDriver driver;
    public static ExtentReports extent;
    public static ExtentSparkReporter sparkReporter;
    public static ExtentTest logger;


    @BeforeSuite
    public void beforeSuite() {
        sparkReporter = new ExtentSparkReporter(System.getProperty("user.dir") + File.separator + "reports" + File.separator + "DandyExtentReport.html");
        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);
        sparkReporter.config().setTheme(Theme.DARK);
        extent.setSystemInfo("HostName", "abhishek");
        extent.setSystemInfo("UserName", "rawat");
        sparkReporter.config().setDocumentTitle("Dandy Automation Report");
        sparkReporter.config().setReportName("Dandy Automation Test Results by QA Team");

        // Inject custom JavaScript to change the logo
        String customJS = "document.addEventListener('DOMContentLoaded', function() {" +
                "   console.log('DOM fully loaded and parsed');" +
                "   var logo = document.querySelector('.logo');" +
                "   if (logo) {" +
                "       console.log('Logo element found');" +
                "       logo.style.backgroundImage = 'url(\\\"../logos/logo4.png\\\")';" +
                "       logo.style.backgroundSize = 'contain';" +
                "       logo.style.backgroundRepeat = 'no-repeat';" +
                "       logo.style.width = '30px';" +  // Set the desired width
                "       logo.style.height = '30px';" + // Set the desired height
                "       logo.style.marginRight = '15px';" +
                "   } else {" +
                "       console.log('Logo element not found');" +
                "   }" +
                "   var link = document.querySelector('link[rel*=\"icon\"]') || document.createElement('link');" +
                "   link.type = 'image/png';" +
                "   link.rel = 'shortcut icon';" +
                "   link.href = '../logos/logo4.png';" +
                "   document.getElementsByTagName('head')[0].appendChild(link);" +
                "});";
        sparkReporter.config().setJs(customJS);
    }

    // Helper method to get the path of the logo
//    private String getLogoPath() {
//        return  System.getProperty("user.dir").replace("\\", "/") + "/logos/JPG-05.jpg";
//    }

    @BeforeMethod
    @Parameters({"browser", "headless"})
    public void beforeTestMethod(String browser, @Optional("false") String headless, Method testMethod) {
        // Retrieve the description from the @Test annotation
        Test testAnnotation = testMethod.getAnnotation(Test.class);
        String description = testAnnotation.description();

        // Create a test instance with description
        logger = extent.createTest(description);

        // Convert headless string parameter to boolean
        boolean isHeadless = Boolean.parseBoolean(headless);

        setupDriver(browser, isHeadless);
        driver.manage().window().maximize();
        driver.get(constants.url);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));

//         Set logger instance for the test method
//        try {
//            ((baseTest) testMethod.getDeclaringClass().newInstance()).setLogger(logger);
//        } catch (InstantiationException | IllegalAccessException e) {
//            e.printStackTrace();
//        }
    }


//     Setter method for logger
//    public void setLogger(ExtentTest logger) {
//        this.logger = logger;
//    }

    @AfterMethod
    public void afterMethod(ITestResult result) throws IOException {
        if (result.getStatus() == ITestResult.FAILURE) {
            String screenshotPath = captureScreenshot(result.getMethod().getMethodName());
            logger.log(Status.FAIL, MarkupHelper.createLabel(result.getName() + " - Test Case Failed", ExtentColor.RED));

            String failureDetails = result.getThrowable().getMessage();
            logger.fail("Failure Attachment " + failureDetails,
                    MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());

        } else if (result.getStatus() == ITestResult.SKIP) {
            logger.log(Status.SKIP, MarkupHelper.createLabel(result.getName() + " - Test Case Skipped", ExtentColor.ORANGE));
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            logger.log(Status.PASS, MarkupHelper.createLabel(result.getName() + " - Test Case PASS", ExtentColor.GREEN));
        }
        driver.quit();
    }

    @AfterSuite
    public void afterSuite() 
    {
        extent.flush();
    }

    public void setupDriver(String browser, boolean headless) {
        if (browser.equalsIgnoreCase("chrome")) {
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--disable-extensions");
            options.addArguments("--disable-infobars");
            options.addArguments("--disable-notifications");
            if (headless) {
                options.addArguments("--headless=new");
                options.addArguments("--disable-gpu"); // for Windows OS
                options.addArguments("--no-sandbox"); // Bypass OS security model
                options.addArguments("--disable-dev-shm-usage"); // Overcome limited resource problems
            }
            driver = new ChromeDriver(options);
        } else if (browser.equalsIgnoreCase("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            FirefoxOptions options = new FirefoxOptions();
            options.addArguments("--disable-notifications");
            if (headless) {
                options.addArguments("--headless");
            }
            driver = new FirefoxDriver(options);
        } else if (browser.equalsIgnoreCase("edge")) {
            WebDriverManager.edgedriver().setup();
            EdgeOptions options = new EdgeOptions();
            options.addArguments("--disable-notifications");
            if (headless) {
                options.addArguments("headless");
            }
            driver = new EdgeDriver(options);
        }
        driver.manage().window().maximize();
        driver.get(constants.url);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
    }
    public String captureScreenshot(String methodName) {
        String screenshotPath = System.getProperty("user.dir") + File.separator + "reports" + File.separator + "screenshots" +methodName + ".png";
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(screenshot, new File(screenshotPath));
            System.out.println("Screenshot saved at: " + screenshotPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "screenshots/" + methodName + ".png";
    }
}

