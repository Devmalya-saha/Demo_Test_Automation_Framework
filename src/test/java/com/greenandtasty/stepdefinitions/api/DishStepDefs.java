package com.greenandtasty.stepdefinitions.api;

import com.greenandtasty.api.utils.ApiEndPoints;
import com.greenandtasty.api.utils.ApiUtil;
import com.greenandtasty.api.utils.RequestSpecificationBuilder;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.eo.Do;
import io.cucumber.java.sl.In;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DishStepDefs {
    Response response;
    List<Map<String, Object>> ObjectList;
    Map<String, String> queryParams = new HashMap<>();
    Map<String, String> pathParams = new HashMap<>();
    List<Double> priceList;
    List<String> dishesIDList;
    Set<String> dishesID = new HashSet<>();
    List<Integer> statusCodeList = new ArrayList<>();


    @When("I send a GET request to the end point")
    public void iSendAGetRequestToTheEndPoint() {
        response = ApiUtil.getRequest(RequestSpecificationBuilder.setRequestSpecificationWithoutAuth(),
                ApiEndPoints.getEndPoint("POPULAR_DISHES"),
                null, null);
    }

    @Then("the response from the system status code should be {int}")
    public void theResponseStatusCodeShouldBe(int expectedStatusCode) {
        int actualStatusCode = response.getStatusCode();
        Assert.assertEquals(actualStatusCode, expectedStatusCode);
    }

    @And("the response body should not be empty")
    public void theResponseBodyShouldNotBeEmpty() {
        ObjectList = response.jsonPath().getList("");
        Assert.assertFalse(ObjectList.isEmpty());
    }


    @And("all the objects should contain the required field")
    public void allTheObjectsShouldContainTheRequiredField() {
        boolean allValid = checkNonEmptyAndNonNullObjectVariable(ObjectList);
        Assert.assertTrue(allValid);
    }


    @When("the user send a GET request to the end point with dishType {string} and sort by popularity in {string}")
    public void theUserSendAGETRequestToTheEndPointWithDishTypeDishTypeAndSortByPopularityInOrder(String dishType, String order) {
        queryParams.put("dishType", dishType);
        queryParams.put("sort", "popularity," + order);

        response = ApiUtil.getRequest(RequestSpecificationBuilder.setRequestSpecificationWithoutAuth(),
                ApiEndPoints.getEndPoint("DISHES"),
                Collections.emptyMap(),
                queryParams);
        System.out.println(response.asString());


    }


    @And("the response body should have all the parameters")
    public void theResponseBodyShouldHaveAllTheParameters() {
        if (response.getStatusCode() == 200) {
            ObjectList = response.jsonPath().getList("content");
            boolean allValid = checkNonEmptyAndNonNullObjectVariable(ObjectList);
            Assert.assertTrue(allValid);
        }


    }


    @Then("the status code should be {int} or {int}")
    public void theStatusCodeShouldBeOr(int expectedStatusCode, int optionalExpectedStatusCode) {
        int actualStatusCode = response.getStatusCode();
        System.out.println("actualStatusCode = " + actualStatusCode);
        Assert.assertTrue(actualStatusCode == expectedStatusCode || actualStatusCode == optionalExpectedStatusCode);
    }


    @When("the user send a GET request to the end point with dishType {string} and sort by price in {string}")
    public void theUserSendAGETRequestToTheEndPointWithDishTypeDishTypeAndSortByPriceInOrder(String dishType, String order) {
        queryParams.put("dishType", dishType);
        queryParams.put("sort", "price," + order);

        response = ApiUtil.getRequest(RequestSpecificationBuilder.setRequestSpecificationWithoutAuth(),
                ApiEndPoints.getEndPoint("DISHES"),
                Collections.emptyMap(),
                queryParams);

    }


    @And("check if the dishes are shown in correct order {string} of price")
    public void checkIfTheDishesAreShownInCorrectOrderOrderOfPrice(String order) {
        if (response.getStatusCode() == 200) {
            priceList = response.jsonPath().getList("content.price", Double.class);
            boolean isSorted = checkOrder(priceList, order);
            Assert.assertTrue(isSorted);
        }
    }


    @When("the user sends a GET request to the end point with a id of a dish that are there")
    public void theUserSendsAGETRequestToTheEndPointWithAParitcularIdOfADish() {
        getListOfIds();
        for (String id : dishesID) {
            pathParams.put("id", id);
            response = ApiUtil.getRequest(RequestSpecificationBuilder.setRequestSpecificationWithoutAuth(),
                    ApiEndPoints.getEndPoint("DISH_INFO"),
                    pathParams, Collections.emptyMap());
            statusCodeList.add(response.getStatusCode());
            String actualID = response.jsonPath().getString("id");
            Assert.assertEquals(actualID, id);

        }

    }

    @Then("also validate the response code is {int} for all the get request")
    public void alsoValidateTheResponseCodeIsForAllTheGetRequest(int expectedStatusCode) {
        boolean statusCodeMatch = statusCodeList.stream().allMatch(responseStatusCode -> responseStatusCode == expectedStatusCode);
        Assert.assertTrue(statusCodeMatch);
    }


    public boolean checkNonEmptyAndNonNullObjectVariable(List<Map<String, Object>> ListOfObjects) {
        return ListOfObjects.stream()
                .allMatch(currentObject ->
                        currentObject.keySet().stream()
                                .allMatch(key -> {
                                    Object value = currentObject.get(key);
                                    return value != null &&
                                            (!(value instanceof String) || !((String) value).isEmpty()); // Ensure non-empty strings
                                })
                );
    }

    public boolean checkOrder(List<Double> list, String order) {
        if (order.equalsIgnoreCase("asc")) {
            return IntStream.range(0, priceList.size() - 1).allMatch(i -> priceList.get(i) <= priceList.get(i + 1));
        } else {
            return IntStream.range(0, priceList.size() - 1).allMatch(i -> priceList.get(i) >= priceList.get(i + 1));
        }
    }
    public void getListOfIds(){
        queryParams.put("dishType", "Appetizers");
        queryParams.put("sort", "popularity," + "asc");
        response = ApiUtil.getRequest(RequestSpecificationBuilder.setRequestSpecificationWithoutAuth(),
                ApiEndPoints.getEndPoint("DISHES"),
                Collections.emptyMap(),
                queryParams);
        dishesIDList=response.jsonPath().getList("content.id");
        dishesID=dishesIDList.stream().collect(Collectors.toSet());
    }
}
