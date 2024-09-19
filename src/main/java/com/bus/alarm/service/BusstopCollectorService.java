package com.bus.alarm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.json.JSONArray;
import org.json.JSONObject;

import com.bus.alarm.DTO.DatabaseDTO.BusstopDTO;
import com.bus.alarm.DTO.DatabaseDTO.SubscribeDTO;
import com.bus.alarm.config.Config;
import com.bus.alarm.repository.BusstopRepository;
import com.bus.alarm.repository.SubscribeRepository;
import com.bus.alarm.utils.HttpUtils;

@Component
public class BusstopCollectorService {

    @Autowired
    private Config config;

    @Autowired
    private BusstopRepository busStopRepository;

    @Autowired
    private HttpUtils httpUtils;

    public void collectAndStoreBusStop() {
        int page = 0;
        int perPage = 200;
        while (true) {
            String url = config.getBusStopApiUrl() + "?" + "page=" + String.valueOf(page) + "&perPage=" + String.valueOf(perPage) + "&serviceKey=" + config.getBusStopApiToken();
            String response = httpUtils.getRequest(url);
            JSONObject jsonResponse = new JSONObject(response);
            if (jsonResponse.getInt("currentCount") <= 0) break;
            JSONArray busStopArray = jsonResponse.getJSONArray("data");
            for (int i = 0; i < busStopArray.length(); i++) {
                try {
                    JSONObject busStopInfo = busStopArray.getJSONObject(i);
                    BusstopDTO busStopDTO = new BusstopDTO();
                    busStopDTO.setLatitude(busStopInfo.getDouble("경도"));
                    busStopDTO.setLongitude(busStopInfo.getDouble("위도"));
                    busStopDTO.setId(busStopInfo.getString("정류장번호"));
                    busStopRepository.save(BusstopDTO.toEntity(busStopDTO));
                } catch (Exception e) {
                    continue;
                }
            }
            page = page + 1;
        }
        
    }
}
