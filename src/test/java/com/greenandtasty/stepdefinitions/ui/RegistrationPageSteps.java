package com.greenandtasty.stepdefinitions.ui;


import com.greenandtasty.api.models.SignUp;
import com.greenandtasty.api.utils.SignUpObject;
import com.greenandtasty.config.ConfigLoader;

import com.greenandtasty.hooks.ui.UIHooks;
import com.greenandtasty.hooks.ui.UITestContext;
import com.greenandtasty.ui.pageobjects.LoginPage;
import com.greenandtasty.ui.pageobjects.MainPage;
import com.greenandtasty.ui.pageobjects.RegistrationPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import org.testng.Assert;


public class RegistrationPageSteps {
    private final UITestContext testContext = UITestContext.getInstance();


    @Given("the user is on the sign up page")
    public void signUpPageIsLoaded() {
        MainPage mainPage = new MainPage(testContext.getDriver());
        RegistrationPage registrationPage =  mainPage.clickOnSignIn().clickSignUpLink();
        testContext.setRegistrationPage(registrationPage);

    }


    @When("the valid inputs for each required fields are given")
    public void theValidInputsForEachRequiredFieldsAreGiven() throws InterruptedException {
        RegistrationPage page = testContext.getRegistrationPage();
        SignUp signUp = SignUpObject.signUpObject();
        testContext.setSignUp(signUp);
        LoginPage loginPage = page.enterFirstName(signUp.getFirstName())
                .enterLastName(signUp.getLastName())
                .enterEmail(signUp.getEmail())
                .enterPassword(signUp.getPassword())
                .enterConfirmPassword(signUp.getPassword())
                .clickCreateAccount();
        testContext.setRegistrationPage(page);
        testContext.setLoginPage(loginPage);


    }


    @Then("create an account button is clicked")
    public void createAnAccountButtonIsClicked()  {
        RegistrationPage page = testContext.getRegistrationPage();
        LoginPage loginPage = page.clickCreateAccount();
        testContext.setLoginPage(loginPage);
    }

    @And("user should be able to login with the same user name and password")
    public void userShouldBeAbleToLoginWithTheSameUserNameAndPassword() {
       SignUp signUp = testContext.getSignUp();
        testContext.getLoginPage().enterEmail(signUp.getEmail())
                .enterPassword(signUp.getPassword())
                .clickSubmit();

    }


    @When("the invalid input is given in the first name {string} field")
    public void theInvalidInputIsGivenInTheFirstNameField(String firstName) {
        RegistrationPage page = testContext.getRegistrationPage();
        SignUp signUp = SignUpObject.signUpObject();
        signUp.setFirstName(firstName);
        page.enterFirstName(signUp.getFirstName())
                .enterLastName(signUp.getLastName())
                .enterEmail(signUp.getEmail())
                .enterPassword(signUp.getPassword())
                .enterConfirmPassword(signUp.getPassword());
        testContext.setSignUp(signUp);
    }

    @And("the invalid first name  message should be displayed to the user properly")
    public void theInvalidFirstNameMessageShouldBeDisplayedToTheUserProperly() {
        RegistrationPage page = testContext.getRegistrationPage();
        String actualMessage = page.locateErrorMessageForInvalidFirstName();
        Assert.assertEquals(actualMessage, "First name must be up to 50 characters. Only Latin letters, hyphens, and apostrophes are allowed.");
    }

    @When("the invalid input is given in the last name {string} field")
    public void theInvalidInputIsGivenInTheLastNameNameField(String lastName) {
        RegistrationPage page = testContext.getRegistrationPage();
        SignUp signUp = SignUpObject.signUpObject();
        signUp.setLastName(lastName);
        page.enterFirstName(signUp.getFirstName())
                .enterLastName(signUp.getLastName())
                .enterEmail(signUp.getEmail())
                .enterPassword(signUp.getPassword())
                .enterConfirmPassword(signUp.getPassword());
        testContext.setSignUp(signUp);
    }

    @And("the invalid last name  message should be displayed to the user properly")
    public void theInvalidLastNameMessageShouldBeDisplayedToTheUserProperly() {
        RegistrationPage page = testContext.getRegistrationPage();
        String actualMessage = page.locateErrorMessageForInvalidLastName();
        Assert.assertEquals(actualMessage, "Last name must be up to 50 characters. Only Latin letters, hyphens, and apostrophes are allowed.");

    }


    @When("the invalid invalid input for the e-mail  is given in the e-mail field {string}")
    public void theInvalidInvalidInputForTheEMailIsGivenInTheEMailFieldEMail(String email) {
        RegistrationPage page = testContext.getRegistrationPage();
        SignUp signUp = SignUpObject.signUpObject();
        signUp.setEmail(email);
        page.enterFirstName(signUp.getFirstName())
                .enterLastName(signUp.getLastName())
                .enterEmail(signUp.getEmail())
                .enterPassword(signUp.getPassword())
                .enterConfirmPassword(signUp.getPassword());
        testContext.setSignUp(signUp);
    }

    @And("the invalid e-mail message should be displayed to the user properly")
    public void theInvalidEMailMessageShouldBeDisplayedToTheUserProperly() {
        RegistrationPage page = testContext.getRegistrationPage();
        String actualMessage = page.locateErrorMessageForInvalidEmail();
        Assert.assertEquals(actualMessage, "Invalid email address. The email must be valid, domain must not contain digits or consecutive dots, cannot start or end with a hyphen, and must end with .com.");

    }

    @Given("a user has already registered")
    public void aUserHasAlreadyRegistered() {
        SignUp signUp = SignUpObject.signUpObject();
        RegistrationPage page = testContext.getRegistrationPage()
                .enterFirstName(signUp.getFirstName())
                .enterLastName(signUp.getLastName())
                .enterEmail(signUp.getEmail())
                .enterPassword(signUp.getPassword())
                .enterConfirmPassword(signUp.getPassword());
        testContext.setSignUp(signUp);
    }

    @When("the user tries to register with the same e-mail account")
    public void theUserTriesToRegisterWithTheSameEMailAccount() {
        SignUp signUp = testContext.getSignUp();
        RegistrationPage page = testContext.getRegistrationPage()
                .enterFirstName(signUp.getFirstName())
                .enterLastName(signUp.getLastName())
                .enterEmail(signUp.getEmail())
                .enterPassword(signUp.getPassword())
                .enterConfirmPassword(signUp.getPassword());
        testContext.setSignUp(signUp);


    }

    @Then("the user should be displayed a proper error message showing e-mail already exists")
    public void theUserShouldBeDisplayedAProperErrorMessageShowingEMailAlreadyExists() {
        RegistrationPage page = testContext.getRegistrationPage();
        String actualMessage = page.locateErrorMessageForInvalidEmail();
        Assert.assertEquals(actualMessage, "A user with this email address already exists.");
        SignUp signUppage = testContext.getSignUp();

    }


    @Then("the user returns to the registration page again")
    public void theUserReturnsToTheRegistrationPageAgain() {
        LoginPage loginPage = new LoginPage(testContext.getDriver());
        RegistrationPage page = loginPage.clickSignUpLink();
        testContext.setRegistrationPage(page);
    }

    @When("the password and the confirm password doesn't match")
    public void thePasswordAndTheConfirmPasswordDoesnTMatch() {
        SignUp signUp = SignUpObject.signUpObject();
        RegistrationPage page = testContext.getRegistrationPage()
                .enterFirstName(signUp.getFirstName())
                .enterLastName(signUp.getLastName())
                .enterEmail(signUp.getEmail())
                .enterPassword(signUp.getPassword())
                .enterConfirmPassword(signUp.getPassword() + "D");
        testContext.setRegistrationPage(page);

    }

    @Then("the error message should be visible to the user")
    public void theErrorMessageShouldBeVisibleToTheUser() {
        RegistrationPage page = testContext.getRegistrationPage();
        String actualAttribute = page.locateErrorMismatchOfPassword();
        Assert.assertEquals(actualAttribute, "text-xs mt-2 text-red-500");
    }


    @When("the user is registering for first time if his {string} is already assigned as waiter by admin the user will be waiter or else he will be a customer")
    public void theUserIsRegisteringForFirstTimeIfHisIsAlreadyAssignedAsWaiterByAdminTheUserWillBeWaiterOrElseHeWillBeACustomer(String role) {
        if (role.equalsIgnoreCase("Customer")) {
            RegistrationPage page = testContext.getRegistrationPage();


            SignUp signUp = SignUpObject.signUpObject();
            testContext.setSignUp(signUp);

            page.enterFirstName(signUp.getFirstName())
                    .enterLastName(signUp.getLastName())
                    .enterEmail(signUp.getEmail())
                    .enterPassword(signUp.getPassword())
                    .enterConfirmPassword(signUp.getPassword());
            LoginPage loginPage = page.clickCreateAccount();
            testContext.setLoginPage(loginPage);

        } else {
            RegistrationPage registrationPage = testContext.getRegistrationPage();
            LoginPage page = registrationPage.clickLogInLink();
            testContext.setLoginPage(page);
        }

    }

    @And("the {string} should be properly visible in the user page")
    public void theShouldBeProperlyVisibleInTheUserPage(String role) {
        MainPage mainPage;
        LoginPage loginPage = testContext.getLoginPage();
        if (role.equalsIgnoreCase("Customer")) {

            SignUp signUp = testContext.getSignUp();
            mainPage = loginPage.enterEmail(signUp.getEmail())
                    .enterPassword(signUp.getPassword())
                    .clickSubmit();
        } else {
            mainPage = loginPage.enterEmail(ConfigLoader.getEnvironmentSpecificProperty("qa", "waiteremail"))
                    .enterPassword(ConfigLoader.getEnvironmentSpecificProperty("qa", "waiterpassword"))
                    .clickSubmit();
        }
        String userRole = mainPage.getProfileNameAndRole();
        Assert.assertTrue(userRole.contains(role));


    }
}
