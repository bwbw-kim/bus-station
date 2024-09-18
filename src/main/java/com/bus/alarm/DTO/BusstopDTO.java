package com.bus.alarm.DTO;
import com.bus.alarm.entity.BusstopEntity;
import com.bus.alarm.entity.SubscribeEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class BusstopDTO {    
    private String id;
    private double latitude;
    private double longitude;
    
    public static BusstopDTO toDTO(BusstopEntity busStopEntity){
        BusstopDTO busStopDTO = new BusstopDTO();
        busStopDTO.setId(busStopEntity.getId());
        busStopDTO.setLatitude(busStopEntity.getLatitude());
        busStopDTO.setLongitude(busStopEntity.getLongitude());
        return busStopDTO;
    }

    public static BusstopEntity toEntity(BusstopDTO busStopDTO) {
        BusstopEntity busStopEntity = new BusstopEntity();
        busStopEntity.setId(busStopDTO.getId());
        busStopEntity.setLatitude(busStopDTO.getLatitude());
        busStopEntity.setLongitude(busStopDTO.getLongitude());
        return busStopEntity;
    }
}
