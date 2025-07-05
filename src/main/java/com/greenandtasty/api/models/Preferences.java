package com.greenandtasty.api.models;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Preferences {
    private List<String> favoriteLocations;
    private List<String> dietaryRestrictions;
    private boolean notificationsEnabled;
}
