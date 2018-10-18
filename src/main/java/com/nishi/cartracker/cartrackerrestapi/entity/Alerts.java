package com.nishi.cartracker.cartrackerrestapi.entity;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "alerts")
public class Alerts {
    public Alerts() {
    }

    public Alerts(String alertId, String vin, Rule rule, Priority priority) {
        this.alertId = alertId;
        this.vin = vin;
        this.rule = rule;
        this.priority = priority;
    }

    public void setAlertId(String alertId) {
        this.alertId = alertId;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public void setRule(Rule rule) {
        this.rule = rule;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    @Id
    String alertId;
    String vin;
    Rule rule;
    Priority priority;
    Timestamp alertTime;

    public void setAlertTime(Timestamp alertTime) {
        this.alertTime = alertTime;
    }

    public Timestamp getAlertTime() {
        return alertTime;
    }

    public String getAlertId() {
        return alertId;
    }

    public String getVin() {
        return vin;
    }

    public Rule getRule() {
        return rule;
    }

    public Priority getPriority() {
        return priority;
    }
}
