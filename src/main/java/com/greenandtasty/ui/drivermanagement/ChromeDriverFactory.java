package com.greenandtasty.ui.drivermanagement;

import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;


public class ChromeDriverFactory extends AbstractBrowserDriver {

    @Override
    public WebDriver createLocalWebDriver(MutableCapabilities options) {
        return new ChromeDriver((ChromeOptions) options);
    }

    @Override
    public ChromeOptions getOptions() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--headless");
        return options;
    }
}
