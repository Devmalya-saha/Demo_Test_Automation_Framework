package com.greenandtasty.ui.drivermanagement;

import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;


public class EdgeDriverFactory extends AbstractBrowserDriver {
    @Override
    public WebDriver createLocalWebDriver(MutableCapabilities options) {
        return new EdgeDriver((EdgeOptions) options);
    }

    @Override
    public EdgeOptions getOptions() {
        EdgeOptions options=new EdgeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--headless");
        return options;

    }
}
