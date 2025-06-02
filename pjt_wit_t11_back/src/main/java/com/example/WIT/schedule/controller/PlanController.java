package com.example.WIT.schedule.controller;

import com.example.WIT.schedule.dto.PlanSaveRequestDto;
import com.example.WIT.schedule.service.PlanService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/plan")
@RequiredArgsConstructor
public class PlanController {

    private final PlanService planService;

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody PlanSaveRequestDto dto) {
        System.out.println("수정 데이터 안에 schedule id 값 확인 : " + dto.getScheduleId());
        planService.savePlan(dto);
        return ResponseEntity.ok("저장 성공!");
    }

}
