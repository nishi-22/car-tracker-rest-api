package com.nishi.cartracker.cartrackerrestapi.repository;

import com.nishi.cartracker.cartrackerrestapi.entity.Vehicle;
import org.springframework.data.repository.CrudRepository;

public interface VehicleRepository extends CrudRepository<Vehicle, Integer> {
}
