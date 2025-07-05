package com.greenandtasty.stepdefinitions.ui;

import com.greenandtasty.api.models.DeleteUserModel;
import com.greenandtasty.api.models.SignUp;
import com.greenandtasty.api.utils.SignUpObject;
import com.greenandtasty.hooks.ui.UITestContext;
import com.greenandtasty.ui.pageobjects.LoginPage;
import com.greenandtasty.ui.pageobjects.MainPage;
import com.greenandtasty.ui.pageobjects.RegistrationPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;


public class LoginPageSteps {
    private final UITestContext testContext = UITestContext.getInstance();


    @Given("the login page is loaded and the user has already registered")
    public void signInPageIsLoaded() {
        MainPage mainPage = new MainPage(testContext.getDriver());
        SignUp signUp = SignUpObject.signUpObject();
        testContext.setSignUp(signUp);
        RegistrationPage registrationPage = mainPage.clickOnSignIn().clickSignUpLink()
                .enterFirstName(signUp.getFirstName())
                .enterLastName(signUp.getLastName())
                .enterEmail(signUp.getEmail())
                .enterPassword(signUp.getPassword())
                .enterConfirmPassword(signUp.getPassword());
        LoginPage loginPage = registrationPage.clickCreateAccount();
        testContext.setLoginPage(loginPage);
    }


    @When("the user logs in with correct email and password")
    public void theUserLogsInWithCorrectEmailAndPassword() {
        SignUp signUp = testContext.getSignUp();
        LoginPage loginPage = testContext.getLoginPage();
        MainPage mainPage = loginPage.enterEmail(signUp.getEmail())
                .enterPassword(signUp.getPassword())
                .clickSubmit();
        testContext.setMainPage(mainPage);
    }

    @Then("the user name will be visible on clicking the profile icon")
    public void theUserNameWillBeVisibleOnClickingTheProfileIcon() {
        SignUp signUp = testContext.getSignUp();
        MainPage mainPage = testContext.getMainPage();
        String text = mainPage.getProfileNameAndRole();
        Assert.assertTrue(text.contains(signUp.getFirstName()));
    }

    @When("the user tries to log in but gives incorrect email")
    public void theUserTriesToLogInButGivesIncorrectEmail() {
        SignUp signUp = testContext.getSignUp();
        LoginPage loginPage = testContext.getLoginPage();
        loginPage.enterEmail(signUp.getEmail() + "com")
                .enterPassword(signUp.getPassword())
                .clickSubmit();
        testContext.setLoginPage(loginPage);

    }

    @Then("the user will remain on the login page even after clicking the submit button")
    public void theUserWillRemainOnTheLoginPageEvenAfterClickingTheSubmitButton() {
        LoginPage loginPage = testContext.getLoginPage();
        Assert.assertTrue(loginPage.isInLoginPage());
    }

    @When("the user tries to log in but gives incorrect password")
    public void theUserTriesToLogInButGivesIncorrectPassword() {
        SignUp signUp = testContext.getSignUp();
        LoginPage loginPage = testContext.getLoginPage();
        loginPage.enterEmail(signUp.getEmail())
                .enterPassword(signUp.getPassword() + "pass")
                .clickSubmit();
        testContext.setLoginPage(loginPage);
    }

    @When("the user tries to log in but forgets to give email")
    public void theUserTriesToLogInButForgetsToGiveEmail() {
        SignUp signUp = testContext.getSignUp();
        LoginPage loginPage = testContext.getLoginPage();
        loginPage.enterPassword(signUp.getPassword())
                .clickSubmit();
        testContext.setLoginPage(loginPage);
    }

    @Then("the proper error message showing missing email should be visible")
    public void theProperErrorMessageShowingMissingEmailShouldBeVisible() {
        LoginPage loginPage = testContext.getLoginPage();
        Assert.assertEquals(loginPage.getMissingEmailMessage(), "Email address is required.");
    }

    @When("the user tries to log in but forgets to give password")
    public void theUserTriesToLogInButForgetsToGivePassword() {
        SignUp signUp = testContext.getSignUp();
        LoginPage loginPage = testContext.getLoginPage();
        loginPage.enterEmail(signUp.getEmail())
                .clickSubmit();
        testContext.setLoginPage(loginPage);
    }


    @Then("the proper error message showing missing password should be visible")
    public void theProperErrorMessageShowingMissingPasswordShouldBeVisible() {
        LoginPage loginPage = testContext.getLoginPage();
        Assert.assertEquals(loginPage.getMissingPasswordMessage(), "Password is required");
    }
}
