package com.greenandtasty.stepdefinitions.api;

import com.greenandtasty.api.models.SignIn;
import com.greenandtasty.api.models.SignUp;
import com.greenandtasty.api.utils.*;
import com.greenandtasty.hooks.api.ApiContext;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.testng.Assert;


public class SignInStepDefs {
    public static SignUp userForSignInTest = SignUpObject.signUpObject();
    public static Response response;
    private ApiContext context;

    public SignInStepDefs(){
        this.context = ApiContext.getInstance();
    }

    @Given("a user has signed up with valid credentials")
    public void aUserHasSignedUpWithValidCredentials() {
        SignUp user = userForSignInTest;
        ApiUtil.postRequest(RequestSpecificationBuilder.setRequestSpecificationWithoutAuth(),
                ApiEndPoints.getEndPoint("SIGN_UP"),
                user);
        context.setSignedUpUser(userForSignInTest);
    }

    @When("the user signs in with the valid credentials")
    public void theUserSignsInWithTheValidCredentials() {
        SignIn userbody = SignInObject.signInObject(userForSignInTest);
        response=ApiUtil.postRequest(RequestSpecificationBuilder.setRequestSpecificationWithoutAuth(),
                ApiEndPoints.getEndPoint("SIGN_IN"),
                userbody);
        context.setResponse(response);
    }

    @And("the response body should have role as customer or waiter")
    public void theResponseBodyShouldHaveRoleAsCustomerOrWaiter() {
        String userRole=response.jsonPath().get("role");
        Assert.assertTrue(userRole.equalsIgnoreCase("Customer")||userRole.equalsIgnoreCase("Waiter"));
    }

    @And("the response body should have username same as the signed up user")
    public void theResponseBodyShouldHaveUsernameSameAsTheSignedUpUser() {
        String userName=response.jsonPath().get("username");
        Assert.assertEquals(userName,userForSignInTest.getFirstName()+" "+userForSignInTest.getLastName());
    }

    @When("the user signs in with email {string} and password {string}")
    public void theUserSignsInWithUsernameUsernameAndPasswordPassword(String email , String password) {
        SignIn invalidUser=SignInObject.signInObject(userForSignInTest);
        if(!email.equalsIgnoreCase("valid") ){
            invalidUser.setEmail(email);
        }
        invalidUser.setPassword(password);
        response=ApiUtil.postRequest(RequestSpecificationBuilder.setRequestSpecificationWithoutAuth(),
                ApiEndPoints.getEndPoint("SIGN_IN"),
                invalidUser);
        context.setResponse(response);
    }

    @When("the user attempts sign-in without email field")
    public void createRequestBodyWithoutEmail() {
        SignIn invalidUser = SignIn.builder().email(null).password(userForSignInTest.getPassword()).build();
        response=ApiUtil.postRequest(RequestSpecificationBuilder.setRequestSpecificationWithoutAuth(),
                ApiEndPoints.getEndPoint("SIGN_IN"),
                invalidUser);
        System.out.println("Request: "+invalidUser);
        context.setResponse(response);
    }

    @When("the user attempts sign-in without password field")
    public void createRequestBodyWithoutPassword() {
        SignIn invalidUser = SignIn.builder().email(userForSignInTest.getEmail()).password(null).build();
        response=ApiUtil.postRequest(RequestSpecificationBuilder.setRequestSpecificationWithoutAuth(),
                ApiEndPoints.getEndPoint("SIGN_IN"),
                invalidUser);
        context.setResponse(response);
    }

    @Then("the system should response with the status code {int}")
    public void theSystemShouldRespondeWithTheStatusCodeStatusCode(int expectedStatusCode) {
        int statusCode=response.getStatusCode();
        Assert.assertEquals(statusCode,expectedStatusCode,"The expected status code was "+expectedStatusCode);
    }

    @And("the response body will display as {string}")
    public void theReponseBodyWillDisplayAsErrorMessage(String expectedMessage) {
        String message=response.jsonPath().getString("message");
        Assert.assertEquals(message,expectedMessage,"The expected message was "+expectedMessage);
    }
}
