package com.bus.alarm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bus.alarm.entity.BusstopEntity;

public interface BusstopRepository extends JpaRepository<BusstopEntity, Long> {
    List<BusstopEntity> findByLongitudeBetweenAndLatitudeBetween(
        double smallLongitude, double bigLongitude,
        double smallLatitude, double bigLatitude
    );
}
