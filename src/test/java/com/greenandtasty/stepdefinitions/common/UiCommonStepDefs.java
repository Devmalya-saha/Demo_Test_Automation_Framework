package com.greenandtasty.stepdefinitions.common;

import com.greenandtasty.config.ConfigLoader;
import com.greenandtasty.hooks.ui.UITestContext;
import com.greenandtasty.ui.pageobjects.LoginPage;
import com.greenandtasty.ui.pageobjects.MainPage;
import io.cucumber.java.en.Given;

import static com.greenandtasty.constant.Location.USER_EMAIL;
import static com.greenandtasty.constant.Location.USER_PASSWORD;

public class UiCommonStepDefs {
    UITestContext testContext = UITestContext.getInstance();

    @Given("user is logged in as customer")
    public void loginUser(){
        testContext.getDriver().get(ConfigLoader.getEnvironmentSpecificProperty("qa", "frontend") + "login");
        testContext.setMainPage(new MainPage(testContext.getDriver()));
        testContext.setLoginPage(new LoginPage(testContext.getDriver()));
        testContext.getLoginPage().enterEmail(USER_EMAIL).enterPassword(USER_PASSWORD).clickSubmit();
        testContext.getMainPage().isPageLoaded();
    }

    @Given("a user is not logged in")
    public void userIsNotLoggedIn() {
        testContext.getDriver().get(ConfigLoader.getEnvironmentSpecificProperty("qa", "frontend"));
        testContext.setMainPage(new MainPage(testContext.getDriver()));
        testContext.getMainPage().isPageLoaded();
        testContext.getMainPage().isUserSignedIn();
    }
}
