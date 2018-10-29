package com.nishi.cartracker.cartrackerrestapi.repository;

import com.nishi.cartracker.cartrackerrestapi.entity.Alerts;
import com.nishi.cartracker.cartrackerrestapi.entity.VehicleTable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;
import java.util.Collection;

public interface AlertsRepository extends CrudRepository<Alerts, String> {

    String GET_ALERTS_BY_VIN = "SELECT alertId, vin, rule, priority, " +
            "date_format(alertTime, '%Y-%m-%d %h:%i:%s %p') as alertTime " +
            "FROM alerts WHERE vin = ?1";

    @Query(value = GET_ALERTS_BY_VIN, nativeQuery= true)
    ArrayList<Alerts> getAlertsByVIN(String vin);
}
