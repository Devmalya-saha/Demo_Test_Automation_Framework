package com.greenandtasty.stepdefinitions.api;

import com.greenandtasty.api.models.Feedback;
import com.greenandtasty.api.models.ReservationByCustomer;
import com.greenandtasty.api.models.SignUp;
import com.greenandtasty.api.utils.ApiEndPoints;
import com.greenandtasty.api.utils.ApiUtil;
import com.greenandtasty.api.utils.RequestSpecificationBuilder;
import com.greenandtasty.api.utils.SignUpObject;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.testng.Assert;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public class FeedbackStepDefs {
    SignUp signUp;
    static String reservationId;
    Response response;

    @Given("The user logs in")
    public void theUserLogsIn() {
        signUp = SignUpObject.signUpObject();
        ApiUtil.postRequest(RequestSpecificationBuilder.setRequestSpecificationWithoutAuth(),
                ApiEndPoints.getEndPoint("SIGN_UP"),
                signUp);
    }

    @Given("the user made a reservation for table number {string} for {string} guest and from time {string} to {string} at {string} for a present day")
    public void theUserHasLoggedInAndMadeAReservationForTableNumberForGuestAndFromTimeTo(String tableNumber, String guestsNumber, String timeFrom, String timeTo, String locationId) {
        ReservationByCustomer reservationByCustomerbody = ReservationByCustomer.builder()
                .locationId(locationId)
                .tableNumber(tableNumber)
                .date(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .guestsNumber(guestsNumber)
                .timeFrom(timeFrom)
                .timeTo(timeTo)
                .build();
        response = ApiUtil.postRequest(RequestSpecificationBuilder.setRequestSpecificationWithAuth(signUp)
                , ApiEndPoints.getEndPoint("TABLE_BOOKING_BY_CLIENT")
                , reservationByCustomerbody);
        response.then().log().all();
        reservationId=response.jsonPath().getString("id");
        reservationId = reservationId.substring(1, reservationId.length() - 1);


    }


    @When("the user tries to give a cuisine rating {string} and service rating of {string}")
    public void theUserTriesToGiveACuisineRatingeAndServiceRatingOf(String cuisineRating, String serviceRating) {
        Feedback feedback = Feedback.builder()
                .cuisineComment("The food is really good")
                .cuisineRating(cuisineRating.equalsIgnoreCase("null")?null:cuisineRating)
                .reservationId(reservationId)
                .serviceComment("The service was really fast and good")
                .serviceRating(serviceRating.equalsIgnoreCase("null")?null:serviceRating)
                .build();
        response=ApiUtil.postRequest(RequestSpecificationBuilder.setRequestSpecificationWithAuth(signUp),
                ApiEndPoints.getEndPoint("FEEDBACKS"),
                feedback);
        response.then().log().all();
    }

    @Given("the user has logged in and made a reservation for table number {string} for {string} guest and from time {string} to {string} at {string} for a future reservation")
    public void theUserHasLoggedInAndMadeAReservationForTableNumberForGuestAndFromTimeToAtForAFutureReservation(String tableNumber, String guestsNumber, String timeFrom, String timeTo, String locationId) {
        ReservationByCustomer reservationByCustomerbody = ReservationByCustomer.builder()
                .locationId(locationId)
                .tableNumber(tableNumber)
                .date(LocalDate.now().plus(Period.ofDays(1)).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .guestsNumber(guestsNumber)
                .timeFrom(timeFrom)
                .timeTo(timeTo)
                .build();
        response = ApiUtil.postRequest(RequestSpecificationBuilder.setRequestSpecificationWithAuth(signUp)
                , ApiEndPoints.getEndPoint("TABLE_BOOKING_BY_CLIENT")
                , reservationByCustomerbody);
        response.then().log().all();
        reservationId=response.jsonPath().getString("id");
        reservationId = reservationId.substring(1, reservationId.length() - 1);
    }

    @Then("the response status-code should be {int}")
    public void theResponseStatusCodeShouldBe(int expectedStatusCode) {
        Assert.assertEquals(response.getStatusCode(),expectedStatusCode);
    }

    @And("the response-body should have message {string}")
    public void theResponseBodyShouldHaveMessage(String expectedMessage) {
        Assert.assertEquals(response.jsonPath().getString("message"),expectedMessage);
    }

    @When("the user tries to give a cuisine rating {string} and service rating of {string} for a reservationID not of the user")
    public void theUserTriesToGiveACuisineRatingAndServiceRatingOfForAReservationIDNotOfTheUser(String cuisineRating, String serviceRating) {
        Feedback feedback = Feedback.builder()
                .cuisineComment("The food is really good")
                .cuisineRating(cuisineRating)
                .reservationId("2025-05-20location-testtable-201slot1")
                .serviceComment("The service was really fast and good")
                .serviceRating(serviceRating)
                .build();
        response=ApiUtil.postRequest(RequestSpecificationBuilder.setRequestSpecificationWithAuth(signUp),
                ApiEndPoints.getEndPoint("FEEDBACKS"),
                feedback);

    }


}
