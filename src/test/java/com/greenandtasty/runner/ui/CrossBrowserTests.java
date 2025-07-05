package com.greenandtasty.runner.ui;

import com.greenandtasty.hooks.ui.BrowserHolder;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

@CucumberOptions(
        features = "src/test/resources/features/ui",
        glue = {"com.greenandtasty.stepdefinitions.ui", "com.greenandtasty.stepdefinitions.common", "com.greenandtasty.hooks.ui"},
        plugin = {"pretty", "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"},
        tags = "@Smoke",
        monochrome = true
)

public class CrossBrowserTests extends AbstractTestNGCucumberTests {
    @BeforeClass
    @Parameters({"browserValue"})
    public void setUp(String browser) {
        System.out.println("Setting up browser: " + browser);
        BrowserHolder.setBrowser(browser);
        System.out.println("Set browser for thread: " + browser);
    }


}
