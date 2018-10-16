package com.nishi.cartracker.cartrackerrestapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CarTrackerDAO {

	@Autowired
    JdbcTemplate jdbcTemplate;
	
	public String getTaskNameById(String entryID) {
		return jdbcTemplate.queryForObject("SELECT content FROM entries WHERE entryID=?", new Object[] {entryID},String.class);
	}
}