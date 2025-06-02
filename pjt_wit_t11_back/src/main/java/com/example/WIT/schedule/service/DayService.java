package com.example.WIT.schedule.service;

import com.example.WIT.domain.Day;
import com.example.WIT.schedule.mapper.DayMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DayService {

    private final DayMapper dayMapper;

    public int save(Day day) {
        dayMapper.insertDay(day);
        return day.getDayId(); // PK 반환 (나중에 dayplace 저장 시 사용)
    }
}
