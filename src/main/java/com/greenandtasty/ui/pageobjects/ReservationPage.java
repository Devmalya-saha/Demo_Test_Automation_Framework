package com.greenandtasty.ui.pageobjects;

import java.time.Duration;
import java.time.Instant;
import java.util.*;

import com.greenandtasty.api.utils.ApiEndPoints;
import com.greenandtasty.api.utils.ApiUtil;
import com.greenandtasty.api.utils.RequestSpecificationBuilder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static com.greenandtasty.constant.Reservation.FEEDBACK_TEXT;

public class ReservationPage extends BasePage{
    public ReservationPage(WebDriver driver) {
        super(driver);
    }
    @FindBy(id = "leave-feedback-button")
    WebElement leaveFeedbackButton;
    @FindBy(id = "reservation-card")
    List<WebElement> reservationCards;
    @FindBy(xpath = "//div[@role='dialog']//button[contains(text(), 'Service')]")
    WebElement serviceFeedbackButton;
    @FindBy(xpath = "//div[@role='dialog']//button[contains(text(), 'Culinary')]")
    WebElement culinaryFeedbackButton;
    @FindBy(tagName = "textarea")
    WebElement feedbackTextArea;
    @FindBy(xpath = "//div[@role='dialog']//span[contains(@class,'cursor-pointer')]")
    List<WebElement> starRating;
    @FindBy(className = "text-yellow-400")
    List<WebElement> selectedStarRating;
    @FindBy(xpath = "//div[@role='dialog']//button[contains(text(), 'Submit')]")
    WebElement submitFeedbackButton;
    @FindBy(id = "reservation-status")
    List<WebElement> reservationStatuses;
    @FindBy(xpath = "//*[@id='leave-feedback-button']/../preceding-sibling::div//*[@id='reservation-location']")
    WebElement reservationLocationForNewFeedback;
    @FindBy(id = "update-feedback-button")
    WebElement updateFeedbackButton;
    @FindBy(id = "edit-button")
    WebElement editReservationButton;
    @FindBy(id = "cancel-button")
    WebElement cancelReservationButton;
    @FindBy(xpath = "//div[contains(@class, \"Toastify\") and contains(text(),'Feedback submitted!')]")
    WebElement feedbackSubmittedToastMessage;

    @Override
    public boolean isPageLoaded() {
        return !reservationCards.isEmpty();
    }

    public ReservationPage clickOnLeaveFeedbackButton() {
        waitForElement(leaveFeedbackButton);
        clickOnAWebElement(leaveFeedbackButton);
        return this;
    }

    public ReservationPage clickOnServiceFeedbackButton() {
        waitForElement(serviceFeedbackButton);
        clickOnAWebElement(serviceFeedbackButton);
        return this;
    }

    public ReservationPage clickOnStarRating(int star) {
        waitForElement(starRating.get(star - 1));
        clickOnAWebElement(starRating.get(star - 1));
        return this;
    }

    public ReservationPage clickOnCulinaryFeedbackButton() {
        waitForElement(culinaryFeedbackButton);
        clickOnAWebElement(culinaryFeedbackButton);
        return this;
    }

    public ReservationPage enterFeedbackText(String feedback) {
        waitForElement(feedbackTextArea);
        sendKey(feedbackTextArea, feedback);
        return this;
    }

    public void clickOnSubmitFeedbackButton() {
        waitForElement(submitFeedbackButton);
        clickOnAWebElement(submitFeedbackButton);
    }

    public void clickOnUpdateFeedbackButton() {
        waitForElement(updateFeedbackButton);
        clickOnAWebElement(updateFeedbackButton);
    }

    public boolean confirmFeedbackIsVisible(String location, String typeOfFeedback) {
        waitForElement(feedbackSubmittedToastMessage);
        return feedbackSubmittedToastMessage.isDisplayed();
//        new MainPage(driver).clickMainPageTab();
//        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='location-cards-container']")));
//        String locationId;
//        try {
//            locationId = driver.findElements(By.xpath("//*[@id='location-cards-container']//*[contains(text(),'" + location + "')]/.."))
//                    .stream()
//                    .map(e -> e.getDomAttribute("data-testid"))
//                    .filter(Objects::nonNull)
//                    .map(e -> e.replace("location-address-", ""))
//                    .toList().get(0);
//        } catch (ArrayIndexOutOfBoundsException e) {
//            //log exception
//            System.out.println("Array Index Out Of Bounds Exception: " + e.getMessage());
//            return false;
//        }
//
//        List<LinkedHashMap<String, String>> feedbackList = ApiUtil.getRequest(RequestSpecificationBuilder.setRequestSpecificationWithoutAuth(),
//                        ApiEndPoints.getEndPoint("LOCATION_SPECIFIC_FEEDBACKS"),
//                        Collections.singletonMap("id", locationId),
//                        Map.of("type", typeOfFeedback, "sort", "date,desc"))
//                .jsonPath().getList("content");
//        System.out.println(typeOfFeedback+"::"+feedbackList);
//        return feedbackList.stream()
//                .filter(e -> Instant.parse(e.get("date")+"Z").isAfter(Instant.now().minusSeconds(5)))
//                .map(e -> e.get("comment"))
//                .filter(comment -> comment.contains("test feedback"))
//                .map(comment -> comment.substring(comment.lastIndexOf(" ") + 1))
//                .anyMatch(time -> Instant.parse(time).isAfter(Instant.now().minusSeconds(5)));
    }

    public boolean reservationHasStatus(String status) {
        return reservationStatuses.stream()
                .map(WebElement::getText)
                .anyMatch(e->e.equalsIgnoreCase(status));
    }

    public boolean isFeedbackButtonVisible() {
        return leaveFeedbackButton.isDisplayed();
    }

    public boolean isUpdateFeedbackButtonVisible() {
        return updateFeedbackButton.isDisplayed();
    }

    public boolean isStarRatingVisible() {
        return !starRating.isEmpty();
    }

    public boolean isTextualCommentVisible() {
        return feedbackTextArea.isDisplayed();
    }

    public boolean isSubmitButtonEnabled(){
        return submitFeedbackButton.isEnabled();
    }

    public boolean isFeedbackButtonVisible(String reservationStatus) {
        reservationStatus = (
                reservationStatus.equalsIgnoreCase("Cancelled") ? "Cancelled"
                        : (reservationStatus.equalsIgnoreCase("Future"))? "Reserved"
                        : "Finished");
        return !driver.findElements(By.xpath("//*[@id='reservation-status' and contains(text(),'" +reservationStatus+ "')]/ancestor::div[@id='reservation-card']//*[contains(text(),'Feedback')]"))
                .isEmpty();
    }

    public boolean isEditReservationButtonVisible() {
        return editReservationButton.isDisplayed();
    }

    public boolean isCancelReservationButtonVisible() {
        return cancelReservationButton.isDisplayed();
    }

    public List<WebElement> getListOfCancelButtons(){
        return driver.findElements(By.id("cancel-button"));
    }

    public void clickOnCancelReservationButton(){
        waitForElement(cancelReservationButton);
        clickOnAWebElement(cancelReservationButton);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@role='alert']")));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[@role='alert']")));
    }

    public String getReservationLocationForNewFeedback() {
        return reservationLocationForNewFeedback.getText();
    }

    public boolean hasStarRating(){
        return !selectedStarRating.isEmpty();
    }

    public boolean hasTextualComment(){
        return feedbackTextArea!=null && !feedbackTextArea.getText().isEmpty();
    }

    public boolean hasFeedback(){
        return this.clickOnServiceFeedbackButton().hasStarRating() || this.clickOnCulinaryFeedbackButton().hasStarRating();
    }
}
