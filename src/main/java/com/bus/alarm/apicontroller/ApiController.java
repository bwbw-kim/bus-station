package com.bus.alarm.apicontroller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bus.alarm.DTO.ApiDTO.CoordinatesRequestDTO;
import com.bus.alarm.DTO.ApiDTO.StationIdBusIdDTO;
import com.bus.alarm.DTO.ApiDTO.StationIdDTO;
import com.bus.alarm.DTO.DatabaseDTO.BusstopDTO;
import com.bus.alarm.service.BusStopService;
import com.bus.alarm.service.SubscribeService;

@RestController
@RequestMapping("/api")
public class ApiController {
    @Autowired
    BusStopService busStopService;

    @Autowired
    SubscribeService subscribeService;

    @PostMapping("/range")
    public List<BusstopDTO> getBusstopsInRange(@RequestBody CoordinatesRequestDTO request) {
        double smallLatitude = request.getSmallLatitude();
        double bigLatitude = request.getBigLatitude();
        double smallLongitude = request.getSmallLongitude();
        double bigLongitude = request.getBigLongitude();
        return busStopService.getBusstopsInRange(smallLatitude, bigLatitude, smallLongitude, bigLongitude);
    }

    @PostMapping("/bus-arrival-list")
    public Map<String, Map<String, String>> getBusArrivalList(@RequestBody StationIdDTO request) {
        return busStopService.getBusArrivalMap(request.getStationId());
    }

    @PostMapping("/subscribe")
    public Boolean subscribe(@RequestBody StationIdBusIdDTO request) {
        return subscribeService.subscribe(request.getBusId(), request.getStationId());
    }
}
