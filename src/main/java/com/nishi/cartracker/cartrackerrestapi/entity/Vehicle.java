package com.nishi.cartracker.cartrackerrestapi.entity;

import lombok.Data;
import lombok.Value;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "vehicle")
@Data
@Value
public class Vehicle {

    public Vehicle(String vin, String make, String model, Integer year,
                   Integer redlineRpm, Integer maxFuelVolume, String lastServiceDate) {
        this.vin = vin;
        this.make = make;
        this.model = model;
        this.year = year;
        this.redlineRpm = redlineRpm;
        this.maxFuelVolume = maxFuelVolume;
        this.lastServiceDate = lastServiceDate;
    }

    @Id
    String vin;
    String make;
    String model;
    Integer year;
    Integer redlineRpm;
    Integer maxFuelVolume;
    String lastServiceDate;
}
