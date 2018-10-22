package com.nishi.cartracker.cartrackerrestapi.repository;

import com.nishi.cartracker.cartrackerrestapi.entity.Readings;
import org.springframework.data.repository.CrudRepository;

public interface ReadingsRepository extends CrudRepository<Readings, String> {
}
