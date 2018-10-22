package com.nishi.cartracker.cartrackerrestapi.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.UUID;

@Data
@Getter
@Setter
public class ReadingsRequest {

    TireReadings tires;
    String vin;
    Double latitude;
    Double longitude;
    Timestamp timestamp;
    Int fuelVolume;
    Integer speed;
    Integer engineHp;
    Boolean checkEngineLightOn;
    Boolean engineCoolantLow;
    Boolean cruiseControlOn;
    Integer engineRpm;

    public Readings getReadingsEntity(){
        Readings readings = new Readings();
        readings.setReadingId(UUID.randomUUID().toString());
        readings.setVin(vin);
        readings.setLatitude(latitude);
        readings.setLongitude(longitude);
        readings.setTimestamp(timestamp);
        readings.setFuelVolume(fuelVolume);
        readings.setSpeed(speed);
        readings.setEngineHp(engineHp);
        readings.setCheckEngineLightOn(checkEngineLightOn);
        readings.setEngineCoolantLow(engineCoolantLow);
        readings.setCruiseControlOn(cruiseControlOn);
        readings.setEngineRpm(engineRpm);
        readings.setTireReadingId(UUID.randomUUID().toString());
        return readings;
    }
}
