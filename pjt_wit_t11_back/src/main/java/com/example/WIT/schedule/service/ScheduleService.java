package com.example.WIT.schedule.service;

import com.example.WIT.domain.Schedule;
import com.example.WIT.schedule.mapper.ScheduleMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleMapper scheduleMapper;

    //scheduleId를 반환하여 day 저장에 사용
    public int save(Schedule schedule) {
        scheduleMapper.insertSchedule(schedule);
        return schedule.getScheduleId(); // PK 반환
    }
}
