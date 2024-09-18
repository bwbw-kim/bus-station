package com.bus.alarm.DTO;

import lombok.Data;

@Data
public class CoordinatesRequest {
    private double smallLatitude;
    private double bigLatitude;
    private double smallLongitude;
    private double bigLongitude;
}
