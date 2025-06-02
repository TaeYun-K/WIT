package com.example.WIT.schedule.service;

import com.example.WIT.domain.DayPlace;
import com.example.WIT.schedule.mapper.DayPlaceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DayPlaceService {

    private final DayPlaceMapper dayPlaceMapper;

    public void save(DayPlace dayPlace) {
        dayPlaceMapper.insertDayPlace(dayPlace);
    }
}
