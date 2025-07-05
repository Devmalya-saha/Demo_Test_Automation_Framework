package com.greenandtasty.api.models;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class Feedback {
    private String cuisineComment;
    private String cuisineRating;
    private String reservationId;
    private String serviceComment;
    private String serviceRating;
}
