package com.greenandtasty.stepdefinitions.api;

import com.greenandtasty.api.models.ReservationByCustomer;
import com.greenandtasty.api.models.SignUp;
import com.greenandtasty.api.utils.*;
import com.greenandtasty.hooks.api.ApiContext;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.testng.Assert;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookingByClient {

    Response response;
    Map<String, String> queryParams = new HashMap<>();
    boolean slotStillAvailable;
    final ApiContext context;

    public BookingByClient() {
        this.context = ApiContext.getInstance();
    }

    @Given("the user logged in")
    public void theUserLoggedIn() {
        SignUp signUp = SignUpObject.signUpObject();
        ApiUtil.postRequest(RequestSpecificationBuilder.setRequestSpecificationWithoutAuth(),
                ApiEndPoints.getEndPoint("SIGN_UP"),
                signUp);
        context.setSignedUpUser(signUp);
    }


    @When("the user sends a POST request to the end point for booking a table {string} for {string} people from {string} to {string} at location {string}")
    public void theUserSendsAPOSTRequestToTheEndPointForBookingATableForPeopleFromToAtLocation(String tableNumber, String guestNumber, String timeFrom, String timeTo, String location) {
        SignUp signUp=context.getSignedUpUser();
        ReservationByCustomer reservationByCustomer = ReservationByCustomer.builder()
                .locationId("null".equals(location) ? null : location)
                .tableNumber("null".equals(tableNumber) ? null : tableNumber)
                .date(LocalDate.now().plus(Period.ofDays(1)).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .guestsNumber("null".equals(guestNumber) ? null : guestNumber)
                .timeFrom("null".equals(timeFrom) ? null : timeFrom)
                .timeTo("null".equals(timeTo) ? null : timeTo)
                .build();

        response = ApiUtil.postRequest(RequestSpecificationBuilder.setRequestSpecificationWithAuth(signUp),
                ApiEndPoints.getEndPoint("TABLE_BOOKING_BY_CLIENT"),
                reservationByCustomer);
        response.then().log().all();
        context.setResponse(response);
        if (response.getStatusCode() == 200) {
            context.setReservationId(response.jsonPath().getString("id"));
        }
    }

    @Then("the response status code must be {int}")
    public void theResponseStatusCodeMustBe(int expectedStatusCode) {
        int actualStatusCode = context.getResponse().getStatusCode();
        System.out.println(actualStatusCode);
        Assert.assertEquals(actualStatusCode, expectedStatusCode);
    }

    @Then("delete the user")
    public void deleteTheUser() {
        SignUp signUp = context.getSignedUpUser();
        DeleteObject deleteUser = new DeleteObject(signUp);
        response = ApiUtil.deleteRequest(RequestSpecificationBuilder.setRequestSpecificationWithAuth(signUp)
                , ApiEndPoints.getEndPoint("USER_DELETION"),
                deleteUser);
    }

    @And("the response body should not be empty and status should be {string}")
    public void theResponseBodyShouldNotBeEmptyAndStatusShouldBe(String expectedMessage) {
        String actualStatus = context.getResponse().jsonPath().getString("[0].status");
        Assert.assertEquals(actualStatus, expectedMessage);

    }

    @Given("another user signs in and send a POST request to the end point for booking the table {string} for {string} people from {string} to {string} at location {string}")
    public void anotherUserSignsInAndSendAPOSTRequestToTheEndPointForBookingTheTableForPeopleFromToAtLocation(String tableNumber, String guestNumber, String timeFrom, String timeTo, String location) {
        SignUp newUser = SignUpObject.signUpObject();
        ApiUtil.postRequest(RequestSpecificationBuilder.setRequestSpecificationWithoutAuth(),
                ApiEndPoints.getEndPoint("SIGN_UP"),
                newUser);
        ReservationByCustomer reservationByCustomer = ReservationByCustomer.builder()
                .locationId(location)
                .tableNumber(tableNumber)
                .date(LocalDate.now().plus(Period.ofDays(1)).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .guestsNumber(guestNumber)
                .timeFrom(timeFrom)
                .timeTo(timeTo)
                .build();
        response = ApiUtil.postRequest(RequestSpecificationBuilder.setRequestSpecificationWithAuth(newUser)
                , ApiEndPoints.getEndPoint("TABLE_BOOKING_BY_CLIENT")
                , reservationByCustomer);
        context.setResponse(response);

    }

    @And("the response message should be {string}")
    public void theResponseMessageShouldBe(String expectedMessage) {
        String actualMessage = context.getResponse().jsonPath().get("message");
        Assert.assertEquals(actualMessage, expectedMessage);
    }

    @When("the user sends a POST request to the end point for booking a table {string} for {string} people from {string} to {string} at location {string} but for a pastdate")
    public void theUserSendsAPOSTRequestToTheEndPointForBookingATableForPeopleFromToAtLocationButForAPastdate(String tableNumber, String guestNumber, String timeFrom, String timeTo, String location) {
        ReservationByCustomer reservationByCustomer = ReservationByCustomer.builder()
                .locationId(location)
                .tableNumber(tableNumber)
                .date(LocalDate.now().minus(Period.ofDays(1)).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .guestsNumber(guestNumber)
                .timeFrom(timeFrom)
                .timeTo(timeTo)
                .build();
        SignUp signUp=context.getSignedUpUser();
        response = ApiUtil.postRequest(RequestSpecificationBuilder.setRequestSpecificationWithAuth(signUp)
                , ApiEndPoints.getEndPoint("TABLE_BOOKING_BY_CLIENT")
                , reservationByCustomer);
        context.setResponse(response);
    }


    @And("then send a GET request to the bookings table end point for that {string} and for number of guests {string}")
    public void thenSendAGETRequestToTheBookingsTableEndPointForThatAndForNumberOfGuests(String locationId, String guestNumber) {
        queryParams.put("locationId", locationId);
        queryParams.put("date", LocalDate.now().plus(Period.ofDays(1)).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        queryParams.put("time", "12:00");
        queryParams.put("guests", guestNumber);
        response = ApiUtil.getRequest(RequestSpecificationBuilder.setRequestSpecificationWithoutAuth(),
                ApiEndPoints.getEndPoint("AVAILABLE_TABLES"),
                Collections.emptyMap(),
                queryParams);
        context.setResponse(response);
    }

    @Then("check if the slot for {string} for {string} to {string} is present or not")
    public void checkIfTheSlotForForToIsPresentOrNot(String tableNumber, String timeFrom, String timeTo) {
        //check naming convention for this method
        String bookedTimeSlot = timeFrom + "-" + timeTo;
        List<Map<String, Object>> tables = response.jsonPath().getList("");
        for (Map<String, Object> table : tables) {
            String retrievedTableNumber = (String) table.get("tableNumber");
            if (retrievedTableNumber.equals(tableNumber)) {
                List<String> availableSlots = (List<String>) table.get("availableSlots");
                if (availableSlots.contains(bookedTimeSlot)) {
                    slotStillAvailable = true;
                    break;
                }
            }

        }
        Assert.assertFalse(slotStillAvailable);
    }

}
