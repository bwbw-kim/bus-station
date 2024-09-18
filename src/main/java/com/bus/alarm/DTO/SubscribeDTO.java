package com.bus.alarm.DTO;
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
    private String subscriberKakaoId;
    private String busStopId;
    private String busId;
    
    public static SubscribeDTO toDTO(SubscribeEntity subscribeEntity){
        SubscribeDTO subscribeDTO = new SubscribeDTO();
        subscribeDTO.setBusId(subscribeEntity.getBusId());
        subscribeDTO.setBusStopId(subscribeEntity.getBusStopId());
        subscribeDTO.setSubscriberKakaoId(subscribeEntity.getSubscriberKakaoId());
        return subscribeDTO;
    }

    public static SubscribeEntity toEntity(SubscribeDTO subscribeDTO) {
        SubscribeEntity subscribeEntity = new SubscribeEntity();
        subscribeEntity.setBusId(subscribeDTO.getBusId());
        subscribeEntity.setBusStopId(subscribeDTO.getBusStopId());
        subscribeEntity.setSubscriberKakaoId(subscribeDTO.getSubscriberKakaoId());
        return subscribeEntity;
    }
}
