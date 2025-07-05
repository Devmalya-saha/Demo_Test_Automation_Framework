package com.greenandtasty.api.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class SignIn {
SignUp signUp;
private String email;
private String password;
    @Override
    public String toString(){
        return "E-Mail: "+ this.email+"\n"+
                "Password: "+this.password;
    }
}
