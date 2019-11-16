package com.aera.hotwire.testInitializer;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class BasePage {
    private static final int TIMEOUT = 15;
    private static final int POLLING = 100;
    protected WebDriver driver;
    private WebDriverWait wait;

    public BasePage(WebDriver driver) throws Exception {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, TIMEOUT, POLLING);
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, TIMEOUT), this);
    }

    protected void implicitilyWait(int num){
        driver.manage().timeouts().implicitlyWait(num, TimeUnit.SECONDS);
    }

    protected void waitForElementToAppear(WebElement element) {
        this.wait.until(ExpectedConditions.visibilityOf(element));
        System.out.println(element + " element is visible now.");
    }

    protected void waitForElementToDisappear(WebElement element) {
        this.wait.until(ExpectedConditions.invisibilityOf(element));
        System.out.println(element + " element is disappeared now.");
    }

    protected void waitForElementToEnable(WebElement element) {
        this.wait.until(ExpectedConditions.elementToBeClickable(element));
        System.out.println(element + " element is clickable now.");
    }

    protected void waitForTextToDisappear(WebElement element, String text) {
        this.wait.until(ExpectedConditions.not(ExpectedConditions.textToBePresentInElement(element, text)));
        System.out.println(element + " text is disappeared now.");
    }

    protected void waitForTexttoAppear(WebElement element, String text) {
        this.wait.until(ExpectedConditions.textToBePresentInElement(element, text));
        System.out.println(element + " text is visible now.");
        System.out.println("The available text on the form is: " + element.getText());
    }

    protected void waitForMessagetoAppear(WebElement element, String text) {
        this.wait.until(ExpectedConditions.textToBePresentInElement(element, text));
        System.out.println(element + " text is visible now.");
        System.out.println("'" + element.getText() + "' message displayed.");
    }
}