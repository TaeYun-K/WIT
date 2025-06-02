package com.example.WIT.domain;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Schedule {
    private int scheduleId;
    private int userId;
    private String title;
    private LocalDate startDate;
    private int dayCount;
}
