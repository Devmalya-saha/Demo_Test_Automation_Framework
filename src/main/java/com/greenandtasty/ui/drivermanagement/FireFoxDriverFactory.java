package com.greenandtasty.ui.drivermanagement;

import com.greenandtasty.config.ConfigLoader;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;

public class FireFoxDriverFactory extends  AbstractBrowserDriver {
    @Override
    public WebDriver createLocalWebDriver(MutableCapabilities options) {
        return new FirefoxDriver((FirefoxOptions) options);
    }

    @Override
    public FirefoxOptions getOptions() {
        FirefoxOptions options=new FirefoxOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--headless");
        return options;

    }
}
