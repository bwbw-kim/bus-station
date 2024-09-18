package com.bus.alarm.service;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.stream.StreamSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import com.bus.alarm.DTO.BusstopDTO;
import com.bus.alarm.config.ExternalApiConfig;
import com.bus.alarm.entity.BusstopEntity;
import com.bus.alarm.repository.BusstopRepository;
import com.bus.alarm.utils.HttpUtils;

@Component
public class BusStopService {

    @Autowired
    private ExternalApiConfig externalApiConfig;
    
    @Autowired
    BusstopRepository busstopRepository;

    @Autowired
    private HttpUtils httpUtils;

    public List<BusstopDTO> getBusstopsInRange(double smallLatitude, double bigLatitude, double smallLongitude, double bigLongitude) {
        List<BusstopEntity> queryResult = busstopRepository.findByLongitudeBetweenAndLatitudeBetween(smallLongitude, bigLongitude, smallLatitude, bigLatitude);
        List<BusstopDTO> result = queryResult.stream()
            .map(BusstopDTO::toDTO) 
            .collect(Collectors.toList());
        return result;
    }

    private String getBusName(String routeId) {
        String url = externalApiConfig.getBusRouteUrl() + "?" + "serviceKey=" + externalApiConfig.getBusStopApiToken() + "&routeId=" + routeId;
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

    public Map<String, String> getBusArrivalMap(String stationId) {
        String url = externalApiConfig.getArrivalListUrl() + "?" + "serviceKey=" + externalApiConfig.getBusStopApiToken() + "&stationId=" + stationId;
        String response = httpUtils.getRequest(url);
        Map<String, String> busArrivalMap = new HashMap<String, String>();
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new InputSource(new StringReader(response)));
            doc.getDocumentElement().normalize();
            NodeList nodeList = doc.getElementsByTagName("busArrivalList");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Element element = (Element) nodeList.item(i);
                String busName = getBusName(element.getElementsByTagName("routeId").item(0).getTextContent());
                String location = element.getElementsByTagName("locationNo1").item(0).getTextContent();
                busArrivalMap.put(busName, location);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return busArrivalMap;
    }
}
