package com.example.WIT.location.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.WIT.location.dto.AttractionDto;
import com.example.WIT.location.mapper.AttractionMapper;
import java.util.*;

@Service
@RequiredArgsConstructor
public class AttractionService {
    private final AttractionMapper attractionMapper;
    public List<AttractionDto> searchAttraction(String searchQuery, int region, List<Integer> typeIds, int limit, int offset) {
        return attractionMapper.searchAttraction(searchQuery, region, typeIds, limit, offset);
    }

    public long countAttractions(int region , String searchQuery, List<Integer> typeIds){
        return attractionMapper.countAttractions(region, searchQuery, typeIds);
    }
}
