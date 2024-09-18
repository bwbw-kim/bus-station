package com.bus.alarm.scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.bus.alarm.service.BusstopCollectorService;

@Component
public class BusstopCollector {

    @Autowired
    BusstopCollectorService busstopCollectorService;
    
    // @Scheduled(fixedRate = 1000 * 60 * 60 * 24)
    @Scheduled(cron = "0 0 0 * * ?")
    public void collectBusStop() {
        busstopCollectorService.collectAndStoreBusStop();
    }
}
