package com.example.WIT.schedule.dto;

import lombok.Data;

import java.util.List;

@Data
public class DaySaveDto {

    private int dayNumber;
    private int scheduleId;

    private String departureTime;

    private int startAccommodationIndex;
    private int endAccommodationIndex;

    private int startStayDuration;
    private int endStayDuration;

    private int startTravelCar;
    private int startTravelBus;
    private int endTravelCar;
    private int endTravelBus;

    private List<PlaceDto> places; // ✅ dayplace에 같이 저장할 명소 정보
}
