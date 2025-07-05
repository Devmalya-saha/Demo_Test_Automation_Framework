package com.greenandtasty.ui.pageobjects;

import com.greenandtasty.common.LoggerHelper;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public abstract class BasePage {
    protected Logger log;
    protected WebDriver driver;
    protected JavascriptExecutor js;
    protected FluentWait<WebDriver> wait;


    protected  BasePage(WebDriver driver) {

        this.driver = driver;

        this.log = LoggerHelper.getLogger(this.getClass());

        this.wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(30))
                .pollingEvery(Duration.ofMillis(5))
                .ignoring(NoSuchElementException.class, ElementClickInterceptedException.class);

        js = (JavascriptExecutor) driver;

        PageFactory.initElements(driver, this);
    }

    public void waitForElement(WebElement element) {

        try {
            wait.until(ExpectedConditions.visibilityOf(element));
            log.info(WebElement.class.getName() + " is visible");

        } catch (TimeoutException | NoSuchElementException e) {
            log.error("Element not found");
        }

    }

    public void clickOnAWebElement(WebElement button) {
        wait.until(ExpectedConditions.visibilityOf(button)).click();
        log.info("WebElement " + button.getText() + " is clicked");

    }

    public void sendKey(WebElement field, String value) {
        wait.until(ExpectedConditions.visibilityOf(field)).clear();
        field.sendKeys(value);
    }

    public void scrollToAnELement(WebElement targetElement) {
        wait.until(ExpectedConditions.visibilityOf(targetElement));
        js.executeScript("arguments[0].scrollIntoView(true);", targetElement);
    }

    public BasePage dismissAlert() {
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.alertIsPresent());
        driver.switchTo().alert().dismiss();
        return this;
    }

    public void refreshPage() {
        driver.navigate().refresh();
        PageFactory.initElements(driver, this);
    }
}
