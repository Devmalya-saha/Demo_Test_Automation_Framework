package com.greenandtasty.api.models;


import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Builder
@Getter
public class SignUp {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    @Override
    public String toString(){
        return "First Name: "+ this.firstName+"\n"+
                "Last Name: "+ this.lastName+"\n"+
                "email: "+this.email+"\n"+
                "password: "+this.password;
    }
}
