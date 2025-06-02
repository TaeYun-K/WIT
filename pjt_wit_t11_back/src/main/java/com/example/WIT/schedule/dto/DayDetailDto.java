package com.example.WIT.schedule.dto;

import com.example.WIT.location.dto.DayAccommodationDto;
import lombok.Data;

import java.util.List;

@Data
public class DayDetailDto {
    private int dayId;
    private int dayNumber;
    private String departureTime;
    private DayAccommodationDBDto dayAccommodations;
    private List<PlaceDto> places; // ✅ 해당 일차의 방문지들
}
