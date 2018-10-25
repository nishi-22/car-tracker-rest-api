package com.nishi.cartracker.cartrackerrestapi.repository;

import com.nishi.cartracker.cartrackerrestapi.entity.Vehicle;
import com.nishi.cartracker.cartrackerrestapi.entity.VehicleLocations;
import com.nishi.cartracker.cartrackerrestapi.entity.VehicleTable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Collection;

public interface VehicleRepository extends CrudRepository<Vehicle, String> {

//    @Query("SELECT new com.nishi.cartracker.cartrackerrestapi.entity.Vehicle(v.vin, v.make, v.model, v.year, v.redlineRpm, v.maxFuelVolume, v.lastServiceDate) FROM Vehicle v WHERE vin = :vin")
//    public Vehicle findVehicleByVin(@Param("vin") String vin);

    String GET_VEHICLE_TABLE =
        "select v1.model as model, v1.year as year, a.vin as vin, v1.make as make, " +
                "date_format(v1.lastServiceDate, '%Y-%m-%d %h:%i:%s %p') as lastServiceDate, " +
                "v1.redlineRpm as redlineRpm, v1.maxFuelVolume as maxFuelVolume, count(*) as highAlerts " +
                "from alerts a  " +
                "inner join vehicle v1 on v1.vin = a.vin " +
                "where a.alertTime > now() - interval 48 hour and " +
                " a.priority = 0 group by a.vin;";

    @Query(value = GET_VEHICLE_TABLE, nativeQuery= true)
    Collection<VehicleTable> getVehicleTableData();

}
