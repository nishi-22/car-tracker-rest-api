package com.nishi.cartracker.cartrackerrestapi.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tire_readings")
public class TireReadings {

    @Id
    String tireReadingId;
    Integer frontLeft;
    Integer frontRight;
    Integer rearLeft;
    Integer rearRight;

    public Integer getFrontLeft() {
        return frontLeft;
    }

    public void setFrontLeft(Integer frontLeft) {
        this.frontLeft = frontLeft;
    }

    public Integer getFrontRight() {
        return frontRight;
    }

    public void setFrontRight(Integer frontRight) {
        this.frontRight = frontRight;
    }

    public Integer getRearLeft() {
        return rearLeft;
    }

    public void setRearLeft(Integer rearLeft) {
        this.rearLeft = rearLeft;
    }

    public Integer getRearRight() {
        return rearRight;
    }

    public void setRearRight(Integer rearRight) {
        this.rearRight = rearRight;
    }

    public String getTireReadingId() {
        return tireReadingId;
    }

    public void setTireReadingId(String tireReadingId) {
        this.tireReadingId = tireReadingId;
    }

}
