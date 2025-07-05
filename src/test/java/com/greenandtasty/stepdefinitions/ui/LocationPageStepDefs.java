package com.greenandtasty.stepdefinitions.ui;

import com.greenandtasty.hooks.ui.UITestContext;
import com.greenandtasty.ui.pageobjects.LocationPage;
import com.greenandtasty.ui.pageobjects.MainPage;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

public class LocationPageStepDefs {
    final UITestContext testContext = UITestContext.getInstance();

    @When("the user navigates to the location page of {string}")
    public void theUserNavigatesToTheLocationPageOf(String locationName) {
        MainPage page = testContext.getMainPage();
        LocationPage locationPage = page.clickOnLocationCard(locationName);
        testContext.setLocationPage(locationPage);
    }

    @Then("the location address must be visible")
    public void theLocationAddressMustBeVisible() {
        LocationPage locationPage = testContext.getLocationPage();
        String actualAddress = locationPage.getLocationAddress();
        System.out.println(actualAddress);
        Assert.assertFalse(actualAddress.isEmpty());
        Assert.assertNotNull(actualAddress);
    }

    @Then("the location details must be visible")
    public void theLocationDetailsMustBeVisible() {
        LocationPage locationPage = testContext.getLocationPage();
        String actualDetails = locationPage.getLocationDetails();
        System.out.println(actualDetails);
        Assert.assertFalse(actualDetails.isEmpty());
        Assert.assertNotNull(actualDetails);
    }

    @Then("the location rating must be visible to the user")
    public void theLocationRatingMustBeVisibleToTheUser() {
        LocationPage locationPage = testContext.getLocationPage();
        String actualRatings = locationPage.getRestaurantRating();
        System.out.println(actualRatings);
        Assert.assertFalse(actualRatings.isEmpty());
        Assert.assertNotNull(actualRatings);
    }


    @Then("if the user clicks on book a table button he must be able to navigate to the booking page")
    public void ifTheUserClicksOnBookATableButtonHeMustBeAbleToNavigateToTheBookingPage() {
        LocationPage locationPage = testContext.getLocationPage();
        Assert.assertTrue(locationPage.checkBookATableButton());
    }

    @Then("the speciality dishes of the location must be visible")
    public void theSpecialityDishesOfTheLocationMustBeVisible() {
        LocationPage locationPage=testContext.getLocationPage();
        Assert.assertFalse(locationPage.getSpecialDishesDesciption().isEmpty());
        Assert.assertTrue(locationPage.getSpecialDishesDesciption().size()>0);
    }


    @Then("the user must be able to see the service based review of that location")
    public void theUserMustBeAbleToSeeTheServiceBasedReviewOfThatLocation() throws InterruptedException {
        LocationPage locationPage=testContext.getLocationPage();
        System.out.println(locationPage.getServiceReviews());
        Assert.assertFalse(locationPage.getServiceReviews().isEmpty());
        Assert.assertTrue(locationPage.getServiceReviews().size()>0);
    }

    @Then("the user must be able to see the cuisine based review of that location")
    public void theUserMustBeAbleToSeeTheCuisineBasedReviewOfThatLocation() {
        LocationPage locationPage=testContext.getLocationPage();
        System.out.println(locationPage.getCuisineReviews());
        Assert.assertFalse(locationPage.getCuisineReviews().isEmpty());
        Assert.assertTrue(locationPage.getCuisineReviews().size()>0);
    }
}
