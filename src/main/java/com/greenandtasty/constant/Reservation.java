package com.greenandtasty.constant;

import java.time.Instant;
import java.util.Arrays;

public class Reservation {
    public static final int STAR_RATING = 5;
    public static final String FEEDBACK_TEXT = "This is a test feedback " + Instant.now();
    public static final String UPDATED_FEEDBACK_TEXT = "This is an updated test feedback " + Instant.now();
    public static final String FEEDBACK_TYPE_CUISINE = "Cuisine";
    public static final String FEEDBACK_TYPE_SERVICE = "Service";

    public static void main(String[] args) {
        System.out.println(FEEDBACK_TEXT.substring(FEEDBACK_TEXT.lastIndexOf(" ") + 1));
        System.out.println(UPDATED_FEEDBACK_TEXT);
    }
}
