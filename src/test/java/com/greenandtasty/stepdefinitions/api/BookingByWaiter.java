package com.greenandtasty.stepdefinitions.api;

import com.greenandtasty.api.models.ReservationByCustomer;
import com.greenandtasty.api.models.ReservationByWaiter;
import com.greenandtasty.api.models.SignUp;
import com.greenandtasty.api.utils.ApiEndPoints;
import com.greenandtasty.api.utils.ApiUtil;
import com.greenandtasty.api.utils.RequestSpecificationBuilder;
import com.greenandtasty.api.utils.SignUpObject;
import com.greenandtasty.constant.Waiter;
import com.greenandtasty.hooks.api.ApiContext;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.it.Ma;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;

public class BookingByWaiter {
    Response response;
    boolean slotStillAvailable;
    Map<String, Object> queryParams = new HashMap<>();
    ApiContext context;

    public BookingByWaiter() {
        this.context = ApiContext.getInstance();
    }

    @Given("the waiter is logged in")
    public void theWaiterIsLoggedIn() {
        SignUp signUp = SignUpObject.signUpObject();
        signUp.setEmail(Waiter.WAITER_EMAIL);
        signUp.setPassword(Waiter.WAITER_PASSWORD);
        context.setSignedUpUser(signUp);
    }

    @When("the waiter sends a POST request to the end point for booking a table {string} for {string} people from {string} to {string} at location {string} and for customer {string}")
    public void theWaiterSendsAPOSTRequestToTheEndPointForBookingATableForPeopleFromToAtLocationAndForCustomer(String tableNumber, String guestsNumber, String timeFrom, String timeTo, String locationId, String customerName) {
        ReservationByWaiter reservationByWaiterBody = ReservationByWaiter.builder()
                .clientType("VISITOR")
                .customerName(customerName)
                .locationId("null".equals(locationId) ? null : locationId)
                .tableNumber("null".equals(tableNumber) ? null : tableNumber)
                .date(LocalDate.now().plus(Period.ofDays(1)).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .guestsNumber("null".equals(guestsNumber) ? null : guestsNumber)
                .timeFrom("null".equals(timeFrom) ? null : timeFrom)
                .timeTo("null".equals(timeTo) ? null : timeTo)
                .build();
        SignUp signUp = context.getSignedUpUser();
        response = ApiUtil.postRequest(RequestSpecificationBuilder.setRequestSpecificationWithAuth(signUp),
                ApiEndPoints.getEndPoint("TABLE_BOOKING_BY_WAITER"),
                reservationByWaiterBody);
        context.setResponse(response);
        if (response.getStatusCode() == 200) {
            context.setReservationId(response.jsonPath().getString("id"));
        }
    }

    @Then("the response status-code must be {int}")
    public void theResponseStatusCodeMustBe(int expectedStatusCode) {
        System.out.println(context.getResponse().then().log().all());
        Assert.assertEquals(context.getResponse().getStatusCode(), expectedStatusCode);

    }


    @And("the response body should have status {string}")
    public void theResponseBodyShouldHaveStatus(String expectedStatus) {
        Assert.assertEquals(context.getResponse().jsonPath().getString("status"), expectedStatus);
    }

    @Given("another waiter signs in and send a POST request to the end point for booking the table {string} for {string} people from {string} to {string} at location {string} for customer {string}")
    public void anotherWaiterSignsInAndSendAPOSTRequestToTheEndPointForBookingTheTableForPeopleFromToAtLocationForCustomer(String tableNumber, String guestsNumber, String timeFrom, String timeTo, String locationId, String customerName) {
        ReservationByWaiter reservationByWaiterBody = ReservationByWaiter.builder()
                .clientType("VISITOR")
                .customerName(customerName)
                .date(LocalDate.now().plus(Period.ofDays(1)).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .guestsNumber(guestsNumber)
                .locationId(locationId)
                .tableNumber(tableNumber)
                .timeFrom(timeFrom)
                .timeTo(timeTo)
                .build();
        SignUp signUp = context.getSignedUpUser();
        response = ApiUtil.postRequest(RequestSpecificationBuilder.setRequestSpecificationWithAuth(signUp),
                ApiEndPoints.getEndPoint("TABLE_BOOKING_BY_WAITER"),
                reservationByWaiterBody);
        context.setResponse(response);

    }

    @And("the response body should have message {string}")
    public void theResponseBodyShouldHaveMessage(String expectedMessage) {
        Assert.assertEquals(response.jsonPath().getString("message"), expectedMessage);
    }

    @When("the waiter sends a POST request to the end point for booking a table {string} for {string} people from {string} to {string} at location {string} but for a past-date")
    public void theWaiterSendsAPOSTRequestToTheEndPointForBookingATableForPeopleFromToAtLocationButForAPastDate(String tableNumber, String guestsNumber, String timeFrom, String timeTo, String locationId) {
        ReservationByWaiter reservationByWaiterBody = ReservationByWaiter.builder()
                .clientType("VISITOR")
                .customerName("David Jack")
                .date(LocalDate.now().minus(Period.ofDays(2)).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .guestsNumber(guestsNumber)
                .locationId(locationId)
                .tableNumber(tableNumber)
                .timeFrom(timeFrom)
                .timeTo(timeTo)
                .build();
        SignUp signUp = context.getSignedUpUser();
        response = ApiUtil.postRequest(RequestSpecificationBuilder.setRequestSpecificationWithAuth(signUp),
                ApiEndPoints.getEndPoint("TABLE_BOOKING_BY_WAITER"),
                reservationByWaiterBody);
        context.setResponse(response);
    }

    @Then("validate if the slot for {string} from {string} to {string} is present or not")
    public void validateIfTheSlotForForToIsPresentOrNot(String tableNumber, String timeFrom, String timeTo) {
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

    @And("then waiter send a GET request to the bookings table end point for that {string} and for number of guests {string}")
    public void thenWaiterSendAGETRequestToTheBookingsTableEndPointForThatAndForNumberOfGuests(String locationId, String guestNumber) {
        queryParams.put("locationId", locationId);
        queryParams.put("date", LocalDate.now().plus(Period.ofDays(1)).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        queryParams.put("time", "12:00");
        queryParams.put("guests", guestNumber);
        response = ApiUtil.getRequest(RequestSpecificationBuilder.setRequestSpecificationWithoutAuth(),
                ApiEndPoints.getEndPoint("AVAILABLE_TABLES"),
                Collections.emptyMap(),
                queryParams);
    }
}

