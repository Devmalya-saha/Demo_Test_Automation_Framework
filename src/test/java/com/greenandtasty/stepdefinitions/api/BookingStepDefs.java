package com.greenandtasty.stepdefinitions.api;

import com.greenandtasty.api.models.SignUp;
import com.greenandtasty.api.utils.ApiEndPoints;
import com.greenandtasty.api.utils.ApiUtil;
import com.greenandtasty.api.utils.RequestSpecificationBuilder;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.testng.Assert;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static com.greenandtasty.constant.Location.*;
import static java.util.Map.entry;


public class BookingStepDefs {
    Response response;
    Map<String, String> queryParams = new HashMap<>();
    static Integer maxCapacity;


    @When("a GET request is sent to the end point with location {string}, time {string}, guest number {string} and date query params")
    public void sendGetRequestWithDateQueryParam(String locationId, String time, String guestNumber) {
        queryParams.put("locationId", locationId);
        queryParams.put("date", LocalDate.now().plus(Period.ofDays(1)).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        queryParams.put("time", time);
        queryParams.put("guests", guestNumber);
        response = ApiUtil.getRequest(RequestSpecificationBuilder.setRequestSpecificationWithoutAuth(),
                ApiEndPoints.getEndPoint("AVAILABLE_TABLES"),
                Collections.emptyMap(),
                queryParams);
    }

    @When("a GET request is sent to the end point with location {string}, time {string}, guest number exceeding maximum capacity and date query params")
    public void aGETRequestIsSentToTheEndPointWithLocationTimeGuestNumberExceedingMaximumCapacityAndDateQueryParams(String locationId, String time) {
        queryParams.put("locationId", locationId);
        queryParams.put("date", LocalDate.now().plus(Period.ofDays(1)).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        queryParams.put("time", time);
        queryParams.put("guests", String.valueOf(maxCapacity + 1));
        response = ApiUtil.getRequest(RequestSpecificationBuilder.setRequestSpecificationWithoutAuth(),
                ApiEndPoints.getEndPoint("AVAILABLE_TABLES"),
                Collections.emptyMap(),
                queryParams);
    }

    @When("a GET request is sent to the end point with location {string}, time {string}, guest number {string} and a past date")
    public void aGETRequestIsSentToTheEndPointWithLocationTimeGuestNumberAndAPastDate(String locationId, String time, String guestNumber) {
        queryParams.put("locationId", locationId);
        queryParams.put("date", LocalDate.now().minus(Period.ofDays(2)).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        queryParams.put("time", time);
        queryParams.put("guests", guestNumber);
        response = ApiUtil.getRequest(RequestSpecificationBuilder.setRequestSpecificationWithoutAuth(),
                ApiEndPoints.getEndPoint("AVAILABLE_TABLES"),
                Collections.emptyMap(),
                queryParams);
    }


    @Then("store the maximum capacity from the response")
    public void storeMaximumCapacity() {
        maxCapacity = response.jsonPath()
                .getList("capacity")
                .stream()
                .map(Object::toString)
                .mapToInt(Integer::parseInt)
                .max()
                .orElse(0);
    }


    @Then("response should have a status-code {int}")
    public void validateStatusCode(int statusCode) {
        Assert.assertEquals(response.statusCode(), statusCode);
    }

    @And("response body should filter tables according to query guest number {string}")
    public void validateResponseBodyWhenQueriedByDates(String guestNumber) {
        int guests = (Integer.parseInt(guestNumber));
        Assert.assertTrue(response.body().jsonPath().getList("capacity")
                .stream()
                .map(Object::toString)
                .map(Integer::parseInt)
                .allMatch(e -> e >= guests));

    }


    @Then("contains a message {string}")
    public void validateResponseMessage(String message) {
        String actualMessage = response.jsonPath().getString("message");
        Assert.assertEquals(actualMessage, message);
    }


}