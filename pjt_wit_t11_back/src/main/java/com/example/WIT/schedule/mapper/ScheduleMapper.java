package com.example.WIT.schedule.mapper;

import com.example.WIT.domain.Schedule;
import com.example.WIT.location.dto.AccommodationDto;
import com.example.WIT.location.dto.DayAccommodationDto;
import com.example.WIT.schedule.dto.DayAccommodationDBDto;
import com.example.WIT.schedule.dto.DayDetailDto;
import com.example.WIT.schedule.dto.PlaceDto;
import com.example.WIT.schedule.dto.ScheduleDetailDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ScheduleMapper {
    void insertSchedule(Schedule schedule);

    List<Schedule> findSchedulesByUserId(int userId);

    int countPlacesByScheduleId(int scheduleId);

    List<Integer> findDayIdsByScheduleId(int scheduleId);

    void deleteDayPlacesByDayIds(@Param("dayIds") List<Integer> dayIds);

    void deleteDaysByScheduleId(int scheduleId);

    void deleteSchedule(int scheduleId);

    ScheduleDetailDto findScheduleById(int scheduleId);

    List<DayDetailDto> findDaysByScheduleId(int scheduleId);

    List<PlaceDto> findPlacesByDayId(int dayId);

    DayAccommodationDBDto findDayAccommodationByDayId(int dayId);

}
