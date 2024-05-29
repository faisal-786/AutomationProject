package utils;

import base.baseTest;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.IAnnotationTransformer;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.ITestAnnotation;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class SuitListener implements ITestListener, IAnnotationTransformer {

    @Override
    public void onTestFailure(ITestResult result) {
        String methodName = result.getMethod().getMethodName();
        String screenshotPath = System.getProperty("user.dir") + File.separator + "reports"+File.separator+"screenshots" + File.separator + methodName + ".png";

        // Capture the screenshot
        File screenshot = ((TakesScreenshot) baseTest.driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(screenshot, new File(screenshotPath));
            System.out.println("Screenshot saved at: " + screenshotPath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

//    @Override
//    public void transform(
//            ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {
//        annotation.setRetryAnalyzer(RetryAnalyzer.class);
//    }

    @Override
    public void onTestStart(ITestResult result) {
        // Implementation not needed for screenshot capture
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        // Implementation not needed for screenshot capture
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        // Implementation not needed for screenshot capture
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        // Implementation not needed for screenshot capture
    }

    @Override
    public void onStart(ITestContext context) {
        // Implementation not needed for screenshot capture
    }

    @Override
    public void onFinish(ITestContext context) {
        // Implementation not needed for screenshot capture
    }
}
