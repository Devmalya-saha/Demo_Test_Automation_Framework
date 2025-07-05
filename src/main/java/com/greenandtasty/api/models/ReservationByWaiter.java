package com.greenandtasty.api.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ReservationByWaiter {
    private String locationId;
    private String tableNumber;
    private String date;
    private String guestsNumber;
    private String timeFrom;
    private String timeTo;
    private String clientType;
    private String customerName;
}
