package com.example.WIT.schedule.dto;

import com.example.WIT.domain.Schedule;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ScheduleDto {
    private int scheduleId;
    private String title;
    private LocalDate startDate;
    private int dayCount;
    private int placeCount;

    public static ScheduleDto from(Schedule schedule, int placeCount) {
        ScheduleDto dto = new ScheduleDto();
        dto.setScheduleId(schedule.getScheduleId());
        dto.setTitle(schedule.getTitle());
        dto.setStartDate(schedule.getStartDate());
        dto.setDayCount(schedule.getDayCount());
        dto.setPlaceCount(placeCount);
        return dto;
    }
}
