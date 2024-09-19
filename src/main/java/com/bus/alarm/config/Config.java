package com.bus.alarm.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import lombok.Getter;

@Component
@Getter
public class Config {
    @Value("${busstop.api.url}")
    private String busStopApiUrl;

    @Value("${target.user}")
    private String targetUser;

    @Value("${busstop.api.token}")
    private String busStopApiToken;

    @Value("${arrival.list.url}")
    private String arrivalListUrl;

    @Value("${bus.route.url}")
    private String busRouteUrl;

    @Value("${host.url}")
    private String hostUrl;

    @Value("${msg.api.key}")
    private String msgApiKey;

    @Value("${msg.api.secret}")
    private String msgApiSecret;

    @Value("${msg.api.url}")
    private String msgApiUrl;
}