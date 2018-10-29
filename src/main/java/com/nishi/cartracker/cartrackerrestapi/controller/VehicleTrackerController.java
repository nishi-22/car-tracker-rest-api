package com.nishi.cartracker.cartrackerrestapi.controller;

import com.nishi.cartracker.cartrackerrestapi.CarTrackerDAO;
import com.nishi.cartracker.cartrackerrestapi.entity.*;
import com.nishi.cartracker.cartrackerrestapi.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.*;

@RestController
@CrossOrigin
public class VehicleTrackerController {

    @Autowired
    CarTrackerDAO carTrackerDAO;

    @Autowired
    EntriesRepository entriesRepository;

    @Autowired
    VehicleRepository vehicleRepository;

    @Autowired
    AlertsRepository alertsRepository;

    @Autowired
    ReadingsRepository readingsRepository;

    @Autowired
    TireReadingsRepository tireReadingsRepository;

    @GetMapping("/")
    public String hello() {
        return "Hello Spring Boot!";
    }

    @RequestMapping(path="/vehicles", method = RequestMethod.GET)
    public @ResponseBody
    Collection<VehicleTable> getVehicleList(){
        return vehicleRepository.getVehicleTableData();
    }

    @RequestMapping(path="/vehicle/{vin}", method = RequestMethod.GET)
    public @ResponseBody
    Optional<Vehicle> getVehicleById(@PathVariable("vin") String vin){
        return vehicleRepository.findById(vin);
    }

    @RequestMapping(path="/vehicles", method = RequestMethod.PUT, consumes = "application/json")
    public @ResponseBody String addNewVehicles(@RequestBody ArrayList<Vehicle> vehicles){
        Iterator<Vehicle> vehicleIterator = vehicles.iterator();
        while (vehicleIterator.hasNext()) {
            vehicleRepository.save(vehicleIterator.next());
        }
        return "saved";
    }

    @RequestMapping(path = "/alerts/{vin}", method = RequestMethod.GET)
    public @ResponseBody ArrayList<Alerts> getAlertsByVIN(@PathVariable("vin") String vin){
        return alertsRepository.getAlertsByVIN(vin);
    }
}
