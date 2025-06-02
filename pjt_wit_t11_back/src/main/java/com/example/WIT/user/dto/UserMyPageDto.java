// com.example.WIT.user.dto.UserMyPageDto.java
package com.example.WIT.user.dto;

import com.example.WIT.domain.User;
import com.example.WIT.schedule.dto.ScheduleDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Getter
@AllArgsConstructor
public class UserMyPageDto {
    private String username;
    private String email;
    private String createdAt; // 날짜 포맷을 문자열로 반환
    private List<ScheduleDto> schedules; //스케쥴 관리

    //Entity -> Dto 변환 메서드 ( 보안성, 중첩 dto 관리 )
    public static UserMyPageDto from(User user, List<ScheduleDto> scheduleList) {
        return new UserMyPageDto(
            user.getUsername(),
            user.getEmail(),
            user.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy년 M월 d일")),
            scheduleList
        );
    }
}
