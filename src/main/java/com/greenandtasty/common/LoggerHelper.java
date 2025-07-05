package com.greenandtasty.common;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class goLoggerHelper {
    public static Logger getLogger(Class<?> clazz) {
        return LogManager.getLogger(clazz);
    }
}
