package com.greenandtasty.stepdefinitions.common;

import com.greenandtasty.hooks.api.ApiContext;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.testng.Assert;

public class ApiCommonStepDefs {
    private ApiContext context;

    public ApiCommonStepDefs(){
        this.context = ApiContext.getInstance();
    }

    @Then("the response status code should be {int}")
    public void theResponseStatusCodeShouldBeExpectedStatusCode(int expectedStatusCode) {
        context.getResponse().then().statusCode(expectedStatusCode);
    }

    @Then("the response should have message {string}")
    public void theResponseShouldHaveMessage(String expectedMessage) {
        String actualMessage=context.getResponse().jsonPath().getString("message");
        Assert.assertTrue(actualMessage.contains(expectedMessage));
    }
}
