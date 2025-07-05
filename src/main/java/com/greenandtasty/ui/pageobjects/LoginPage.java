package com.greenandtasty.ui.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage extends BasePage{

    @FindBy(xpath = "//h2[.=\"Sign In To Your Account\"]/following-sibling::form//input[@id=\"email\"]")
    WebElement emailInput;

    @FindBy(xpath = "//h2[.=\"Sign In To Your Account\"]/following-sibling::form//input[@id=\"password\"]")
    WebElement passwordInput;

    @FindBy(css = "button[type='submit']")
    WebElement submitButton;

    @FindBy(xpath = "//a[@href='/signup']")
    WebElement signUpLink;

    @FindBy(xpath = "//h2[text()=\"Sign In To Your Account\"]")
    WebElement loginPageHeader;

    By missingEmailMessage=By.xpath("//input[@id=\"email\"]/following-sibling::span");

    By missingPasswordMessage=By.xpath("//input[@id=\"password\"]/following-sibling::span");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    @Override
    protected boolean isPageLoaded() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h2[text()='Sign In To Your Account']")));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    public LoginPage enterEmail(String email){
        sendKey(emailInput, email);
        return this;
    }

    public LoginPage enterPassword(String password){
        sendKey(passwordInput, password);
        return this;
    }

    public MainPage clickSubmit(){
        clickOnAWebElement(submitButton);
        return new MainPage(driver);
    }
    public RegistrationPage clickSignUpLink(){
        clickOnAWebElement(signUpLink);
        return new RegistrationPage(driver);
    }

    public boolean isInLoginPage(){
        return loginPageHeader.isDisplayed();
    }

    public  String getMissingEmailMessage(){
        return driver.findElement(missingEmailMessage).getText();
    }

    public  String getMissingPasswordMessage(){
        return driver.findElement(missingPasswordMessage).getText();
    }
}
