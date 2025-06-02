package com.example.WIT.domain;

import lombok.Data;

@Data
public class DayPlace {

    private int placeId; // auto_increment
    private int dayId;
    private int no; // attractions.no

    private int visitOrder;
    private int duration;

    private int travelCar;
    private int travelBus;
}
