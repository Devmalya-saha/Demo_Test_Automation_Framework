package com.greenandtasty.constant;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public class Location {
    public static final String LOCATION_ID = "location-test";
    public static final int GUESTS_NUMBER = 3;
    public static final int EXCEEDING_GUESTS_NUMBER = 76;
    public static final String FUTURE_DATE = LocalDate.now().plus(Period.ofDays(2)).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    public static final String FUTURE_DATE_DDMMYYYY = LocalDate.now().plus(Period.ofDays(2)).format(DateTimeFormatter.ofPattern("ddMMyyyy"));
    public static final String PAST_DATE = LocalDate.now().minus(Period.ofDays(2)).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    public static final String USER_EMAIL = "jhon_smith@example.com";
    public static final String USER_PASSWORD = "Strong@123";
}
