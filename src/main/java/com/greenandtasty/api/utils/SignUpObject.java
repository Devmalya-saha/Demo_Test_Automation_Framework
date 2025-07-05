package com.greenandtasty.api.utils;

import com.github.javafaker.Faker;
import com.greenandtasty.api.models.SignUp;

public class SignUpObject{
    static Faker faker;
    public static SignUp signUp;

    public static SignUp signUpObject() {
        faker = new Faker();
        signUp= SignUp.builder()
                .firstName(faker.name().firstName())
                .lastName(faker.name().lastName())
                .email(faker.internet().emailAddress())
                .password("Simple@123")
                .build();
        return signUp;


    }

}
