package com.greenandtasty.stepdefinitions.api;

import com.greenandtasty.api.utils.ApiEndPoints;
import com.greenandtasty.api.utils.ApiUtil;
import com.greenandtasty.api.utils.RequestSpecificationBuilder;
import com.greenandtasty.api.utils.ReservationByCustomerObject;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.testng.Assert;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class LocationStepDefs {
    Response response;
    Map<String, String> id = new HashMap<>();
    List<Map<String, Object>> responseBodyList;
    List<Double> ratingList;
    List<String> dateList;
    Map<String, String> pathParams = new HashMap<>();
    Map<String, String> queryParams = new HashMap<>();


    @When("I send a GET request to the endpoint")
    public void iSendAGETRequestToTheEndpoint() {
        response = ApiUtil.getRequest(RequestSpecificationBuilder.setRequestSpecificationWithoutAuth(),
                ApiEndPoints.getEndPoint("LOCATIONS"),
                null, null);
    }


    @Then("response should have a status code {int}")
    public void responseShouldHaveAStatusCode(int expectedStatuCode) {
        int actualStatusCode = response.getStatusCode();
        Assert.assertEquals(actualStatusCode, expectedStatuCode);
    }

    @And("the response should return an non empty list of all the locations")
    public void theResponseShouldReturnAnNonEmptyListOfAllTheLocationsAndAllKeyShouldContainValues() {
        responseBodyList = response.jsonPath().getList("");
        ReservationByCustomerObject.locationIds = new HashSet<>(response.jsonPath().getList("id"));
        Assert.assertFalse(responseBodyList.isEmpty());
    }

    @And("the all the objects should be non null and have values")
    public void theAllTheObjectsShouldBeNonNullAndHaveValues() {
        boolean checkNonNullObjects = checkNonEmptyAndNonNullObjectVariable(responseBodyList);
        Assert.assertTrue(checkNonNullObjects);
    }

    @When("I fetch the speciality dishes for each {string}")
    public void iFetchTheSpecialityDishesForEachLocation(String locationID) {
        id.put("id", locationID);
        response = ApiUtil.getRequest(RequestSpecificationBuilder.setRequestSpecificationWithoutAuth(),
                ApiEndPoints.getEndPoint("SPECIAL_DISHES_OF_A_LOCATION"), id, Collections.emptyMap());
        iShouldSeeTheValidSpecialityDishesForEachLocation(response);
    }


    public void iShouldSeeTheValidSpecialityDishesForEachLocation(Response response) {
        response.then().statusCode(200);
        responseBodyList = response.jsonPath().getList("");
        Assert.assertFalse(responseBodyList.isEmpty());
        boolean checkNonNullObjects = checkNonEmptyAndNonNullObjectVariable(responseBodyList);
        Assert.assertTrue(checkNonNullObjects);
    }

    @When("I send a GET request to the end point for select-options")
    public void iSendAGETRequestToTheEndPointForSelectOptions() {
        response = ApiUtil.getRequest(RequestSpecificationBuilder.setRequestSpecificationWithoutAuth()
                , ApiEndPoints.getEndPoint("LOCATION_SPECIFIC_INFO"),
                null,
                null);

    }

    @Then("the response should be status code {int} and it should not give an empty list")
    public void theResponseShouldBeStatusCodeAndItShouldShowTheListOfAllTheLocationDetails(int expectedStatusCode) {
        int actualStatusCode = response.getStatusCode();
        Assert.assertEquals(actualStatusCode, expectedStatusCode);
        responseBodyList = response.jsonPath().getList("");
        Assert.assertNotNull(responseBodyList);
    }

    @And("all the objects key value pair should be non null")
    public void allTheObjectsKeyValuePairShouldBeNonNull() {
        boolean checkNonNullObject = checkNonEmptyAndNonNullObjectVariable(responseBodyList);
        Assert.assertTrue(checkNonNullObject);
    }


    @Then("the response status code should be {int} for each and they should have a non null object body")
    public void theResponseStatusCodeShouldBeForEachAndTheyShouldHaveANonNullObjectBody(int expectedStatusCode) {
        int actualStatusCode = response.getStatusCode();
        Assert.assertEquals(actualStatusCode, expectedStatusCode);
        Assert.assertNotNull(response.body());
    }

    @Then("the status code should be {int}")
    public void theReponseStatusCodeShouldBe(int expectedStatusCode) {
        int actualStatusCode = response.getStatusCode();
        Assert.assertEquals(actualStatusCode, expectedStatusCode);
    }

    @When("the user sends a GET request to the end point for {string} with {string} and sort by {string}")
    public void theUserSendsAGETRequestToTheEndPointForWithTypeAndSortBySort(String locationId, String type, String order) {
        pathParams.put("id", locationId);
        queryParams.put("type", type);
        queryParams.put("sort", "rating," + order);
        response = ApiUtil.getRequest(RequestSpecificationBuilder.setRequestSpecificationWithoutAuth(),
                ApiEndPoints.getEndPoint("LOCATION_SPECIFIC_FEEDBACKS"),
                pathParams,
                queryParams);
        responseBodyList = response.jsonPath().getList("content");


    }

    public boolean checkNonEmptyAndNonNullObjectVariable(List<Map<String, Object>> ListOfObjects) {
        return ListOfObjects.stream()
                .allMatch(currentObject ->
                        currentObject.keySet().stream()
                                .allMatch(key -> {
                                    Object value = currentObject.get(key);
                                    return value != null; // Ensure non-empty strings
                                })
                );
    }

    @And("the ratings should be in proper order {string}")
    public void theRatingsShouldBeInProperOrder(String order) {
        ratingList = response.jsonPath().getList("content.rate", Double.class);
        Assert.assertTrue(checkOrder(ratingList, order));


    }

    public boolean checkOrder(List<Double> list, String order) {
        if (order.equalsIgnoreCase("asc")) {
            return IntStream.range(0, list.size() - 1).allMatch(i -> list.get(i) <= list.get(i + 1));
        } else {
            return IntStream.range(0, list.size() - 1).allMatch(i -> list.get(i) >= list.get(i + 1));
        }
    }

    @When("the user sends a GET request to the end point for {string} with {string} and sort by date {string}")
    public void theUserSendsAGETRequestToTheEndPointForWithAndSortByDate(String locationId, String type, String order) {
        pathParams.put("id", locationId);
        queryParams.put("type", type);
        queryParams.put("sort", "date," + order);
        response = ApiUtil.getRequest(RequestSpecificationBuilder.setRequestSpecificationWithoutAuth(),
                ApiEndPoints.getEndPoint("LOCATION_SPECIFIC_FEEDBACKS"),
                pathParams,
                queryParams);
        responseBodyList = response.jsonPath().getList("content");
    }

   /* @And("the date should be in proper order {string}")
    public void theDateShouldBeInProperOrder(String order) {
        dateList = response.jsonPath().getList("content.date");
        System.out.println(dateList);
        Assert.assertTrue(isDateListSorted(dateList, order));

    }


    public boolean isDateListSorted(List<String> dateStrings, String order) {
        if (dateStrings == null || dateStrings.size() <= 1) {
            return true;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;

        try {
            List<LocalDateTime> dates = dateStrings.stream()
                    .map(date -> LocalDateTime.parse(date, formatter))
                    .collect(Collectors.toList());

            if ("asc".equalsIgnoreCase(order)) {
                return IntStream.range(0, dates.size() - 1)
                        .allMatch(i -> dates.get(i).compareTo(dates.get(i + 1)) <= 0);
            } else if ("desc".equalsIgnoreCase(order)) {
                return IntStream.range(0, dates.size() - 1)
                        .allMatch(i -> dates.get(i).compareTo(dates.get(i + 1)) >= 0);
            } else {
                return false;
            }

        } catch (Exception e) {
            return false;
        }
    }*/
}
