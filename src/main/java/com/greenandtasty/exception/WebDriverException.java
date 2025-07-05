package com.greenandtasty.exception;

public class WebDriverException extends RuntimeException {
    public WebDriverException(String message) {
        super(message);
    }
    public WebDriverException(String message, Throwable cause) {
        super(message, cause);
    }
    public WebDriverException(Throwable cause) {
        super(cause);
    }
    public WebDriverException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
