package com.greenandtasty.stepdefinitions.api;

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

import java.util.Collections;

public class UserDef {
    static SignUp signUp;
    Response response;

    @Given("the user has already registered")
    public void theUserHasAlreadyRegistered() {
        signUp= SignUpObject.signUpObject();
        System.out.println(signUp.getEmail());
        System.out.println(ApiUtil.postRequest(RequestSpecificationBuilder.setRequestSpecificationWithoutAuth(),
                ApiEndPoints.getEndPoint("SIGN_UP"),
                signUp).asPrettyString());
    }
    @When("the user sends a GET request to the end point for getting user data")
    public void theUserSendsAGETRequestToTheEndPointForGettingUserData() {
        System.out.println(signUp.getEmail()+" "+signUp.getPassword());
        response=ApiUtil.getRequest(RequestSpecificationBuilder.setRequestSpecificationWithAuth(signUp),
                ApiEndPoints.getEndPoint("USER_PROFILE"),
                Collections.emptyMap(),
                Collections.emptyMap());
        System.out.println("User Data: "+response.asPrettyString());
    }


    @And("validate that the first name and last name are same as one sent during registering")
    public void validateThatTheFirstNameAndLastNameAreSameAsOneSentDuringRegistering() {
        Assert.assertEquals(response.jsonPath().getString("firstName"),signUp.getFirstName());
        Assert.assertEquals(response.jsonPath().getString("lastName"),signUp.getLastName());
    }

    @Then("the response-status code should be {int}")
    public void theResponseStatusCodeShouldBe(int expectedStatusCode) {
        Assert.assertEquals(response.getStatusCode(),expectedStatusCode);
        System.out.println(response.asPrettyString());
    }
}
