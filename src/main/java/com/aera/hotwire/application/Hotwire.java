package com.aera.hotwire.application;

import com.aera.hotwire.testInitializer.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Hotwire extends BasePage {

    public Hotwire(WebDriver driver) throws Exception {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    /**
     * Page Factory method for getting web-elements
     */
    @FindBy(how = How.LINK_TEXT, using = "Hotwire™")
    static WebElement homePage;

    @FindBy(how = How.XPATH, using = "//li[@class='hidden-xs']//a[contains(text(),'Vacations')]")
    static WebElement vacationsTab;

    @FindBy(how = How.XPATH, using = "//button[@id='farefinder-package-search-button']")
    static WebElement findADealBtn;

    @FindBy(how = How.XPATH, using = "//button[contains(text(),'Flight')]")
    static WebElement flightOption;

    @FindBy(how = How.XPATH, using = "//button[contains(text(),'Hotel')]")
    static WebElement hotelOption;

    @FindBy(how = How.XPATH, using = "//button[contains(text(),'Car')]")
    static WebElement carOption;

    @FindBy(how = How.XPATH, using = "//input[@id='farefinder-package-origin-location-input']")
    static WebElement flyFromInput;

    @FindBy(how = How.XPATH, using = "//input[@id='farefinder-package-destination-location-input']")
    static WebElement flyToInput;

    @FindBy(how = How.ID, using = "farefinder-package-startdate-input")
    static WebElement departureDateTxtBox;

    @FindBy(how = How.XPATH, using = "//div[@class='date-picker-wrapper']//div[@class='month-wrapper']")
    static WebElement calender;

    @FindBy(how = How.XPATH, using = "//select[@id='farefinder-package-pickuptime-input']")
    static WebElement departureTimingSelector;

    @FindBy(how = How.ID, using = "farefinder-package-enddate-input")
    static WebElement returningDateTxtBox;

    @FindBy(how = How.XPATH, using = "//select[@id='farefinder-package-dropofftime-input']")
    static WebElement returningTimingSelector;

    //New Page opens up with search criteria results
    @FindBy(how = How.XPATH, using = "//div[@id='interstitial-message']")
    static WebElement waitMessage;

    @FindBy(how = How.ID, using = "hotelResultTitle")
    static WebElement pageTitle;

    @FindBy(how = How.XPATH, using = "/html[1]/body[1]/div[4]/form[1]/div[12]/div[2]/div[15]/section[1]/div[1]/div[21]/section[1]/article")
    static WebElement searchedResult;

    public void searchVacationBundle() throws Exception {
        waitForElementToAppear(homePage);

        //Navigating to the Vacations tab available in Menu Bar
        vacationsTab.click();

        //Waiting for the page reload, till the "Find a deal" button appears
        waitForElementToAppear(findADealBtn);

        //Verifying with the page title if it navigated to the Vacations Tab
        String pageTitleName = driver.getCurrentUrl();
        Assert.assertTrue(pageTitleName.contains("packages"));

        /*  Select all the available options under the Vacations that are not already selected and before this
            Verifying if all the available options are selected (Flight/Hotel/Car)
        */

        //Checking if Flight option is selected, and if not selected then selecting it
        String flight = flightOption.getAttribute("class");
        if(flight.equalsIgnoreCase("hw-btn hw-btn-check hw-btn-check-active")){
            System.out.println("Flight Option is already selected");
        }
        else
        {
            System.out.println("Flight Option is not selected, selecting it");
            flightOption.click();
        }

        //Checking if Hotel option is selected, and if not selected then selecting it
        String hotel = hotelOption.getAttribute("class");
        if(hotel.equalsIgnoreCase("hw-btn hw-btn-check hw-btn-check-active")){
            System.out.println("Hotel Option is already selected");
        }
        else
        {
            System.out.println("Hotel Option is not selected, selecting it");
            hotelOption.click();
        }

        //Checking if Car option is selected, and if not selected then selecting it
        String car = carOption.getAttribute("class");
        if(car.equalsIgnoreCase("hw-btn hw-btn-check hw-btn-check-active")){
            System.out.println("Car Option is already selected");
        }
        else {
            System.out.println("Car Option is not selected, selecting it");
            carOption.click();
        }

        //Selecting to Origin Location
        flyFromInput.click();
        flyFromInput.clear();
        String Origin = "SFO";
        String Destination = "LAX";
        flyFromInput.sendKeys(Origin);
        implicitilyWait(10);

        //Created a list of elements available in the drop-down and selecting the option if it contains the value.
        List<WebElement> OriginLocation = driver.findElements(By.xpath("/html[1]/body[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[2]/farefinder-package[1]/div[1]/form[1]/div[1]/div[1]/div[1]/ul[1]/li"));
        System.out.println(OriginLocation.size());
        for (int j = 0; j <= OriginLocation.size(); j++) {
            String temp = OriginLocation.get(j).getText();
            if (temp.contains(Origin)) {
                OriginLocation.get(j).click();
                break;
            }
        }

        //Selecting to Destination Location
        flyToInput.click();
        flyToInput.clear();
        flyToInput.sendKeys(Destination);
        implicitilyWait(10);

        //Created a list of elements available in the drop-down and selecting the option if it contains the value.
        List<WebElement> DestinationLocation = driver.findElements(By.xpath("/html[1]/body[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[2]/farefinder-package[1]/div[1]/form[1]/div[1]/div[2]/div[1]/ul[1]/li"));
        System.out.println(DestinationLocation.size());
        for (int k = 0; k <= DestinationLocation.size(); k++) {
            String temp = DestinationLocation.get(k).getText();
            if (temp.contains(Destination)) {
                DestinationLocation.get(k).click();
                break;
            }
        }

        /*
            Getting dates as today, tomorrow and 3rd or can say any week
            As we require date in the below format, that's why converted the get date into this format and
            then utilized it.

            I used Calender abstract class for getting next day's date and for date after some week

            As it is a text box, as well date selection from calender pop-up, I used text option to enter the date
        */
        String pattern = "MM/dd/yyyy";
        DateFormat dateFormat = new SimpleDateFormat(pattern);

        Calendar calendar = Calendar.getInstance();
        Date today = calendar.getTime();
        System.out.println(dateFormat.format(today));
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date tomorrow = calendar.getTime();
        System.out.println(dateFormat.format(tomorrow));
        String DepartureDate = dateFormat.format(tomorrow);
        calendar.add(Calendar.DAY_OF_YEAR,21);
        Date threeWeekLaterDate = calendar.getTime();
        System.out.println(dateFormat.format(threeWeekLaterDate));
        String ReturnDate = dateFormat.format(threeWeekLaterDate);


        //Entering departure date
        departureDateTxtBox.click();
        departureDateTxtBox.clear();
        departureDateTxtBox.sendKeys(DepartureDate);
        departureDateTxtBox.sendKeys(Keys.TAB);
        System.out.println("Entered the departure date");
        Thread.sleep(1000);
        //Selecting departure time
        try {
            Select depTime = new Select(departureTimingSelector);
            String depTimeValue = "Evening";
            depTime.selectByVisibleText(depTimeValue);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Entering returning date
        returningDateTxtBox.click();
        returningDateTxtBox.clear();
        returningDateTxtBox.sendKeys(ReturnDate);
        returningDateTxtBox.sendKeys(Keys.TAB);
        System.out.println("Entered the return date");
        Thread.sleep(2000);

        //Selecting returning time
        try {
            Select retTime = new Select(returningTimingSelector);
            String retTimeValue = "Morning";
            retTime.selectByVisibleText(retTimeValue);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Tapping/Clicking Find a Deal Button.
        findADealBtn.click();

        //New Page opens up with search criteria results
        waitForElementToAppear(pageTitle);

        //Collecting all the results found in a list
        List<WebElement> result = driver.findElements(By.xpath("/html[1]/body[1]/div[4]/form[1]/div[12]/div[2]/div[15]/section[1]/div[1]/div[21]/section[1]/article"));
        System.out.println(result.size());

        //Assert to verify by checking if the list it empty
        Assert.assertTrue(!result.isEmpty());
    }
}