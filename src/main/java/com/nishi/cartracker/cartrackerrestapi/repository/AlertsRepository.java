package com.nishi.cartracker.cartrackerrestapi.repository;

import com.nishi.cartracker.cartrackerrestapi.entity.Alerts;
import org.springframework.data.repository.CrudRepository;

public interface AlertsRepository extends CrudRepository<Alerts, String> {
}
