package com.greenandtasty.runner.api;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions(
        features = "src/test/resources/features/api",
        glue = {"com.greenandtasty.stepdefinitions.api", "com.greenandtasty.stepdefinitions.common", "com.greenandtasty.hooks.api"},
        plugin = {"pretty","io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"},
        tags = "@Regression",
        monochrome = true
)

public class RegressionTestRunner extends AbstractTestNGCucumberTests {

}
