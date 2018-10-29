package com.nishi.cartracker.cartrackerrestapi.entity;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "alerts")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Alerts {
    @Id
    String alertId;
    String vin;
    Rule rule;
    Priority priority;
    Timestamp alertTime;
}

