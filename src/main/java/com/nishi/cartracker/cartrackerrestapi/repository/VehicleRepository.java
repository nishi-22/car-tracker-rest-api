package com.nishi.cartracker.cartrackerrestapi.repository;

import com.nishi.cartracker.cartrackerrestapi.entity.Vehicle;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface VehicleRepository extends CrudRepository<Vehicle, String> {

//    @Query("SELECT new com.nishi.cartracker.cartrackerrestapi.entity.Vehicle(v.vin, v.make, v.model, v.year, v.redlineRpm, v.maxFuelVolume, v.lastServiceDate) FROM Vehicle v WHERE vin = :vin")
//    public Vehicle findVehicleByVin(@Param("vin") String vin);
}
