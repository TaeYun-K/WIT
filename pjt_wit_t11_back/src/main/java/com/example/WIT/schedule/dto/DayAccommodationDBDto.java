package com.example.WIT.schedule.dto;

import lombok.Data;

@Data
public class DayAccommodationDBDto {
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
}
