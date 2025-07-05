package com.greenandtasty.stepdefinitions.api;

import com.greenandtasty.api.models.ReservationByCustomer;
import com.greenandtasty.api.models.SignUp;
import com.greenandtasty.api.utils.ApiEndPoints;
import com.greenandtasty.api.utils.ApiUtil;
import com.greenandtasty.api.utils.RequestSpecificationBuilder;
import com.greenandtasty.api.utils.SignUpObject;
import com.greenandtasty.hooks.api.ApiContext;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReservartionFetchingSteps {
    static SignUp signUp;
    Response response;
    static String reservationId;
    Map<String ,String> pathParams=new HashMap<>();
    ApiContext context;

    public ReservartionFetchingSteps(){
        this.context = ApiContext.getInstance();
    }

    @Given("the user reserves a table {string} for {string} people from {string} to {string} at location {string}")
    public void theUserReservesATableForPeopleFromToAtLocation(String tableNumber, String guestNumber, String timeFrom, String timeTo, String locationId) {
        signUp=SignUpObject.signUpObject();
        ApiUtil.postRequest(RequestSpecificationBuilder.setRequestSpecificationWithoutAuth(),
                ApiEndPoints.getEndPoint("SIGN_UP"),
                signUp);
        ReservationByCustomer reservationByCustomerBody=ReservationByCustomer.builder()
                .locationId(locationId)
                .tableNumber(tableNumber)
                .date(LocalDate.now().plus(Period.ofDays(1)).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .guestsNumber(guestNumber)
                .timeFrom(timeFrom)
                .timeTo(timeTo)
                .build();
        response=ApiUtil.postRequest(RequestSpecificationBuilder.setRequestSpecificationWithAuth(signUp),
                ApiEndPoints.getEndPoint("TABLE_BOOKING_BY_CLIENT"),
                reservationByCustomerBody);
        System.out.println("Reservation: "+response.asPrettyString());
        reservationId=response.jsonPath().getString("id");

        context.setResponse(response);
        context.setSignedUpUser(signUp);
        context.setReservationId(response.jsonPath().getString("id"));
        System.out.println(reservationId);
    }


    @When("the user logs in and sends a GET request to the reservation end point he will be able to fetch all the response")
    public void theUserLogsInAndSendsAGETRequestToTheReservationEndPointHeWillBeAbleToFetchAllTheResponse() {
       response= ApiUtil.getRequest(RequestSpecificationBuilder.setRequestSpecificationWithAuth(signUp),
                ApiEndPoints.getEndPoint("RESERVATIONS"),
                Collections.emptyMap(),
                Collections.emptyMap());

    }

    @Then("the response should have status-code {int}")
    public void theResponseShouldHaveStatusCode(int expectedStatusCode) {
        Assert.assertEquals(response.getStatusCode(),expectedStatusCode);
    }

    @And("the response body should contain the reservation that was made prior")
    public void theResponseBodyShouldHaveNoNullObjects() {
        String idList=response.jsonPath().getString("id");
        Assert.assertEquals(reservationId, idList);
    }

    @When("the user sends a DELETE request along with the id of the reservation the reservation status becomes null")
    public void theUserSendsADELETERequestAlongWithTheIdOfTheReservationTheReservationStatusBecomesNull() {
        pathParams.put("id",reservationId);
        System.out.println(reservationId);
      response= ApiUtil.deleteRequestWithPathParam(RequestSpecificationBuilder.setRequestSpecificationWithAuth(signUp),
                ApiEndPoints.getEndPoint("RESERVATION_CANCELLATIONS"),
                pathParams);
      response.then().log().body();

    }

    @Then("send a GET request to the reservation end point")
    public void sendAGETRequestToTheReservationEndPoint() {
        response= ApiUtil.getRequest(RequestSpecificationBuilder.setRequestSpecificationWithAuth(signUp),
                ApiEndPoints.getEndPoint("RESERVATIONS"),
                Collections.emptyMap(),
                Collections.emptyMap());
    }

    @And("see if for that particular reservation the status is changed to cancelled or not")
    public void seeIfForThatParticularReservationTheStatusIsChangedToCancelledOrNot() {
        System.out.println(reservationId);
        List<Map<String,Object>> reservations=response.jsonPath().getList("");
        for(Map<String ,Object>reservation:reservations){
            String id=(String) reservation.get("id");
            if(id.equals(reservationId)){
                String status=(String) reservation.get("status");
                Assert.assertEquals(status,"Cancelled");
            }
        }
    }


}
