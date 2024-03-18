package Worldline_Level_3.Task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

public class IndigoFlightSearch {
    static WebDriver driver;
    
    @BeforeClass
    public static void initializeChromeDriver() {
        ChromeOptions options = new ChromeOptions();
        // ALLOW CHROME PERMISSIONS.
        options.addArguments("--use-fake-ui-for-media-stream",
                             "--use-fake-device-for-media-stream",
                             "--allow-file-access-from-files",
                             "--allow-file-access",
                             "--allow-read-access-from-files",
                             "--allow-http-screen-capture",
                             "--allow-insecure-localhost",
                             "--use-file-for-fake-audio-capture=C:\\fakeAudio.wav",
                             "--use-file-for-fake-video-capture=C:\\fakeVideo.y4m",
                             "--enable-usermedia-screen-capturing",
                             "--autoplay-policy=no-user-gesture-required",
                             "--ignore-certificate-errors",
                             "--disable-web-security",
                             "--disable-notifications",
                             "--disable-infobars",
                             "--disable-dev-shm-usage",
                             "--disable-extensions",
                             "--disable-gpu",
                             "--disable-popup-blocking",
                             "--no-sandbox",
                             "--start-maximized");

        System.setProperty("webdriver.chrome.driver", "C:\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe");
        driver = new ChromeDriver(options); 
        System.out.println("Chrome Window Opened.");
        driver.manage().window().maximize();
        System.out.println("Chrome Window Maximized for Convenient View.");
    }

    @Test
    public void openIndigoWebsite() {
        driver.get("https://www.goindigo.in/?linkNav=home_header");
        WebDriverWait wait = new WebDriverWait(driver, 10);
        System.out.println("Indigo URL Opened.");
        String actualUrl = driver.getCurrentUrl();
        String expectedUrl = "https://www.goindigo.in/?linkNav=home_header";
        Assert.assertEquals(actualUrl, expectedUrl, "Indigo website URL does not match.");
    }
    
    @Test(dependsOnMethods = {"openIndigoWebsite"})
    public void closeAdvertisement() {
        WebElement closeAdButton = driver.findElement(By.xpath("//span[@id='salePopupCloseBtn']"));
        closeAdButton.click();
        System.out.println("Advertisement is closed.");
        System.out.println("One-way is already selected.");
    }
    
    @Test(dependsOnMethods = {"closeAdvertisement"})
    public void searchFlight() throws InterruptedException {
        String origin = "Bengaluru(BLR)";
        String destination = "Lucknow(LKO)";
        WebElement originInput = driver.findElement(By.xpath("//input[@placeholder='From']"));
        originInput.click();
        originInput.clear();
        originInput.sendKeys(origin);
        Thread.sleep(1000);

        WebElement destinationInput = driver.findElement(By.xpath("//input[@placeholder='To']"));
        destinationInput.clear();
        destinationInput.sendKeys(destination);
        Thread.sleep(1000);

        WebElement dateField = driver.findElement(By.xpath("//input[@placeholder='Travel Dates']"));
        dateField.click();
        Thread.sleep(1000);
        dateField.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        Thread.sleep(1000);
        int currentDay = LocalDate.now().getDayOfMonth();
        dateField.sendKeys(Integer.toString(currentDay));
        Thread.sleep(1000);
        dateField.sendKeys(Keys.ENTER);
        Thread.sleep(1000);
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        String formattedDate = currentDate.format(formatter);
        System.out.println("Date Input is Entered as per Current date: " + formattedDate);

        WebElement searchButton = driver.findElement(By.xpath("//span[normalize-space()='Search Flight']"));
        searchButton.click();
        Thread.sleep(5000);
        System.out.println("Search Button is clicked.");
    }
    
    @Test(dependsOnMethods = {"searchFlight"})
    public void modifyPassengerCount() throws InterruptedException {
        int adultCount = 3;
        int childrenCount = 2;
        WebElement personField = driver.findElement(By.xpath("//div[contains(@class,'widget-container__filter-bar__pax-selection')]//button[@role='button']"));
        personField.click();
        Thread.sleep(1000);

        WebElement adultField = driver.findElement(By.xpath("//label[contains(@class,'pax-dropdown__label pax-dropdown__label--adult')]//i[contains(@class,'stepper-input__btn__icon stepper-input__btn--plus__icon skp-iconmoon-icon')]"));
        for (int i = 0; i < adultCount-1; i++) {
            adultField.click();
            Thread.sleep(1000);
        }
        System.out.println("Adult count: " + adultCount);

        WebElement childrenField = driver.findElement(By.xpath("//label[contains(@class,'pax-dropdown__label pax-dropdown__label--children')]//i[contains(@class,'stepper-input__btn__icon stepper-input__btn--plus__icon skp-iconmoon-icon')]"));
        for (int i = 0; i < childrenCount; i++) {
            childrenField.click();
            Thread.sleep(1000);
        }
        System.out.println("Children count: " + childrenCount);

        WebElement doneButton = driver.findElement(By.xpath("//span[normalize-space()='Done']"));
        doneButton.click();
        Thread.sleep(1000);
    }
    
    @Test(dependsOnMethods = {"modifyPassengerCount"})
    public void displayAvailableFlights() throws InterruptedException {
        WebElement modifyButton = driver.findElement(By.xpath("//span[normalize-space()='Modify']"));
        modifyButton.click();
        Thread.sleep(1000);
        System.out.println("Available Flights are displayed.....");
    }
    
    @AfterClass
    public static void closeDriver() {
        driver.quit();
    }
}
