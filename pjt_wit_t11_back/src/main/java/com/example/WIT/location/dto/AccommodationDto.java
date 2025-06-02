package com.example.WIT.location.dto;

import lombok.Data;

@Data
public class AccommodationDto {
    private long id;
    private String name;
    private String address;
    private String phone;
    private double latitude;
    private double longitude;
    private int scheduleId;
}
