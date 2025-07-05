package com.greenandtasty.hooks.api;

import com.greenandtasty.api.models.*;
import com.greenandtasty.hooks.ui.UITestContext;
import io.restassured.response.Response;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiContext {

    private static final ThreadLocal<ApiContext> threadLocalContext = ThreadLocal.withInitial(ApiContext::new);
    public static ApiContext getInstance() {
        return threadLocalContext.get();
    }

    public static void remove() {
        threadLocalContext.remove();
    }

    private ApiContext() {
    }

    private Response response;
    private String reservationId = "";
    private SignUp signedUpUser;
    private SignIn signInObject;
    private User userObject;
    private ReservationByCustomer reservationObjectBody;
    private ReservationByWaiter reservationByWaiterBody;
    private Feedback feedbackObject;
}