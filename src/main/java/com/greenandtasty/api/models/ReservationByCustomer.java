package com.greenandtasty.api.models;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;




@Data
@Builder
@Getter
@Setter
public class ReservationByCustomer {
    private String locationId;
    private String tableNumber;
    private String date;
    private String guestsNumber;
    private String timeFrom;
    private String timeTo;


}
