package com.example.WIT.schedule.dto;

import com.example.WIT.location.dto.AccommodationDto;
import com.example.WIT.location.dto.DayAccommodationDto;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class PlanSaveRequestDto {
    private Integer scheduleId;
    private int userId;
    private String title;
    private String startDate; // 또는 LocalDate
    private int dayCount;

    private List<AccommodationDto> accommodations; // 위치: location.dto
    private Map<Integer, List<DayAccommodationDto>> dayAccommodations; // 일차별 숙소
    private Map<Integer, String> startTimes;
    private Map<Integer, List<PlaceDto>> itinerary; // 일차별 명소 리스트
}
