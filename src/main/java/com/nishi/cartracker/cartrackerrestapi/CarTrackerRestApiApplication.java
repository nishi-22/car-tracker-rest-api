package com.nishi.cartracker.cartrackerrestapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class CarTrackerRestApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(CarTrackerRestApiApplication.class, args);
	}

	@GetMapping("/")
	public String hello() {
		return "Hello Spring Boot!";
	}
}
