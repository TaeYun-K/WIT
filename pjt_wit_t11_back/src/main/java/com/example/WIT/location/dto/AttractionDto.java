package com.example.WIT.location.dto;

import com.example.WIT.schedule.dto.PlaceDto;
import lombok.Data;

import java.util.List;

@Data
public class AttractionDto {
    private int no;
    private int contentId;
    private String title;
    private int contentTypeId;
    private int areaCode;
    private int siGunGuCode;
    private String firstImage1;
    private String firstImage2;
    private int mapLevel;
    private double latitude;
    private double longitude;
    private String tel;
    private String addr1;
    private String addr2;
    private String homepage;
    private String overview;
    private String rating;
    private String review_count;
}
