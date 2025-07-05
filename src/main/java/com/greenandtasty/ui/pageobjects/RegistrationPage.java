package com.greenandtasty.ui.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class RegistrationPage extends BasePage{

    @FindBy(xpath = "h2[text()='Create an Account']")
    WebElement pageHeading;

    @FindBy(id = "firstName")
    WebElement firstNameInput;

    @FindBy(id = "lastName")
    WebElement lastNameInput;

    @FindBy(id = "email")
    WebElement emailInput;

    @FindBy(id = "password")
    WebElement passwordInput;

    @FindBy(id = "confirmPassword")
    WebElement confirmPasswordInput;

    @FindBy(xpath = "//button[text()=\"Create an Account\"]")
    WebElement submitButton;

    @FindBy(xpath = "//a[@href='/login']")
    WebElement loginLink;

    By invalidFirstNameErrorMessage=By.xpath("//input[@id='firstName']/following-sibling::p[2]");

    By invalidLastNameErrorMessage=By.xpath("//input[@id='lastName']/following-sibling::p[2]");

    By invalidEmailErrorMessage=By.xpath("//input[@type='email']/following-sibling::p[2]");

    By passowrdMatchValidationTest=By.xpath("//label[@for='confirmPassword']/following-sibling::p");



    public RegistrationPage(WebDriver driver) {
        super(driver);
    }

    @Override
    protected boolean isPageLoaded() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("h2[text()='Create an Account']")));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    public RegistrationPage enterFirstName(String firstName){
        sendKey(firstNameInput, firstName);
        return this;
    }

    public RegistrationPage enterLastName(String lastName){
        sendKey(lastNameInput, lastName);
        return this;
    }

    public RegistrationPage enterEmail(String email){
        sendKey(emailInput, email);
        return this;
    }

    public RegistrationPage enterPassword(String password){
        sendKey(passwordInput, password);
        return this;
    }

    public RegistrationPage enterConfirmPassword(String password){
        sendKey(confirmPasswordInput, password);
        return this;
    }

    public LoginPage clickCreateAccount(){
        clickOnAWebElement(submitButton);
//        wait.until(ExpectedConditions.urlContains("/login"));
        return new LoginPage(driver);
    }

    public LoginPage clickLogInLink(){
        clickOnAWebElement(loginLink);
        return new LoginPage(driver);
    }

    public String locateErrorMessageForInvalidFirstName(){
      return driver.findElement(invalidFirstNameErrorMessage).getText();
    }

    public String locateErrorMessageForInvalidLastName(){
        return driver.findElement(invalidLastNameErrorMessage).getText();
    }

    public String locateErrorMessageForInvalidEmail(){
        return driver.findElement(invalidEmailErrorMessage).getText();
    }

    public String locateErrorMismatchOfPassword(){
        return driver.findElement(passowrdMatchValidationTest).getDomAttribute("class");
    }

}
