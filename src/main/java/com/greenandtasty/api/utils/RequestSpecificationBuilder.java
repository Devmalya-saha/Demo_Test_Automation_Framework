package com.greenandtasty.api.utils;

import static io.restassured.RestAssured.*;

import com.greenandtasty.api.models.SignUp;
import com.greenandtasty.config.ConfigLoader;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

public class RequestSpecificationBuilder {
    static {
        baseURI = ConfigLoader.getEnvironmentSpecificProperty("qa","base_uri");
        System.out.println(baseURI);
    }
    public static RequestSpecification requestSpecification;


    public static RequestSpecification setRequestSpecificationWithoutAuth() {
        return requestSpecification = new RequestSpecBuilder()
                .setBaseUri(baseURI)
                .setContentType("application/json")
                .build();
    }
    public static RequestSpecification setRequestSpecificationWithAuth(SignUp signUp){
        String accessToken=AuthHelper.TokenGeneration(signUp);
        return requestSpecification=new RequestSpecBuilder()
                .setBaseUri(baseURI)
                .setContentType("application/json")
                .addHeader("Authorization", "Bearer " + accessToken)
                .build();
        }




    }

