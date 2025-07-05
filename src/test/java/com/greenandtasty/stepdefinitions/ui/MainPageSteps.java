package com.greenandtasty.stepdefinitions.ui;

import com.greenandtasty.hooks.ui.UIHooks;
import com.greenandtasty.hooks.ui.UITestContext;
import com.greenandtasty.ui.pageobjects.MainPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.List;

public class MainPageSteps {

    final UITestContext testContext=UITestContext.getInstance();


    @Given("the user is on the main page")
    public void theUserNavigatesToTheMainPage() {
        MainPage page = new MainPage(testContext.getDriver());
        testContext.setMainPage(page);
    }

    @Then("the user should see the correct page title")
    public void theUserShouldSeeTheCorrectPageTitle() {
        WebDriver driver = testContext.getDriver();
        String actualTitle = driver.getTitle();
        Assert.assertEquals(actualTitle, "Restaurant-App");
    }

    @And("the logo should be visible")
    public void theLogoShouldBeVisible() {
        MainPage page = testContext.getMainPage();
        Assert.assertTrue(page.isLogoDisplayed());
    }

    @Then("this {string} should be interactable")
    public void thisShouldBeInteractable(String elementName) {
        MainPage page = testContext.getMainPage();
        WebElement element = page.getElementByName(elementName);
        page.clickOnAWebElement(element);
    }

    @Then("popular dishes should be visible")
    public void popularDishesShouldBeVisible() {
        MainPage page = testContext.getMainPage();
        List<WebElement> popularDishesList = page.getPopularDishes();
        Assert.assertTrue(popularDishesList.stream().allMatch(WebElement::isDisplayed));
    }

    @Then("locations should be visible")
    public void locationsShouldBeVisible() {
        MainPage page = testContext.getMainPage();
        page.scrollToLocationsSection();
        List<WebElement> listOfLocations = page.getLocations();
        Assert.assertTrue(listOfLocations.stream().anyMatch(WebElement::isDisplayed));

    }
}
