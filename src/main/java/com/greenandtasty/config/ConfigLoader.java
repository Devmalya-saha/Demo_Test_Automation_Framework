package com.greenandtasty.config;

import com.greenandtasty.api.utils.ApiEndPoints;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigLoader {
    private static final Properties properties;
    static {
        properties=new Properties();
        try {
            properties.load(ConfigLoader.class.getClassLoader()
                    .getResourceAsStream("config.properties"));
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
    public static String getEnvironmentSpecificProperty(String env,String key){
        return properties.getProperty(env+"."+key);
    }
}
