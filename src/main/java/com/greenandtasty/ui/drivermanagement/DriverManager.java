package com.greenandtasty.ui.drivermanagement;
import com.greenandtasty.config.ConfigLoader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;

import java.time.Duration;


public class DriverManager{
    private volatile static DriverManager instance;
    private  static final ThreadLocal<WebDriver> threadLocal= new ThreadLocal<>();
    private static final ThreadLocal<String> browser=new ThreadLocal<>();
    private DriverManager(){}
    public static DriverManager getInstance(){
        if(instance==null){
            synchronized (DriverManager.class){
                if(instance==null){
                    return instance=new DriverManager();
                }
            }
        }
        return instance;
    }
    public  void setBrowserName(String browserName){
        DriverManager.browser.set(browserName);
    }

    public WebDriver getDriver() throws WebDriverException {
        if(threadLocal.get()==null){
            String browserName=browser.get();
            if(browserName==null){
             browserName=System.getProperty("browser", ConfigLoader.getEnvironmentSpecificProperty("qa", "browser"));
            }
            System.out.println("Creating driver for browser: " + browserName +
                    " in thread: " + Thread.currentThread().getId());
            threadLocal.set(DriverFactory.getDriver(browserName).createDriver());
        }

        threadLocal.get().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        return threadLocal.get();
    }
    public void quitdriver(){
        WebDriver driver=threadLocal.get();
        if(driver!=null){
            driver.quit();
            threadLocal.remove();
            browser.remove();
        }
    }

}
