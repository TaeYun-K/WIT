package com.example.WIT.domain;

import lombok.Data;

@Data
public class Day {
    private int dayId;
    private int scheduleId;
    private int dayNumber;
    private String departureTime;
    private int startAccommodationIndex;
    private int endAccommodationIndex;
    private int startStayDuration;
    private int endStayDuration;
    private int startTravelCar;
    private int startTravelBus;
    private int endTravelCar;
    private int endTravelBus;
}
