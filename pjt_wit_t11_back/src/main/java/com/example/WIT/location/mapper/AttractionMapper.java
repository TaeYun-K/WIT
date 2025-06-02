package com.example.WIT.location.mapper;


import com.example.WIT.location.dto.AttractionDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AttractionMapper {
    List<AttractionDto> searchAttraction(String searchQuery, int region, List<Integer> typeIds, int limit, int offset);
    long countAttractions(@Param("region") int region,
                          @Param("search") String search,
                          @Param("typeIds") List<Integer> typeIds);
}
