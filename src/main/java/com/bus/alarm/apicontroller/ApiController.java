package com.bus.alarm.apicontroller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bus.alarm.DTO.BusstopDTO;
import com.bus.alarm.DTO.CoordinatesRequest;
import com.bus.alarm.service.BusStopService;

@RestController
@RequestMapping("/api")
public class ApiController {
    @Autowired
    BusStopService busStopService;

    @PostMapping("/range")
    public List<BusstopDTO> getBusstopsInRange(@RequestBody CoordinatesRequest request) {
        double smallLatitude = request.getSmallLatitude();
        double bigLatitude = request.getBigLatitude();
        double smallLongitude = request.getSmallLongitude();
        double bigLongitude = request.getBigLongitude();
        return busStopService.getBusstopsInRange(smallLatitude, bigLatitude, smallLongitude, bigLongitude);
    }

    @PostMapping("/bus-arrival-list")
    public Map<String, String> getBusArrivalList(@RequestBody Map<String, String> requestBody) {
        return busStopService.getBusArrivalMap(requestBody.get("stationId"));
    }
}
