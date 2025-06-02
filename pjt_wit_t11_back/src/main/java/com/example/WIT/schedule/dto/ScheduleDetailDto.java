package com.example.WIT.schedule.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class ScheduleDetailDto {
    private int scheduleId;
    private int userId;
    private String title;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    private int dayCount;
    private List<DayDetailDto> days; // ✅ day 정보 + 방문지 포함
}
