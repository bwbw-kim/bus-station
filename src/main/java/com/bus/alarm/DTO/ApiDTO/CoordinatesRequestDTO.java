package com.bus.alarm.DTO.ApiDTO;

import lombok.Data;

@Data
public class CoordinatesRequestDTO {
    private double smallLatitude;
    private double bigLatitude;
    private double smallLongitude;
    private double bigLongitude;
}
