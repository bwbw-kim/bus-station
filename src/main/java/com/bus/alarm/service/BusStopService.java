package com.bus.alarm.service;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import com.bus.alarm.DTO.DatabaseDTO.BusstopDTO;
import com.bus.alarm.DTO.DatabaseDTO.SubscribeDTO;
import com.bus.alarm.config.Config;
import com.bus.alarm.entity.BusstopEntity;
import com.bus.alarm.entity.SubscribeEntity;
import com.bus.alarm.repository.BusstopRepository;
import com.bus.alarm.repository.SubscribeRepository;
import com.bus.alarm.utils.HttpUtils;
import com.bus.alarm.utils.MessageUtils;

@Component
public class BusStopService {

    @Autowired
    private Config config;

    @Autowired
    private SubscribeRepository subscribeRepository;
    
    @Autowired
    private BusstopRepository busstopRepository;

    @Autowired
    private HttpUtils httpUtils;

    @Autowired
    private MessageUtils messageUtils;

    public List<BusstopDTO> getBusstopsInRange(double smallLatitude, double bigLatitude, double smallLongitude, double bigLongitude) {
        List<BusstopEntity> queryResult = busstopRepository.findByLongitudeBetweenAndLatitudeBetween(smallLongitude, bigLongitude, smallLatitude, bigLatitude);
        List<BusstopDTO> result = queryResult.stream()
            .map(BusstopDTO::toDTO) 
            .collect(Collectors.toList());
        return result;
    }

    private String getBusName(String routeId) {
        String url = config.getBusRouteUrl() + "?" + "serviceKey=" + config.getBusStopApiToken() + "&routeId=" + routeId;
        String response = httpUtils.getRequest(url);
        String busName = "";
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new InputSource(new StringReader(response)));
            doc.getDocumentElement().normalize();
            NodeList nodeList = doc.getElementsByTagName("busRouteInfoItem");
            if (nodeList.getLength() > 0) {
                Element element = (Element) nodeList.item(0);
                busName = element.getElementsByTagName("routeName").item(0).getTextContent();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return busName;
    }

    public Map<String, Map<String, String>> getBusArrivalMap(String stationId) {
        String url = config.getArrivalListUrl() + "?" + "serviceKey=" + config.getBusStopApiToken() + "&stationId=" + stationId;
        String response = httpUtils.getRequest(url);
        Map<String, Map<String, String>> busArrivalMap = new HashMap<String, Map<String, String>>();
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new InputSource(new StringReader(response)));
            doc.getDocumentElement().normalize();
            NodeList nodeList = doc.getElementsByTagName("busArrivalList");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Map<String, String> busArrivalInfo = new HashMap<String, String>();
                Element element = (Element) nodeList.item(i);
                String busId = element.getElementsByTagName("routeId").item(0).getTextContent();
                String busNumber = getBusName(busId);
                String location = element.getElementsByTagName("locationNo1").item(0).getTextContent();
                busArrivalInfo.put("busNumber", busNumber);
                busArrivalInfo.put("location", location);
                busArrivalInfo.put("busId", busId);
                busArrivalMap.put(busId, busArrivalInfo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return busArrivalMap;
    }

    public void checkSubscriberBus(){
        int page = 0;
        int size = 10;
        Pageable pageable;
        while(true) {
            pageable = PageRequest.of(page, size);
            Page<SubscribeEntity> result = subscribeRepository.findAll(pageable);
            if (result.getContent().size() <= 0) break;
            for(SubscribeEntity entity : result) {
                SubscribeDTO subscribeDTO = SubscribeDTO.toDTO(entity);
                Map<String, Map<String, String>> busArrivalMap= getBusArrivalMap(subscribeDTO.getBusStopId());
                if (busArrivalMap.containsKey(subscribeDTO.getBusId()) && Integer.parseInt(busArrivalMap.get(subscribeDTO.getBusId()).get("location")) <= 2) {
                    messageUtils.sendMessage(config.getTargetUser(), config.getTargetUser(), "곧 " + getBusName(subscribeDTO.getBusId()) + " 버스가 도착해요!");
                    subscribeRepository.deleteById(subscribeDTO.getId());
                }
            }
            page++;
        }
    }
}
