package com.nishi.cartracker.cartrackerrestapi.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Entity
@Table(name = "readings")
@Getter
@Setter
public class Readings {
    @Id
    String readingId;
    String vin;
    Double latitude;
    Double longitude;
    Timestamp timestamp;
    Integer fuelVolume;
    Integer speed;
    Integer engineHp;
    Boolean checkEngineLightOn;
    Boolean engineCoolantLow;
    Boolean cruiseControlOn;
    Integer engineRpm;
    String tireReadingId;
}
