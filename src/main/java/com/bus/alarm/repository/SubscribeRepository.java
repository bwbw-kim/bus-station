package com.bus.alarm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bus.alarm.entity.SubscribeEntity;

public interface SubscribeRepository extends JpaRepository<SubscribeEntity, Long> {
    List<SubscribeEntity> findByBusStopId(String keyword);
}
