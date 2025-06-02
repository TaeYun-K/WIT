package com.example.WIT.schedule.service;

import com.example.WIT.domain.Day;
import com.example.WIT.domain.DayPlace;
import com.example.WIT.domain.Schedule;
import com.example.WIT.location.dto.DayAccommodationDto;
import com.example.WIT.location.mapper.AccommodationMapper;
import com.example.WIT.location.service.AccommodationService;
import com.example.WIT.schedule.dto.DaySaveDto;
import com.example.WIT.schedule.dto.PlaceDto;
import com.example.WIT.schedule.dto.PlanSaveRequestDto;
import com.example.WIT.schedule.mapper.ScheduleMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlanService {

    private final ScheduleService scheduleService;
    private final DayService dayService;
    private final DayPlaceService dayPlaceService;
    private final AccommodationService accommodationService;
    private final ScheduleMapper scheduleMapper;
    private final AccommodationMapper accommodationMapper;

    @Transactional
    public void deleteSchedule(int scheduleId) {
        List<Integer> dayIds = scheduleMapper.findDayIdsByScheduleId(scheduleId);
        accommodationMapper.deleteAccommodationsByScheduleId(scheduleId);

        if (!dayIds.isEmpty()) {
            scheduleMapper.deleteDayPlacesByDayIds(dayIds);
        }

        scheduleMapper.deleteDaysByScheduleId(scheduleId);
        scheduleMapper.deleteSchedule(scheduleId);
    }


    public void savePlan(PlanSaveRequestDto dto) {

        // ✅ 기존 일정이 있으면 먼저 삭제
        if (dto.getScheduleId() != null) {
            log.info("🔄 기존 일정 삭제 - scheduleId: {}", dto.getScheduleId());
            deleteSchedule(dto.getScheduleId());  // 바로 아래에서 정의할 메서드 사용
        }

        log.info("👤 사용자 ID: {}", dto.getUserId());
        log.info("📝 여행 이름: {}", dto.getTitle());
        log.info("📅 여행 시작일: {}", dto.getStartDate());
        log.info("📆 여행 일수: {}일", dto.getDayCount());

        // 1. 일정 저장
        Schedule schedule = new Schedule();
        schedule.setUserId(dto.getUserId());
        schedule.setTitle(dto.getTitle());
        schedule.setStartDate(LocalDate.parse(dto.getStartDate()));
        schedule.setDayCount(dto.getDayCount());

        int scheduleId = scheduleService.save(schedule);
        log.info("✅ 일정 저장 완료 - scheduleId: {}", scheduleId);

        // 2. 숙소 저장
        accommodationService.saveAll(dto.getAccommodations(), scheduleId);
        log.info("🏨 숙소 저장 완료 - 총 {}개", dto.getAccommodations().size());

        // 3. day 저장
        for (int dayNumber = 1; dayNumber <= dto.getDayCount(); dayNumber++) {
            log.info("📅 Day {} 처리 시작", dayNumber);

            DaySaveDto daySaveDto = new DaySaveDto();
            daySaveDto.setScheduleId(scheduleId);
            daySaveDto.setDayNumber(dayNumber);
            daySaveDto.setDepartureTime(dto.getStartTimes().get(dayNumber));

            List<DayAccommodationDto> accItems = dto.getDayAccommodations().get(dayNumber);
            DayAccommodationDto start = accItems.get(0);
            DayAccommodationDto end = accItems.get(1);

            daySaveDto.setStartAccommodationIndex(start.getIndex());
            daySaveDto.setEndAccommodationIndex(end.getIndex());
            daySaveDto.setStartStayDuration(start.getStayHour() * 60 + start.getStayMin());
            daySaveDto.setEndStayDuration(end.getStayHour() * 60 + end.getStayMin());
            daySaveDto.setStartTravelCar(start.getTravelCar());
            daySaveDto.setStartTravelBus(start.getTravelBus());
            daySaveDto.setEndTravelCar(end.getTravelCar());
            daySaveDto.setEndTravelBus(end.getTravelBus());

            int dayId = dayService.save(mapToDay(daySaveDto));

            log.info("📍 Day {} 저장 완료 - dayId: {}", dayNumber, dayId);

            // 4. dayplace 저장
            List<PlaceDto> places = dto.getItinerary().get(dayNumber);
            if (places != null) {
                for (int i = 0; i < places.size(); i++) {
                    PlaceDto placeDto = places.get(i);

                    DayPlace dayPlace = new DayPlace();
                    dayPlace.setDayId(dayId);
                    dayPlace.setNo(placeDto.getNo());

                    // ✅ 순서 기반 visitOrder
                    dayPlace.setVisitOrder(i + 1);

                    // ✅ 시간 계산 기반 duration
                    int duration = placeDto.getStayHour() * 60 + placeDto.getStayMin();
                    dayPlace.setDuration(duration);

                    dayPlace.setTravelCar(placeDto.getTravelCar());
                    dayPlace.setTravelBus(placeDto.getTravelBus());

                    System.out.println(dayPlace.toString());
                    dayPlaceService.save(dayPlace);
                    log.info("🗺️ 명소 저장 - [{}] {} (순서 {}, {}분)", placeDto.getNo(), placeDto.getTitle(), i + 1, duration);
                }
            }
        }
        log.info("🎉 전체 여행 일정 저장 완료!");
    }

    private Day mapToDay(DaySaveDto dto) {
        Day day = new Day();
        day.setScheduleId(dto.getScheduleId());
        day.setDayNumber(dto.getDayNumber());
        day.setDepartureTime(dto.getDepartureTime());
        day.setStartAccommodationIndex(dto.getStartAccommodationIndex());
        day.setEndAccommodationIndex(dto.getEndAccommodationIndex());
        day.setStartStayDuration(dto.getStartStayDuration());
        day.setEndStayDuration(dto.getEndStayDuration());
        day.setStartTravelCar(dto.getStartTravelCar());
        day.setStartTravelBus(dto.getStartTravelBus());
        day.setEndTravelCar(dto.getEndTravelCar());
        day.setEndTravelBus(dto.getEndTravelBus());
        return day;
    }
}
