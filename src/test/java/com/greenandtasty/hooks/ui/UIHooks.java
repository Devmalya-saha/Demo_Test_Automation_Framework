package com.greenandtasty.hooks.ui;

import com.greenandtasty.api.models.SignUp;
import com.greenandtasty.config.ConfigLoader;
import com.greenandtasty.ui.drivermanagement.DriverManager;
import com.greenandtasty.utils.DeleteUser;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.qameta.allure.Allure;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;


import java.io.File;
import java.io.IOException;

public class UIHooks {


    @Before
    public void setUp() {


        DriverManager.getInstance().setBrowserName(BrowserHolder.getBrowser());

        WebDriver driver = DriverManager.getInstance().getDriver();
        UITestContext.getInstance().setDriver(driver);

        String baseUrl = ConfigLoader.getEnvironmentSpecificProperty("qa", "frontend");
        driver.get(baseUrl);
        driver.manage().window().maximize();
    }

    @After
    public void tearDown(Scenario scenario) {
        UITestContext testContext = UITestContext.getInstance();
        SignUp signUp = testContext.getSignUp();
        if (signUp != null) {
            DeleteUser.delete(signUp);
        }
        WebDriver driver = testContext.getDriver();
        if (scenario.isFailed()) {
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            /*TakesScreenshot screenshotStorer=(TakesScreenshot)driver;
            byte[] scrrenShotByte=screenshotStorer.getScreenshotAs(OutputType.BYTES);
            scenario.attach(scrrenShotByte, "image/png", scenario.getName());*/

            try {
                FileUtils.copyFile(screenshot, new File("target/screenshots/" + scenario.getId() + ".png"));;
                Allure.addAttachment(scenario.getName(), "image/png", FileUtils.openInputStream(screenshot), ".png");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (driver != null) {
            DriverManager.getInstance().quitdriver();
            testContext.setDriver(null);
        }
        UITestContext.remove();
    }


}
