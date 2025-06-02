package com.example.WIT.schedule.dto;

import lombok.Data;

@Data
public class PlaceDto {
    private Integer scheduleId;
    private int placeId;
    private int contentTypeId;
    private int no; // attractions 테이블 PK
    private String title;
    private int stayHour;
    private int stayMin;
    private int visitOrder;
    private int duration;
    private int travelCar; //자차 이동시간
    private int travelBus; //버스 이동시간

    //attractions 조인
    private String firstImage1;
    private String homepage;
    private double latitude;
    private double longitude;
    private String addr1;
    private String overview;
    private double rating;
    private int reviewCount;
}
