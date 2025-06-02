package com.example.WIT.location.mapper;

import com.example.WIT.location.dto.AccommodationDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AccommodationMapper {
    void insertAccommodation(AccommodationDto accommodation);
    void insertScheduleAccommodation(@Param("scheduleId") int scheduleId,
                                     @Param("accommodationId") long accommodationId);

    List<AccommodationDto> findAccommodationsByScheduleId(@Param("scheduleId") int scheduleId);

    void deleteAccommodationsByScheduleId(@Param("scheduleId") int scheduleId);
}
