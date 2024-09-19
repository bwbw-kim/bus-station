package com.bus.alarm.DTO.DatabaseDTO;
import com.bus.alarm.entity.SubscribeEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

//lombok dependency추가
@Getter
@Setter
@NoArgsConstructor
@ToString
public class SubscribeDTO {    
    private Long id;
    private String subscriberPhoneNumber;
    private String busStopId;
    private String busId;
    
    public static SubscribeDTO toDTO(SubscribeEntity subscribeEntity){
        SubscribeDTO subscribeDTO = new SubscribeDTO();
        subscribeDTO.setId(subscribeEntity.getId());
        subscribeDTO.setBusId(subscribeEntity.getBusId());
        subscribeDTO.setBusStopId(subscribeEntity.getBusStopId());
        subscribeDTO.setSubscriberPhoneNumber(subscribeEntity.getSubscriberPhoneNumber());
        return subscribeDTO;
    }

    public static SubscribeEntity toEntity(SubscribeDTO subscribeDTO) {
        SubscribeEntity subscribeEntity = new SubscribeEntity();
        subscribeEntity.setBusId(subscribeDTO.getBusId());
        subscribeEntity.setBusStopId(subscribeDTO.getBusStopId());
        subscribeEntity.setSubscriberPhoneNumber(subscribeDTO.getSubscriberPhoneNumber());
        return subscribeEntity;
    }
}
