package com.greenandtasty.ui.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;

import static com.greenandtasty.constant.Location.*;

public class BookingPage extends BasePage{

    @FindBy(xpath = "//h1[contains(text(),'Book')]//following-sibling::div//button/div")
    WebElement addressDropdown;

    @FindBy(css = "input[type='date']")
    WebElement dateInput;

    @FindBy(css = "input[type='time']")
    WebElement timeInput;

    @FindBy(xpath = "//button[contains(text(),'Find')]/parent::*//preceding-sibling::div[1]/button[2]")
    WebElement increaseGuestNumberButton;

    @FindBy(xpath = "//button[contains(text(),'Find')]/parent::*//preceding-sibling::div[1]/button[1]")
    WebElement decreaseGuestNumberButton;

    @FindBy(xpath = "//button[contains(text(),'Find')]/parent::*//preceding-sibling::div[1]/span")
    WebElement guestNumberText;

    @FindBy(xpath = "//button[contains(text(),'Find')]")
    WebElement submitButton;

    @FindBy(xpath = "//p[contains(text(),'Available slots')]/following-sibling::div//button")
    List<WebElement> listOfAllBookingSlots;

    @FindBy(id = "submit-reservation-button")
    WebElement submitReservationButton;

    @FindBy(id = "success-title")
    List<WebElement> successfulReservationMessage;

    @FindBy(css = "div[role='alert']")
    WebElement toastAlert;

    @FindBy(xpath = "//*[contains(text(),'Available slots')]/following-sibling::div//button")
    List<WebElement> reservationSlots;

    public BookingPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public boolean isPageLoaded() {
        wait.until(ExpectedConditions.not(ExpectedConditions.textToBePresentInElementValue(By.cssSelector("button#location-dropdown div"), "Location")));
        return driver.findElement(By.xpath("//nav//a[not(contains(@class, 'transition'))]"))
                    .getText()
                    .toLowerCase()
                    .contains("book");
    }

    public BookingPage enterDate(){
        sendKey(dateInput, FUTURE_DATE_DDMMYYYY);
        return this;
    }

    public BookingPage enterTime(String time){
        sendKey(timeInput, time);
        return this;
    }

    public BookingPage enterTime(){
        sendKey(timeInput, "1400");
        return this;
    }

    public BookingPage selectAddress(String address){
        clickOnAWebElement(addressDropdown);
        clickOnAWebElement(driver.findElement(By.xpath("//li[contains(text(),'" +address.strip()+ "')]")));
        return this;
    }

    public void clickFindATable(){
        clickOnAWebElement(submitButton);
    }

    public String getCurrentGuestNumber(){
        return driver.findElement(By.xpath("//button[contains(text(),'Find')]/parent::*//preceding-sibling::div[1]/span"))
                .getText();
    }

    public String getCurrentLocation(){
        return driver.findElement(By.className("button#location-dropdown div")).getText();
    }

    public void increaseGuests(){
        clickOnAWebElement(increaseGuestNumberButton);
    }

    public void decreaseGuests(){
        clickOnAWebElement(decreaseGuestNumberButton);
    }

    public boolean isTableAvailable(){
        return !driver.findElements(By.tagName("h3")).isEmpty();

    }

    public void selectFirstBooking(){
        waitForElement(driver.findElement(By.xpath("//*[contains(text(),'Available slots')]/following-sibling::div//button")));
        clickOnAWebElement(listOfAllBookingSlots.get(0));
    }

    public void submitReservation(){
        clickOnAWebElement(submitReservationButton);
    }

    public boolean isGuestNumberWhenReservationConfirmedEqual(int guests) {
        return !driver.findElements(
                By.xpath("//strong[contains(text(),'" + guests + "')]"))
                .isEmpty();
    }

    public boolean isDateWhenReservationConfirmedEqual() {
        return !driver.findElements(
                        By.xpath("//strong[contains(text(),'" + FUTURE_DATE + "')]"))
                .isEmpty();
    }

    public boolean isPleaseLoginPromptDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(toastAlert));
            return toastAlert.getText().toLowerCase().contains("please log in");
        } catch (TimeoutException | NoSuchElementException e) {
            return false;
        }
    }
    public boolean isReservationSuccessful(){
        return !successfulReservationMessage.isEmpty();
    }

    public List<WebElement> getListOfAllBookingSlots(){
        return driver.findElements(By.xpath("//*[contains(text(),'Available slots')]/following-sibling::div//button"));
    }
}
