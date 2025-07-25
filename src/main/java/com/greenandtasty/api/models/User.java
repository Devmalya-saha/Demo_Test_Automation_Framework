package com.greenandtasty.api.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private Preferences preferences;
}