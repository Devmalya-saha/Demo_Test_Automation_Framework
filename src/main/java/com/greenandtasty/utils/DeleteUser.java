package com.greenandtasty.utils;

import com.greenandtasty.api.models.DeleteUserModel;
import com.greenandtasty.api.models.SignUp;
import com.greenandtasty.api.utils.ApiEndPoints;
import com.greenandtasty.api.utils.ApiUtil;
import com.greenandtasty.api.utils.DeleteObject;
import com.greenandtasty.api.utils.RequestSpecificationBuilder;

public class DeleteUser {

    public static void delete(SignUp user){
        try {
            ApiUtil.deleteRequest(RequestSpecificationBuilder.setRequestSpecificationWithoutAuth(),
                    ApiEndPoints.getEndPoint("USER_DELETION"),
                    new DeleteObject(user)).then().log().all();
        } catch (Exception e) {
            //log exception here
        }
    }
}
