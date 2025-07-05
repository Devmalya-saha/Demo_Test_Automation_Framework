package com.greenandtasty.hooks.ui;

import com.greenandtasty.api.models.ReservationByCustomer;
import com.greenandtasty.api.models.SignUp;
import com.greenandtasty.ui.pageobjects.*;
import lombok.Data;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;


@Data
public class UITestContext {

    private static final ThreadLocal<UITestContext> threadLocalContext=ThreadLocal.withInitial(UITestContext::new);


    public static UITestContext getInstance() {
        return threadLocalContext.get();
    }

    public static void remove() {
        threadLocalContext.remove();
    }

    private UITestContext() {
    }

    private WebDriver driver;
    private WebElement element;
    private List<WebElement> webElementList;
    private MainPage mainPage;
    private RegistrationPage registrationPage;
    private LoginPage loginPage;
    private BookingPage bookingPage;
    private LocationPage locationPage;
    private ReservationPage reservationPage;
    private SignUp signUp;
    private String reservationLocation;
    private String feedbackText;
    private int initialNumberOfReservations;

}
