package com.nishi.cartracker.cartrackerrestapi.repository;

import com.nishi.cartracker.cartrackerrestapi.entity.ChartReadings;
import com.nishi.cartracker.cartrackerrestapi.entity.Readings;
import com.nishi.cartracker.cartrackerrestapi.entity.VehicleLocations;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import java.util.Collection;


public interface ReadingsRepository extends CrudRepository<Readings, String> {

    String GET_VEHICLE_LOCATIONS =
        "SELECT latitude, longitude FROM readings WHERE vin = ?1 and timestamp > (NOW() - INTERVAL 30 minute)";

    String GET_FUEL_VOLUME_READINGS =
        "SELECT TIME_FORMAT(timestamp, \"%h:%i:%s %p\") as name , fuelVolume as value " +
                "from readings where vin = ?1 and timestamp > (now() - INTERVAL ?2 minute) order by timestamp";

    String GET_ENGINE_HP_READINGS =
        "SELECT TIME_FORMAT(timestamp, \"%h:%i:%s %p\") as name, engineHp as value " +
                "from readings where vin = ?1 and timestamp > (now() - INTERVAL ?2 minute) order by timestamp";

    String GET_SPEED_READINGS =
        "SELECT TIME_FORMAT(timestamp, \"%h:%i:%s %p\") as name, speed as value " +
                "from readings where vin = ?1 and timestamp > (now() - INTERVAL ?2 minute) order by timestamp";

    String GET_ENGINE_RPM_READINGS =
        "SELECT TIME_FORMAT(timestamp, \"%h:%i:%s %p\") as name, engineRpm as value " +
                "from readings where vin = ?1 and timestamp > (now() - INTERVAL ?2 minute)  order by timestamp";


    @Query(value = GET_VEHICLE_LOCATIONS, nativeQuery= true)
    Collection<VehicleLocations> getVehiclePosition(String vin);

    @Query(value = GET_FUEL_VOLUME_READINGS, nativeQuery = true)
    Collection<ChartReadings> getFuelVolumeReadings(String vin, Integer minute);

    @Query(value = GET_ENGINE_HP_READINGS, nativeQuery = true)
    Collection<ChartReadings> getEngineHpReadings(String vin, Integer minute);

    @Query(value = GET_SPEED_READINGS, nativeQuery = true)
    Collection<ChartReadings> getSpeedReadings(String vin, Integer minute);

    @Query(value = GET_ENGINE_RPM_READINGS, nativeQuery = true)
    Collection<ChartReadings> getEngineRPMReadings(String vin, Integer minute);



}
