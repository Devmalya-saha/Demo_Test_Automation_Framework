package com.greenandtasty.hooks.api;

import com.greenandtasty.utils.DeleteReservation;
import com.greenandtasty.utils.DeleteUser;
import io.cucumber.java.After;
import io.cucumber.java.Scenario;

public class ApiHooks {
    private ApiContext apiContext;

    public ApiHooks(){
        this.apiContext = ApiContext.getInstance();
    }

    @After
    public void tearDown(Scenario scenario){
        if(scenario.isFailed()){
            System.out.println(apiContext.getResponse().asPrettyString());
        }
        apiContext.setResponse(null);
        if(apiContext.getSignedUpUser()!=null && apiContext.getSignedUpUser().getEmail()!=null &&!apiContext.getSignedUpUser().getEmail().isBlank()){
            DeleteUser.delete(apiContext.getSignedUpUser());
        }
        if(apiContext.getReservationId()!=null && !apiContext.getReservationId().isBlank()){
            DeleteReservation.delete(apiContext.getSignedUpUser(), apiContext.getReservationId());
        }
    }
}
