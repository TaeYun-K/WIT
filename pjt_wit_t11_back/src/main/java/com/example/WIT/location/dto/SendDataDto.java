package com.example.WIT.location.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class SendDataDto {
    private Integer scheduleId;
    private int userId;
    private String tripName;
    private String tripDays;
    private String tripStart;
    private Map<String, List<PlanItemDto>> itinerary;
    private Map<String, List<DayAccommodationDto>> dayAccommodations;
    private List<AccommodationDto> accommodations;
    private Map<String, String> startTimes;
}
