package com.greenandtasty.api.utils;

import com.greenandtasty.api.models.SignIn;
import com.greenandtasty.api.models.SignUp;

public class DeleteObject {
    private String email;
    public DeleteObject(SignUp signUp){
        this.email=signUp.getEmail();
    }
}
