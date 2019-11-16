package com.aera.hotwire.testInitializer;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import com.aera.hotwire.reports.Reporter;
import org.testng.annotations.*;

import java.util.concurrent.TimeUnit;

public class BrowserConfiguration {
    public static WebDriver driver;
    private static void setChromeDriverProperty(){
        System.setProperty("webdriver.chrome.driver", "./chromedriver.exe");
    }
    private static void setFirefoxDriverProperty(){
        System.setProperty("webdriver.gecko.driver", "./geckodriver.exe");
    }

    @BeforeTest
    @Parameters({"browserName", "webURL", "headLess"})
    public void initializeTestBaseSetup(String browserType, String webURL, Boolean headLess) {
        try {
            System.out.println(browserType + ", " + webURL + ", " + headLess);
            setDriver(browserType, webURL, headLess);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            Reporter.setupReports(getClass().getName(),getClass().getSimpleName());
        }
    }

    private void setDriver(String browserName, String appURL, boolean headLess) {
        switch (browserName) {
            case "chrome":
                driver = initChromeDriver(appURL, headLess);
                break;
            case "firefox":
                driver = initFirefoxDriver(appURL, headLess);
                break;
            default:
                System.out.println("browser : " + browserName + " is invalid, Launching Firefox as browser of choice..");
                driver = initFirefoxDriver(appURL, headLess);
        }
    }

    private static WebDriver initChromeDriver(String webURL, boolean headLess) {
        System.out.println("Launching google chrome browser.......");
        setChromeDriverProperty();
        System.setProperty("headless", String.valueOf(headLess));
        System.out.println(headLess);
        if(headLess) {
            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.addArguments("--headless");
            driver = new ChromeDriver(chromeOptions);
        } else {
            driver = new ChromeDriver();
        }
        driver.manage().window().maximize();
        driver.navigate().to(webURL);
        return driver;
    }

    private static WebDriver initFirefoxDriver(String webURL, Boolean headLess) {
        System.out.println("Launching firefox browser.......");
        setFirefoxDriverProperty();
        System.setProperty("headless", String.valueOf(headLess));
        System.out.println(headLess);
        if(headLess) {
            FirefoxOptions firefoxOptions = new FirefoxOptions();
            firefoxOptions.addArguments("--headless");
            driver = new FirefoxDriver(firefoxOptions);
        } else {
            driver = new FirefoxDriver();
        }
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.navigate().to(webURL);
        return driver;
    }

    @BeforeClass
    public void beforeClass()
    {
        Reporter.setupReports(getClass().getName(), getClass().getSimpleName());
        System.out.println("Test is passing through BEFORE CLASS");
    }

    @BeforeMethod
    public void beforeMethod(){
        System.out.println("Test is passing through BEFORE METHOD");
    }

    @AfterMethod
    public void tearDown()
    {
        System.out.println("Test is passing through AFTER METHOD");
    }

    @AfterClass
    public void afterClass() {
        try {
            System.out.println("Test is passing through AFTER CLASS");
            Reporter.flushReports();
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        finally
        {
            if (driver != null) {
                driver.quit();
            }
        }
    }

    public WebDriver getDriver() {
        return driver;
    }
}