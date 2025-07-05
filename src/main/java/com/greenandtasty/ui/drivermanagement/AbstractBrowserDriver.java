package com.greenandtasty.ui.drivermanagement;

import com.greenandtasty.config.ConfigLoader;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;

public abstract class AbstractBrowserDriver implements BrowserDriver {

    @Override
    public WebDriver createDriver(){
        MutableCapabilities options = getOptions();
        if(useGrid()){
            return createRemoteWebDriver(options);
        } else {
            return createLocalWebDriver(options);
        }
    }

    private boolean useGrid() {
        return ConfigLoader.getEnvironmentSpecificProperty("qa", "useGrid").equalsIgnoreCase("true");
    }

    protected WebDriver createRemoteWebDriver(MutableCapabilities options) {
        try {
            String gridUrl = ConfigLoader.getEnvironmentSpecificProperty("qa", "gridUrl");
            return new RemoteWebDriver(new URL(gridUrl), options);
        } catch (Exception e) {
            throw new WebDriverException("Unable to initiate web driver");
        }
    }

    protected abstract WebDriver createLocalWebDriver(MutableCapabilities options);

    protected abstract MutableCapabilities getOptions();


}
