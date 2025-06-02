package com.example.WIT.schedule.mapper;

import com.example.WIT.domain.Day;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DayMapper {
    void insertDay(Day day);
}
