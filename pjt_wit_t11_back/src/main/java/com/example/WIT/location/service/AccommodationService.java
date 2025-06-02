package com.example.WIT.location.service;

import com.example.WIT.location.dto.AccommodationDto;
import com.example.WIT.location.mapper.AccommodationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccommodationService {

    private final AccommodationMapper accommodationMapper;

    public void saveAll(List<AccommodationDto> accommodations, int scheduleId) {
        for (AccommodationDto dto : accommodations) {
            dto.setScheduleId(scheduleId);
            accommodationMapper.insertAccommodation(dto);
        }
    }
}
