package com.greenandtasty.ui.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LocationPage extends BasePage {


    @FindBy(xpath = "//h1[@id=\"location-title\"]/following-sibling::div//p")
    WebElement locationAddress;

    @FindBy(id = "location-description")
    WebElement shortLocationDescription;

    @FindBy(id = "location-rating")
    WebElement restaurantRating;

    @FindBy(xpath = "//button[@id='bookTableBtn']")
    WebElement bookATableButton;

    @FindBy(xpath = "//section[@id=\"speciality-dishes-section\"]/div/div")
    List<WebElement> specialDishes;

    @FindBy(id = "tab-service")
    WebElement reviewByServiceButton;

    @FindBy(id = "tab-cuisine")
    WebElement reviewByCuisineButton;

    @FindBy(id = "sort-selected-option")
    WebElement sortingButton;

    By reviewsLocator = By.xpath("//div[@id=\"reviews-container\"]/div[1]");

    By bookATableHeader = By.xpath("//h1[text()=\"Book a Table\"] ");


    public LocationPage(WebDriver driver) {
        super(driver);
    }

    @Override
    protected boolean isPageLoaded() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h1[text()='Green & Tasty']")));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    public String getLocationAddress() {
        if (locationAddress.isDisplayed()) return locationAddress.getText();
        return "";
    }

    public String getLocationDetails() {
        if (shortLocationDescription.isDisplayed()) return shortLocationDescription.getText();
        return "";
    }

    public String getRestaurantRating() {
        if (restaurantRating.isDisplayed()) return restaurantRating.getText();
        return "";
    }

    public boolean checkBookATableButton() {
        scrollToAnELement(locationAddress);
        clickOnAWebElement(bookATableButton);
        return driver.findElement(bookATableHeader).isDisplayed();
    }

    public List<Map<String, String>> getSpecialDishesDesciption() {
        List<Map<String, String>> itemsList = new ArrayList<>();
        for (WebElement dishCard : specialDishes) {
            Map<String, String> dishDetails = new HashMap<>();
            if (dishCard.isDisplayed()) {
                String name = dishCard.findElement(By.tagName("h3")).getText();
                String price = dishCard.findElement(By.xpath(".//p[1]")).getText();
                String quantity = dishCard.findElement(By.xpath(".//p[2]")).getText();
                dishDetails.put("name", name);
                dishDetails.put("price", price);
                dishDetails.put("quantity", quantity);
                itemsList.add(dishDetails);
            }
        }
        return itemsList;
    }

    public List<Map<String, String>> getServiceReviews()  {
        List<Map<String, String>> serviceReview = new ArrayList<>();
        clickOnAWebElement(reviewByServiceButton);
        WebElement review = driver.findElement(reviewsLocator);
        scrollToAnELement(review);
        Map<String, String> reviewDetails = new HashMap<>();
        if (review.isDisplayed()) {
            String userNameText = driver.findElement(By.xpath("//div[@id=\"reviews-container\"]/div[1]//h4")).getText();
            String date = driver.findElement(By.xpath("//div[@id=\"reviews-container\"]/div[1]//p[1]")).getText();
            String comment = driver.findElement(By.xpath("//div[@id=\"reviews-container\"]/div[1]/p")).getText();
            reviewDetails.put("userName", userNameText);
            reviewDetails.put("date", date);
            reviewDetails.put("comment", comment);
            serviceReview.add(reviewDetails);
        }
        return serviceReview;

    }
    public List<Map<String, String>> getCuisineReviews()  {
        List<Map<String, String>> cuisineReview = new ArrayList<>();
        clickOnAWebElement(reviewByCuisineButton);
        WebElement review = driver.findElement(reviewsLocator);
        Map<String, String> reviewDetails = new HashMap<>();
        if (review.isDisplayed()) {
            String userNameText = driver.findElement(By.xpath("//div[@id=\"reviews-container\"]/div[1]//h4")).getText();
            String date = driver.findElement(By.xpath("//div[@id=\"reviews-container\"]/div[1]//p[1]")).getText();
            String comment = driver.findElement(By.xpath("//div[@id=\"reviews-container\"]/div[1]/p")).getText();
            reviewDetails.put("userName", userNameText);
            reviewDetails.put("date", date);
            reviewDetails.put("comment", comment);
            cuisineReview.add(reviewDetails);
        }
        return cuisineReview;

    }
}




