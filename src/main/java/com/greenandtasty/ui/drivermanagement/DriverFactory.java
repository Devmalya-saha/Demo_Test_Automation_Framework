package com.greenandtasty.ui.drivermanagement;


public interface DriverFactory {
    static BrowserDriver getDriver(String browserType){
        return switch ((browserType.toLowerCase())) {
            case "chrome" -> new ChromeDriverFactory();
            case "edge" -> new EdgeDriverFactory();
            case "firefox" -> new FireFoxDriverFactory();
            default -> throw new IllegalArgumentException("Browser is not available");
        };
    }
}
