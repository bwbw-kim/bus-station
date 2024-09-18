package com.bus.alarm.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bus.alarm.DTO.SubscribeDTO;
import com.bus.alarm.config.ExternalApiConfig;
import com.bus.alarm.repository.SubscribeRepository;

@Controller
@RequestMapping("/")
public class MapController {

    @Autowired
    private SubscribeRepository subscribeRepository;

    @Autowired
    private ExternalApiConfig externalApiConfig;

    @GetMapping("/hello")
    public String hello() {
        // SubscribeDTO subscribeDTO = new SubscribeDTO();
        // subscribeDTO.setBusId("12345");
        // subscribeDTO.setBusStopId("12345");
        // subscribeDTO.setSubscriberKakaoId("12345");
        // subscribeRepository.save(SubscribeDTO.toEntity(subscribeDTO));
        return "index";
    }
}

