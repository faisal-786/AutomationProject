package utils;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {
    int count = 0;
    int retrycount = 1;
    @Override
    public boolean retry(ITestResult iTestResult) {
        while (count < retrycount) {
            count++;
            return true;
        }
        return false;
    }
}
