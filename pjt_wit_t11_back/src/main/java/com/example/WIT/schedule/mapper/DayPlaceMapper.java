package com.example.WIT.schedule.mapper;

import com.example.WIT.domain.DayPlace;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DayPlaceMapper {
    void insertDayPlace(DayPlace dayPlace);
}
