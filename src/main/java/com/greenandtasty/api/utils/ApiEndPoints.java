package com.greenandtasty.api.utils;

import java.io.IOException;
import java.util.Properties;

public class ApiEndPoints {
    private static final Properties properties;
    private ApiEndPoints(){}

    static {
        properties = new Properties();
        try {
            properties.load(ApiEndPoints.class.getClassLoader()
                    .getResourceAsStream("api_endpoints.properties"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static String getEndPoint(String key) {
        return properties.getProperty(key.toUpperCase());
    }
}
