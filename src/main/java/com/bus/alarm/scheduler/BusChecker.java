package com.bus.alarm.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.bus.alarm.repository.SubscribeRepository;
import com.bus.alarm.service.BusStopService;
@Component
public class BusChecker {

    @Autowired
    BusStopService busStopService;
    
    @Scheduled(fixedRate = 1000 * 60)
    public void busChecker() {
        busStopService.checkSubscriberBus();
    }
}
