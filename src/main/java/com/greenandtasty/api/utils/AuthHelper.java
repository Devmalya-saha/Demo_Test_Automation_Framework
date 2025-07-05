package com.greenandtasty.api.utils;

import com.greenandtasty.api.models.SignIn;
import com.greenandtasty.api.models.SignUp;
import io.restassured.response.Response;


public class AuthHelper {
    public static String accessToken;

    public static String TokenGeneration(SignUp signUp) {
        SignIn signIn = SignInObject.signInObject(signUp);
        Response response = ApiUtil.postRequest(RequestSpecificationBuilder.setRequestSpecificationWithoutAuth(),
                ApiEndPoints.getEndPoint("SIGN_IN"),
                signIn);
        return accessToken = response.jsonPath().getString("accessToken");

    }


}
