package com.nishi.cartracker.cartrackerrestapi.controller;

import com.nishi.cartracker.cartrackerrestapi.CarTrackerDAO;
import com.nishi.cartracker.cartrackerrestapi.repository.EntriesRepository;
import com.nishi.cartracker.cartrackerrestapi.entity.Entries;
import com.nishi.cartracker.cartrackerrestapi.entity.Vehicle;
import com.nishi.cartracker.cartrackerrestapi.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Optional;

@RestController
public class TestController {

    @Autowired
    CarTrackerDAO carTrackerDAO;

    @Autowired
    EntriesRepository entriesRepository;

    @Autowired
    VehicleRepository vehicleRepository;

    @GetMapping("/")
    public String hello() {
        return "Hello Spring Boot!";
    }

    @RequestMapping(path = "/guestName/{id}", method = RequestMethod.GET)
    public String getGuestName(@PathVariable("id") String id){
        return carTrackerDAO.getTaskNameById(id);
    }

    @RequestMapping(path = "/guestMessage/{id}", method = RequestMethod.GET)
    public Optional<Entries> getGuestMessage(@PathVariable("id") Integer id){
        return entriesRepository.findById(id);
    }

    @RequestMapping(path = "/entries", method = RequestMethod.POST, consumes = "application/json")
    public  @ResponseBody String addNewUser (@RequestBody Entries entry) {
       entriesRepository.save(entry);
       return "saved";
    }

    @CrossOrigin(origins = "http://mocker.ennate.academy")
    @RequestMapping(path="/vehicles", method = RequestMethod.PUT, consumes = "application/json")
    public @ResponseBody String addNewVehicles(@RequestBody ArrayList<Vehicle> vehicles){
        Iterator<Vehicle> vehicleIterator = vehicles.iterator();
        while (vehicleIterator.hasNext()) {
            vehicleRepository.save(vehicleIterator.next());
        }
        return "saved";
    }
}
