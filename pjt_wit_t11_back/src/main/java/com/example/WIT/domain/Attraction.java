package com.example.WIT.domain;

import lombok.Data;

@Data
public class Attraction {
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
    private String reviewCount;
}
