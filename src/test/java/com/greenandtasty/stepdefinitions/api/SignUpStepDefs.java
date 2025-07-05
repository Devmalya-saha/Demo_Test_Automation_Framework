package com.greenandtasty.stepdefinitions.api;

import com.greenandtasty.api.models.SignIn;
import com.greenandtasty.api.models.SignUp;
import com.greenandtasty.api.utils.*;
import com.greenandtasty.hooks.api.ApiContext;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.restassured.response.Response;

import static org.hamcrest.CoreMatchers.equalTo;

public class SignUpStepDefs {
    public static SignUp signUp;
    public SignUp newUserObject;
    public Response response;
    public SignIn signIn;
    private final ApiContext context;

    public SignUpStepDefs(){
        this.context = ApiContext.getInstance();
    }

    @Given("a valid SignUp request is prepared")
    public void iHaveAValidRequestBodyForCreatingANewUser() {
        signUp=SignUpObject.signUpObject();
        context.setSignedUpUser(signUp);
    }

    @Given("a request body is created with null {string}")
    public void createRequestWithNullFields(String field){
        newUserObject =SignUpObject.signUpObject();
        switch (field) {
            case "firstName":
                newUserObject.setFirstName(null);
                break;
            case "lastName":
                newUserObject.setLastName(null);
                break;
            case "email":
                newUserObject.setEmail(null);
                break;
            case "password":
                newUserObject.setPassword(null);
                break;
            default:
                System.out.println("Invalid field"); //Implement Logger here
        }
        signUp=newUserObject;
        context.setSignedUpUser(signUp);
    }

    @When("a POST request is sent to the sign up end point")
    public void iHaveToSendAPOSTrequestToTheSignUpEndPoint() {
        response = ApiUtil.postRequest(RequestSpecificationBuilder.setRequestSpecificationWithoutAuth(),ApiEndPoints.getEndPoint("SIGN_UP"),signUp);
        response.then().log().all();
        context.setResponse(response);
    }

    @When("a POST request is sent to the sign up end point twice")
    public void wendAPOSTrequestToTheSignUpEndPointTwice() {
        ApiUtil.postRequest(RequestSpecificationBuilder.setRequestSpecificationWithoutAuth(),ApiEndPoints.getEndPoint("SIGN_UP"),signUp);
        response = ApiUtil.postRequest(RequestSpecificationBuilder.setRequestSpecificationWithoutAuth(),ApiEndPoints.getEndPoint("SIGN_UP"),signUp);
        context.setResponse(response);
    }

    @Given("a request body is created for a new user with {string}")
    public void iHaveAValidRequestBodyForCreatingANewUserWithPassword(String password) {
        newUserObject=SignUpObject.signUpObject();
        newUserObject.setPassword(password);
        signUp=newUserObject;
        context.setSignedUpUser(signUp);
    }

    @Given("a request body is created for a new user with invalid {string} format")
    public void iHaveAValidRequestBodyForCreatingANewUserWithInvalidEmailFormatEmail(String email) {
        newUserObject=SignUpObject.signUpObject();
        newUserObject.setEmail(email);
        signUp=newUserObject;
        context.setSignedUpUser(signUp);
    }



    @Given("a request body is created with invalid {string} {string}")
    public void iHaveAValidRequestBodyForCreatingANewUserButWithInvalidFieldInvalidName(String field,String invalidNames) {
        newUserObject =SignUpObject.signUpObject();
        if(field.equalsIgnoreCase("firstName"))
        {
            newUserObject.setFirstName("NULL".equalsIgnoreCase(invalidNames)?null:invalidNames);
        }
        else {
            newUserObject.setLastName("NULL".equalsIgnoreCase(invalidNames)?null:invalidNames);
        }
        signUp=newUserObject;
        context.setSignedUpUser(signUp);
    }

    @Given("a request body is created with empty {string} {string}")
    public void iHaveAValidRequestBodyForCreatingANewUserButWithEmptyInvalidname(String field,String value){
        newUserObject =SignUpObject.signUpObject();
        switch (field) {
            case "firstName":
                newUserObject.setFirstName(value);
                break;
            case "lastName":
                newUserObject.setLastName(value);
                break;
            case "email":
                newUserObject.setEmail(value);
                break;
            case "password":
                newUserObject.setPassword(value);
                break;
            default:
                System.out.println("Invalid filed"); //Implement Logger here
        }
        signUp=newUserObject;
        context.setSignedUpUser(signUp);
    }

    @Given("I have a valid request body for creating a {string}")
    public void iHaveAValidRequestBodyForCreatingARole(String userRole) {
        if(userRole.equalsIgnoreCase("Customer")) {
            newUserObject = SignUpObject.signUpObject();
        }
        else {
            newUserObject=SignUpObject.signUpObject();
            newUserObject.setEmail("waiter1@gmail.com");
            newUserObject.setPassword("Waiter@123");
        }
        signUp=newUserObject;
    }

    @When("I have to register and then signin for that {string}")
    public void iHaveToRegisterAndThenSignin(String role) {
        if(role.equalsIgnoreCase("CUSTOMER")){
            iHaveToSendAPOSTrequestToTheSignUpEndPoint();
        }
        signIn = SignInObject.signInObject(signUp);
        response=ApiUtil.postRequest(RequestSpecificationBuilder.setRequestSpecificationWithAuth(signUp),ApiEndPoints.getEndPoint("SIGN_IN"),signIn);
        context.setResponse(response);
    }

    @And("the role should be {string}")
    public void theRoleShouldBeRole(String userRole) {
        response.then().body("role",equalTo(userRole)).log().body();
    }
}
