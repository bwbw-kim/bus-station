package com.bus.alarm.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.bus.alarm.DTO.DatabaseDTO.SubscribeDTO;
import com.bus.alarm.config.Config;
import com.bus.alarm.repository.BusstopRepository;
import com.bus.alarm.repository.SubscribeRepository;

@Component
public class SubscribeService {

    @Autowired
    private Config config;
    
    @Autowired
    BusstopRepository busstopRepository;

    @Autowired
    SubscribeRepository subscribeRepository;

    public boolean subscribe(String busId, String stationId) {
        try {
            SubscribeDTO subscribeDTO = new SubscribeDTO();
            subscribeDTO.setBusId(busId);
            subscribeDTO.setBusStopId(stationId);
            subscribeDTO.setSubscriberPhoneNumber(config.getTargetUser());
            subscribeRepository.save(SubscribeDTO.toEntity(subscribeDTO));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
