package com.greenandtasty.stepdefinitions.ui;

import com.greenandtasty.hooks.ui.UITestContext;
import com.greenandtasty.ui.drivermanagement.DriverManager;
import com.greenandtasty.ui.pageobjects.BookingPage;
import com.greenandtasty.ui.pageobjects.LoginPage;
import com.greenandtasty.ui.pageobjects.MainPage;
import com.greenandtasty.ui.pageobjects.ReservationPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import static com.greenandtasty.constant.Location.USER_EMAIL;
import static com.greenandtasty.constant.Location.USER_PASSWORD;

public class BookingPageSteps {
    BookingPage page;
    MainPage mainPage;
    LoginPage loginPage;
    String baseUrl = "http://team-12-frontend-bucket.s3-website.eu-west-3.amazonaws.com/";

    final UITestContext testContext=UITestContext.getInstance();

    @Given("the booking page is loaded")
    public void openBookingPage(){
        testContext.getDriver().get(baseUrl+"bookTable");
        page = new BookingPage(testContext.getDriver());
        page.isPageLoaded();
    }

    @When("address, future date, time and {string} are selected")
    public void createBooking(String guestNumber){
        while(!page.getCurrentGuestNumber().equals(guestNumber)){
            if(page.getCurrentGuestNumber().compareTo(guestNumber)>0)
                page.decreaseGuests();
            else
                page.increaseGuests();
        }
        page.enterDate()
            .enterTime()
            .clickFindATable();
    }

    @When("{string}, future date, time and {string} are selected")
    public void createBookingWithCustomAddressAndGuestNumber(String address, String guestNumber){
        while(!page.getCurrentGuestNumber().equals(guestNumber)){
            if(page.getCurrentGuestNumber().compareTo(guestNumber)>0)
                page.decreaseGuests();
            else
                page.increaseGuests();
        }
        page.selectAddress(address)
            .enterDate()
            .clickFindATable();
    }

    @When("a slot is selected and confirmed")
    public void selectASlotForBooking(){
        testContext.setInitialNumberOfReservations(page.getListOfAllBookingSlots().size());
        page = new BookingPage(testContext.getDriver());
        page.selectFirstBooking();
        page.submitReservation();
    }

    @When("the user navigates to reservation page")
    public void navigateToReservationPage() {
        testContext.getDriver().get(baseUrl+"reservation");
        testContext.setReservationPage(new ReservationPage(testContext.getDriver()));
    }

    @Then("tables should be viewable")
    public void tablesShouldBeViewable(){
        Assert.assertTrue(page.isTableAvailable());
    }

    @Then("{string}, future date and time should match")
    public void validateReservationDetails(String guestsNumber){
        page = new BookingPage(testContext.getDriver());
        SoftAssert softAssert = new SoftAssert();

        softAssert.assertTrue(page.isGuestNumberWhenReservationConfirmedEqual(Integer.parseInt(guestsNumber)));
        softAssert.assertTrue(page.isDateWhenReservationConfirmedEqual());
        softAssert.assertAll();
    }

    @Then("a reservation confirmed message should be displayed")
    public void checkReservationConfirmedMessageDisplayed(){
        page = new BookingPage(testContext.getDriver());
        Assert.assertTrue(page.isReservationSuccessful());
    }

    @Then("the application should prompt the customer to log in or sign up")
    public void checkForLoginOrSignUpPrompt() {
        Assert.assertTrue(page.isPleaseLoginPromptDisplayed(), "Please login prompt is not displayed");
    }

    @Then("the same slot should not be available for {string} and {string}")
    public void checkForSlotAvailability(String address, String guestNumber) {
        page.refreshPage();
        page.isPageLoaded();
        createBookingWithCustomAddressAndGuestNumber(address, guestNumber);
        int differenceInSlotNum = testContext.getInitialNumberOfReservations()
                -new BookingPage(testContext.getDriver()).getListOfAllBookingSlots().size();
        Assert.assertEquals(differenceInSlotNum, 1, "The difference in initial and final slot numbers is not 1.\n\tInitial: "+testContext.getInitialNumberOfReservations()+"\n\tFinal: "+new BookingPage(testContext.getDriver()).getListOfAllBookingSlots().size()+"\n");
    }

    @Then("the user should see an option to edit their reservation")
    public void checkEditReservationOptionIsVisible() {
        Assert.assertTrue(testContext.getReservationPage().isEditReservationButtonVisible(), "Edit reservation button is not displayed");
    }

    @Then("the user should see an option to cancel their reservation")
    public void checkCancelReservationOptionIsVisible() {
        Assert.assertTrue(testContext.getReservationPage().isCancelReservationButtonVisible(), "Cancel reservation button is not displayed");
    }

    @Then("the user should be able to cancel their reservation")
    public void cancelReservation() {
        int initialCancelButtonCount = testContext.getReservationPage().getListOfCancelButtons().size();
        testContext.getReservationPage().clickOnCancelReservationButton();
        int finalCancelButtonCount = testContext.getReservationPage().getListOfCancelButtons().size();
        Assert.assertEquals(initialCancelButtonCount-finalCancelButtonCount, 1, "The difference in initial and final cancel button count is not 1.");
    }
}
