package com.example.WIT.location.controller;

import com.example.WIT.location.dto.PageResultDto;
import com.example.WIT.location.service.AttractionService;
import com.example.WIT.location.dto.AttractionDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Slf4j
@RestController
@RequestMapping("/api/attractions")
@RequiredArgsConstructor
public class AttractionController {

    private final AttractionService attractionService;

    @GetMapping
    public ResponseEntity<PageResultDto> searchAttractions(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) Integer selectedRegion,
            @RequestParam(required = false) String searchQuery,
            @RequestParam(required = false) String type
    ){
        // 1) 페이징 계산
        int offset = (page - 1) * size;
        // 2) type → contentTypeId 리스트 매핑
        Map<String, List<Integer>> TYPE_MAP = Map.of(
                "places",    List.of(12, 39),
                "restaurant", List.of(2),
                "cafe",       List.of(1)
        );
        List<Integer> typeIds;
        if (type != null) {
            // type이 null이 아닐 때만 getOrDefault 호출
            typeIds = TYPE_MAP.getOrDefault(type, Collections.emptyList());
        } else {
            // type이 null일 땐 빈 리스트
            typeIds = Collections.emptyList();
        }
        //3) 전체 개수 조회
        long total = attractionService.countAttractions(selectedRegion, searchQuery, typeIds);

        List<AttractionDto> results = attractionService.searchAttraction(searchQuery, selectedRegion, typeIds, size, offset);
        PageResultDto<AttractionDto> result = new PageResultDto<>();
        result.setTotalElements(total);
        result.setTotalPages((int) Math.ceil((double) total / size));
        result.setContent(results);
        return ResponseEntity.ok(result);
    }
}
