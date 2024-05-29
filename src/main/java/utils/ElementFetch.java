package utils;

import base.baseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class ElementFetch {
    public WebElement getWebElement(String identifierType, String identifierValue){
        switch (identifierType){
            case "XPATH":
                return baseTest.driver.findElement(By.xpath(identifierValue));

            case "CSS":
                return baseTest.driver.findElement(By.cssSelector(identifierValue));

            case "ID":
                return baseTest.driver.findElement(By.id(identifierValue));

            case "NAME":
                return baseTest.driver.findElement(By.name(identifierValue));

            case "TAGNAME":
                return baseTest.driver.findElement(By.tagName(identifierValue));

            default:
                return null;

        }
    }

    public List<WebElement> getWebElements(String identifierType, String identifierValue){
        switch (identifierType){
            case "XPATH":
                return baseTest.driver.findElements(By.xpath(identifierValue));

            case "CSS":
                return baseTest.driver.findElements(By.cssSelector(identifierValue));

            case "ID":
                return baseTest.driver.findElements(By.id(identifierValue));

            case "NAME":
                return baseTest.driver.findElements(By.name(identifierValue));

            case "TAGNAME":
                return baseTest.driver.findElements(By.tagName(identifierValue));

            default:
                return null;

        }
    }

}
