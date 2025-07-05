package com.greenandtasty.api.utils;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class ApiUtil {

    public static Response getRequest(RequestSpecification specification, String endPoint, Map<String, ?> pathParams, Map<String, ?> queryParams) {
        RequestSpecification request = given()
                .spec(specification);
        if ((pathParams != null && !pathParams.isEmpty()) && (queryParams != null && !queryParams.isEmpty())) {
            request.pathParams(pathParams).queryParams(queryParams);

        } else if (pathParams != null && !pathParams.isEmpty()) {
            request.pathParams(pathParams);

        } else if (queryParams != null && !queryParams.isEmpty()) {
            request.queryParams(queryParams);

        }
        return request.when().get(endPoint);
    }

    public static Response postRequest(RequestSpecification specification, String endPoint, Object object) {
        return RestAssured.given()
                .spec(specification)
                .body(object)
                .log().all()
                .when()
                .post(endPoint);
    }

    public static Response putRequest(RequestSpecification specification, String endPoint, Object object) {
        return RestAssured.given()
                .spec(specification)
                .body(object)
                .when()
                .put(endPoint);
    }

    public static Response deleteRequest(RequestSpecification specification, String endPoint, Object object) {
        return RestAssured.given()
                .spec(specification)
                .body(object)
                .contentType(ContentType.JSON)
                .when()
                .delete(endPoint);
    }

    public static Response deleteRequestWithPathParam(RequestSpecification specification, String endPoint, Map<String,?>pathParams) {
        return RestAssured.given()
                .spec(specification)
                .pathParams(pathParams)
                .when()
                .delete(endPoint);

    }


}
