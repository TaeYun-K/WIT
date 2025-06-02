package com.example.WIT.user.controller;

import com.example.WIT.auth.mapper.AuthUserMapper;
import com.example.WIT.auth.util.JwtUtil;
import com.example.WIT.domain.Schedule;
import com.example.WIT.domain.User;
import com.example.WIT.schedule.dto.ScheduleDto;
import com.example.WIT.schedule.mapper.ScheduleMapper;
import com.example.WIT.user.dto.UpdateUserRequest;
import com.example.WIT.user.dto.UserMyPageDto;
import com.example.WIT.user.service.UserService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// UserController.java
@Slf4j
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final AuthUserMapper authUserMapper;
    private final JwtUtil jwtUtil;
    private final ScheduleMapper scheduleMapper;
    private final UserService userService;

    @GetMapping("/mypage")
    public ResponseEntity<UserMyPageDto> getMyPage(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        String email = jwtUtil.extractEmail(token); // 토큰에서 사용자 email 추출
        User user = authUserMapper.findByEmail(email); // DB에서 사용자 정보 조회

        if (user == null) {
            return ResponseEntity.status(404).build();
        }

        System.out.println(user.getUserId());
        List<Schedule> schedules = scheduleMapper.findSchedulesByUserId(user.getUserId());

        List<ScheduleDto> scheduleDtos = schedules.stream()
                .map(s -> {
                    int placeCount = scheduleMapper.countPlacesByScheduleId(s.getScheduleId());
                    return ScheduleDto.from(s, placeCount);
                })
                .toList();
        System.out.println(user.toString());
        log.info("📌 유저 [{}]의 여행 목록:", user.getUsername());
        for (Schedule s : schedules) {
            log.info(" - [{}] {} ~ ({}일)", s.getScheduleId(), s.getTitle(), s.getDayCount());
        }

        return ResponseEntity.ok(UserMyPageDto.from(user,scheduleDtos)); // 프론트에 사용자 정보 반환
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateUser(@RequestBody UpdateUserRequest request,
                                        @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        Claims claims = jwtUtil.parseToken(token);
        int userId = claims.get("userId", Integer.class);

        userService.updateUser(userId, request);
        return ResponseEntity.ok("회원 정보가 수정되었습니다.");
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUser(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        Claims claims = jwtUtil.parseToken(token);
        int userId = claims.get("userId", Integer.class);

        userService.deleteUserById(userId); // 실제 삭제 처리
        return ResponseEntity.ok("계정이 삭제되었습니다.");
    }

}
