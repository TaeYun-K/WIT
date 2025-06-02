package com.example.WIT.schedule.controller;

import com.example.WIT.location.dto.AccommodationDto;
import com.example.WIT.location.dto.DayAccommodationDto;
import com.example.WIT.location.dto.PlanItemDto;
import com.example.WIT.location.dto.SendDataDto;
import com.example.WIT.location.mapper.AccommodationMapper;
import com.example.WIT.schedule.dto.DayAccommodationDBDto;
import com.example.WIT.schedule.dto.DayDetailDto;
import com.example.WIT.schedule.dto.PlaceDto;
import com.example.WIT.schedule.dto.ScheduleDetailDto;
import com.example.WIT.schedule.mapper.ScheduleMapper;
import com.example.WIT.schedule.service.PlanService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Slf4j
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleMapper scheduleMapper;
    private final AccommodationMapper accommodationMapper;
    private final PlanService planService;

    @DeleteMapping("/schedule/{scheduleId}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable int scheduleId) {
        planService.deleteSchedule(scheduleId);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/schedule/{scheduleId}")
    public ResponseEntity<SendDataDto> getScheduleDetail(@PathVariable int scheduleId) {
        // 1. 일정 기본 정보 조회
        ScheduleDetailDto schedule = scheduleMapper.findScheduleById(scheduleId);
        if (schedule == null) {
            return ResponseEntity.notFound().build();
        }

        // 2. day 리스트 조회
        List<DayDetailDto> days = scheduleMapper.findDaysByScheduleId(scheduleId);

        // 3. 응답용 데이터 맵 초기화
        Map<String, List<PlanItemDto>> itinerary = new LinkedHashMap<>();
        Map<String, List<DayAccommodationDto>> dayAccommodations = new HashMap<>();
        Map<String, String> startTimes = new HashMap<>();

        for (DayDetailDto day : days) {
            // 날짜 key (예: "2025-06-01") — dayNumber를 기반으로 key 설정
            String dateKey = "day" + day.getDayNumber();

            // 방문지 조회 및 세팅
            List<PlaceDto> placeDtos = scheduleMapper.findPlacesByDayId(day.getDayId());
            day.setPlaces(placeDtos);
            DayAccommodationDBDto dayAccommodationDBdto = scheduleMapper.findDayAccommodationByDayId(day.getDayId());
            day.setDayAccommodations(dayAccommodationDBdto);
            // PlanItemDto 변환
            List<PlanItemDto> planItems = placeDtos.stream()
                    .sorted(Comparator.comparingInt(PlaceDto::getVisitOrder))
                    .map(p -> {
                        PlanItemDto item = new PlanItemDto();
                        System.out.println("contentTypeId : " + p.getContentTypeId());
                        item.setContentTypeId(p.getContentTypeId());
                        item.setNo(p.getNo());
                        item.setContentId(p.getPlaceId()); // 여기선 placeId가 contentId에 해당
                        item.setTitle(p.getTitle());
                        item.setStayHour(p.getDuration()/60);
                        item.setStayMin(p.getDuration()%60);
                        item.setTravelCar(p.getTravelCar());
                        item.setTravelBus(p.getTravelBus());
                        item.setFirstImage1(p.getFirstImage1());
                        item.setHomepage(p.getHomepage());

                        // 나머지 값은 DB에서 조회할 수 없으면 기본값 처리
                        item.setAreaCode(0);
                        item.setSiGunGuCode(0);
                        item.setFirstImage2(null);
                        item.setMapLevel(0);
                        item.setLatitude(p.getLatitude());
                        item.setLongitude(p.getLongitude());
                        item.setTel(null);
                        item.setAddr1(p.getAddr1());
                        item.setAddr2(null);
                        item.setOverview(p.getOverview());
                        item.setRating(p.getRating());
                        item.setReviewCount(p.getReviewCount());
                        item.setInstanceId(p.getVisitOrder()); // 방문 순서 기반
                        item.setTime(null);

                        return item;
                    })
                    .toList();

            itinerary.put(dateKey, planItems);

            // 4. 숙소 정보 세팅 (startAccommodationIndex, endAccommodationIndex → index로만 저장)
            List<DayAccommodationDto> result = new ArrayList<>(2);

            // 1) 시작 숙소
            DayAccommodationDto start = new DayAccommodationDto();
            start.setIndex(dayAccommodationDBdto.getStartAccommodationIndex());
            // stayDuration 분 → 시간/분
            int startDur = dayAccommodationDBdto.getStartStayDuration(); // 분 단위
            start.setStayHour(startDur / 60);
            start.setStayMin(startDur % 60);
            start.setTime("");   // 빈 문자열
            start.setTravelCar(dayAccommodationDBdto.getStartTravelCar());
            start.setTravelBus(dayAccommodationDBdto.getStartTravelBus());
            result.add(start);

            // 2) 종료 숙소
            DayAccommodationDto end = new DayAccommodationDto();
            end.setIndex(dayAccommodationDBdto.getEndAccommodationIndex());
            int endDur = dayAccommodationDBdto.getEndStayDuration();   // 분 단위
            end.setStayHour(endDur / 60);
            end.setStayMin(endDur % 60);
            end.setTime("");
            // 종료 지점 이후의 이동 시간 정보가 DBDto에 없다면 0 또는 기본값 처리
            end.setTravelCar(0);
            end.setTravelBus(0);
            result.add(end);

            dayAccommodations.put(dateKey, result);

            // 5. 시작 시간 저장
            if (day.getDepartureTime() != null) {
                startTimes.put(dateKey, day.getDepartureTime());
            }
        }

        // 6. 전체 숙소 목록 조회
        List<AccommodationDto> accommodations = accommodationMapper.findAccommodationsByScheduleId(scheduleId);
        // 7. SendDataDto 생성 및 응답 구성
        SendDataDto dto = new SendDataDto();
        dto.setScheduleId(scheduleId);
        dto.setUserId(schedule.getUserId());
        dto.setTripName(schedule.getTitle());
        dto.setTripDays(String.valueOf(schedule.getDayCount()));
        dto.setTripStart(schedule.getStartDate().toString());
        dto.setItinerary(itinerary);
        dto.setDayAccommodations(dayAccommodations);
        dto.setStartTimes(startTimes);
        dto.setAccommodations(accommodations);

        log.info("📝 title={}, startDate={}, days={}일", dto.getTripName(), dto.getTripStart(), dto.getTripDays());
        log.info("🏨 숙소 수: {}", dto.getAccommodations().size());
        dto.getItinerary().forEach((day, places) ->
                log.info("📆 Day {} - 명소 수: {}", day, places.size()));


        return ResponseEntity.ok(dto);
    }


}
