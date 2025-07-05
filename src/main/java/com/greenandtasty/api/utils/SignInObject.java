package com.greenandtasty.api.utils;

import com.greenandtasty.api.models.SignIn;
import com.greenandtasty.api.models.SignUp;

public class SignInObject {
    public static SignIn signIn;


    public static SignIn signInObject(SignUp signUp) {
        return signIn = SignIn.builder()
                .email(signUp.getEmail())
                .password(signUp.getPassword())
                .build();
    }

}
