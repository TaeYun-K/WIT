package com.example.WIT.location.mapper;


import com.example.WIT.location.dto.AttractionDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PlanMapper {
    List<AttractionDto> searchAttraction(String searchQuery, int region);
}
