package com.nishi.cartracker.cartrackerrestapi.repository;

import com.nishi.cartracker.cartrackerrestapi.entity.Entries;
import org.springframework.data.repository.CrudRepository;

public interface EntriesRepository extends CrudRepository<Entries, Integer> {

}

