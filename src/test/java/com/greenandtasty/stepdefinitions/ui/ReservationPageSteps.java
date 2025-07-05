package com.greenandtasty.stepdefinitions.ui;

import com.greenandtasty.hooks.ui.UITestContext;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

import static com.greenandtasty.constant.Reservation.*;

public class ReservationPageSteps {
    UITestContext testContext=UITestContext.getInstance();

    @Given("the customer has a reservation marked as {string}")
    public void aCustomerHasAReservationMarkedAs(String status) {
        testContext.setReservationPage(testContext.getMainPage().clickOnReservationTab());
        //Assert.assertTrue(testContext.getReservationPage().reservationHasStatus(status));
    }

    @Given("the customer has already given the feedback")
    public void theCustomerHasAlreadyGivenTheFeedback() {
        Assert.assertTrue(testContext.getReservationPage().isUpdateFeedbackButtonVisible());
    }

    @Given("a customer is providing feedback")
    public void aCustomerIsProvidingFeedback() {
        testContext.setReservationPage(testContext.getMainPage().clickOnReservationTab());
        testContext.getReservationPage().clickOnLeaveFeedbackButton();
    }

    @When("the customer enters textual comment without star rating")
    public void theCustomerSubmitsFeedbackWithoutAStarRating() {
        testContext.getReservationPage().enterFeedbackText(FEEDBACK_TEXT);
    }

    @Then("the customer should see an option to provide feedback for {string}")
    public void theCustomerShouldSeeAnOptionToProvideFeedbackForService(String typeOfFeedback) {
        Assert.assertTrue(testContext.getReservationPage().isFeedbackButtonVisible());
        testContext.setReservationLocation(testContext.getReservationPage().getReservationLocationForNewFeedback());
        testContext.getReservationPage().clickOnLeaveFeedbackButton();
        if(typeOfFeedback.equals("Service"))
            testContext.getReservationPage().clickOnServiceFeedbackButton();
        else if(typeOfFeedback.equals("Culinary"))
            testContext.getReservationPage().clickOnCulinaryFeedbackButton();
    }

    @Then("the feedback should include a star rating")
    public void theFeedbackShouldIncludeAStarRating() {
        Assert.assertTrue(testContext.getReservationPage().isStarRatingVisible());
        testContext.getReservationPage().clickOnStarRating(STAR_RATING);
    }

    @Then("the feedback should optionally include a textual comment")
    public void theFeedbackShouldOptionallyIncludeATextualComment() {
        Assert.assertTrue(testContext.getReservationPage().isTextualCommentVisible());
        testContext.getReservationPage().enterFeedbackText(FEEDBACK_TEXT);
    }

    @Then("the customer should be able to submit the {string} feedback")
    public void theCustomerShouldBeAbleToSubmitTheFeedback(String typeOfFeedback) {
        testContext.getReservationPage().clickOnSubmitFeedbackButton();
        Assert.assertTrue(testContext.getReservationPage().confirmFeedbackIsVisible(testContext.getReservationLocation(), typeOfFeedback));
    }

    @Then("the customer should be able to view their existing feedback")
    public void viewExistingFeedback() {
        testContext.setReservationLocation(testContext.getReservationPage().getReservationLocationForNewFeedback());
        testContext.getReservationPage().clickOnUpdateFeedbackButton();
        Assert.assertTrue(testContext.getReservationPage().hasFeedback());
    }

    @Then("the customer should be able to submit the updated feedback")
    public void submitUpdatedFeedback() {
        testContext.getReservationPage().clickOnServiceFeedbackButton()
                .clickOnStarRating(STAR_RATING)
                .enterFeedbackText(UPDATED_FEEDBACK_TEXT);

        testContext.getReservationPage().clickOnCulinaryFeedbackButton()
                .clickOnStarRating(STAR_RATING)
                .enterFeedbackText(UPDATED_FEEDBACK_TEXT)
                .clickOnSubmitFeedbackButton();
        Assert.assertTrue(testContext.getReservationPage()
                .confirmFeedbackIsVisible(testContext.getReservationLocation(), FEEDBACK_TYPE_CUISINE));
        //Assert.assertTrue(testContext.getReservationPage()
                //.confirmFeedbackIsVisible(testContext.getReservationLocation(), FEEDBACK_TYPE_SERVICE));

    }

    @Then("the submit button should be disabled")
    public void theSubmitButtonShouldBeDisabled() {
        Assert.assertFalse(testContext.getReservationPage().isSubmitButtonEnabled());
    }


    @Then("the system should not allow feedback submission for {string} reservation")
    public void theSystemShouldNotAllowFeedbackSubmission(String typeOfReservation) {
        Assert.assertFalse(testContext.getReservationPage().isFeedbackButtonVisible(typeOfReservation));
    }
}