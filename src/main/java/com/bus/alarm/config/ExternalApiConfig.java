package com.bus.alarm.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import lombok.Getter;

@Component
@Getter
public class ExternalApiConfig {
    @Value("${busstop.api.url}")
    private String busStopApiUrl;

    @Value("${busstop.api.token}")
    private String busStopApiToken;

    @Value("${arrival.list.url}")
    private String arrivalListUrl;

    @Value("${bus.route.url}")
    private String busRouteUrl;
}