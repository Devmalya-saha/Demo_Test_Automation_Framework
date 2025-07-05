package com.greenandtasty.ui.pageobjects;

import com.github.javafaker.Bool;
import com.greenandtasty.api.models.Feedback;
import com.greenandtasty.api.utils.ApiEndPoints;
import com.greenandtasty.api.utils.ApiUtil;
import com.greenandtasty.api.utils.RequestSpecificationBuilder;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


@Getter
@Setter
public class MainPage extends BasePage {

    @FindBy(xpath = "//img[@alt='Green & Tasty Logo']")
    WebElement mainPageLogo;

    @FindBy(xpath = "//a[@href='/bookTable']")
    WebElement bookTableTab;

    @FindBy(xpath = "//a[text()='Main Page']")
    WebElement mainPageTab;

    @FindBy(xpath = "//a[@href='/login']")
    WebElement signInButton;

    @FindBy(xpath = "//h2[contains(text(),'Location')]")
    WebElement locationHeading;

    @FindBy(id = "viewMenuBtn")
    WebElement viewMenuButton;

    @FindBy(xpath = "//div[@role='alert']")
    WebElement loginSuccessfulAlert;

    @FindBy(xpath = "//section[@id=\"popular-dishes-section\"]/*/div")
    List<WebElement> popularDishes;

    @FindBy(xpath = " //section[@id='locations-section']/*/div")
    List<WebElement> locations;

    @FindBy(xpath = "//div[@id='location-card-location-002']")
    WebElement firstLocationCard;

    @FindBy(xpath = "//div[@id='location-card-location-001']")
    WebElement secondLocationCard;

    @FindBy(xpath = "//div[@id='location-card-location-test']")
    WebElement thirdLocationCard;

    @FindBy(css = "a[href='/reservation']")
    WebElement reservationTab;

    @FindBy(xpath = "//div[@id='location-card-location-003']")
    WebElement fourthLocationCard;

    @FindBy(css = "#location-cards-container div")
    List<WebElement> locationCards;

    By roleAssigned = By.xpath("//div[@data-testid='user-dropdown']/p");

    By profileIcon = By.cssSelector("svg[aria-label='avatar']");


    public MainPage(WebDriver driver) {
        super(driver);
    }


    public boolean isLogoDisplayed() {
        return mainPageLogo.isDisplayed();
    }


    public void scrollToLocationsSection() {
        scrollToAnELement(locationHeading);
    }

    public LoginPage clickOnSignIn() {
        clickOnAWebElement(signInButton);
        return new LoginPage(driver);
    }

    public ReservationPage clickOnReservationTab() {
        clickOnAWebElement(reservationTab);
        return new ReservationPage(driver);
    }


    @Override
    public void clickOnAWebElement(WebElement button) {
        super.clickOnAWebElement(button);
    }

    @Override
    public void waitForElement(WebElement element) {
        super.waitForElement(element);
    }

    public WebElement getElementByName(String elementName) {
        return switch (elementName.toLowerCase()) {
            case "sign in button" -> signInButton;
            case "book table tab" -> bookTableTab;
            case "main page tab" -> mainPageTab;
            case "view menu button" -> viewMenuButton;
            default -> throw new IllegalArgumentException("Element name specified does not match");
        };
    }


    public boolean isPageLoaded() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(By.id("hero-title")));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    public boolean isLoginSuccessfulDisplayed() {
        try {
            System.out.println("Login Alert is visible: " + loginSuccessfulAlert.isDisplayed());
            System.out.println("Login Alert text: " + loginSuccessfulAlert.getText());
            return loginSuccessfulAlert.getText().toLowerCase().contains("successful");
        } catch (NoSuchElementException e) {
            System.out.println("Login Alert is not present: " + e);
            return false;
        } finally {
            wait.until(ExpectedConditions.invisibilityOf(loginSuccessfulAlert));
        }
    }

    public String getProfileNameAndRole() {
        waitForElement(driver.findElement(profileIcon));
        driver.navigate().refresh();
        clickOnAWebElement(driver.findElement(profileIcon));
        return driver.findElement(roleAssigned).getText();
    }

    public LocationPage clickOnLocationCard(String location) {
        return switch (location.toLowerCase()) {
            case "first location" -> {
                clickOnAWebElement(firstLocationCard);
                yield new LocationPage(driver);
            }
            case "second location" -> {
                clickOnAWebElement(secondLocationCard);
                yield new LocationPage(driver);
            }
            case "third location" -> {
                clickOnAWebElement(thirdLocationCard);
                yield new LocationPage(driver);
            }
            case "fourth location" -> {
                clickOnAWebElement(fourthLocationCard);
                yield new LocationPage(driver);
            }
            default -> throw new IllegalArgumentException("Element name specified does not match");
        };
    }

    public MainPage clickMainPageTab() {
        clickOnAWebElement(mainPageTab);
        return this;
    }

    public boolean isUserSignedIn() {
        try {
            return driver.findElement(profileIcon).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public LocationPage clickOnLocationFromAddress(String address) {

        return new LocationPage(driver);
    }
}
