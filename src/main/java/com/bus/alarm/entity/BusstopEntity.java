package com.bus.alarm.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "Busstop")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class BusstopEntity {

    @Id
    private String id;

    @Column(nullable = false)
    private double latitude;

    @Column(nullable = false)
    private double longitude;

}
