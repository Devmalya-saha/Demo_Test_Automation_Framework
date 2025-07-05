package com.greenandtasty.utils;

import com.greenandtasty.api.models.SignUp;
import com.greenandtasty.api.utils.ApiEndPoints;
import com.greenandtasty.api.utils.ApiUtil;
import com.greenandtasty.api.utils.RequestSpecificationBuilder;

import java.util.HashMap;
import java.util.Map;

public class DeleteReservation {

    public static void delete(SignUp user, String reservationId){
        Map<String, String> pathParams = new HashMap<>();
        try {
            pathParams.put("id",reservationId);
            ApiUtil.deleteRequestWithPathParam(RequestSpecificationBuilder.setRequestSpecificationWithAuth(user),
                    ApiEndPoints.getEndPoint("RESERVATION_CANCELLATIONS"),
                    pathParams).then().log().all();
            System.out.println("Deleted: "+reservationId);
        } catch (Exception e) {
            //log exception here
        }
    }
}
