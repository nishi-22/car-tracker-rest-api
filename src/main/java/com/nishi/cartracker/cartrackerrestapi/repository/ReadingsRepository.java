package com.nishi.cartracker.cartrackerrestapi.repository;

import com.nishi.cartracker.cartrackerrestapi.entity.Readings;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;


public interface ReadingsRepository extends CrudRepository<Readings, String> {

    String GET_VEHICLE_LOCATIONS =
            "SELECT * FROM readings WHERE vin = ?1 and timestamp > (NOW() - INTERVAL 30 minute)";

    @Query(value = GET_VEHICLE_LOCATIONS, nativeQuery= true)
    ArrayList<Readings> getVehiclePosition(String vin);
}
