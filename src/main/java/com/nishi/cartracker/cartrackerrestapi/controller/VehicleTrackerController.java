package com.nishi.cartracker.cartrackerrestapi.controller;

import com.nishi.cartracker.cartrackerrestapi.CarTrackerDAO;
import com.nishi.cartracker.cartrackerrestapi.entity.*;
import com.nishi.cartracker.cartrackerrestapi.repository.AlertsRepository;
import com.nishi.cartracker.cartrackerrestapi.repository.EntriesRepository;
import com.nishi.cartracker.cartrackerrestapi.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Optional;
import java.util.UUID;

@RestController
public class VehicleTrackerController {

    @Autowired
    CarTrackerDAO carTrackerDAO;

    @Autowired
    EntriesRepository entriesRepository;

    @Autowired
    VehicleRepository vehicleRepository;

    @Autowired
    AlertsRepository alertsRepository;

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

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

    @CrossOrigin(origins = "http://mocker.ennate.academy")
    @RequestMapping(path="/readings", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody String getReadings(@RequestBody Readings readings) throws ParseException {
        Vehicle vehicle = vehicleRepository.findVehicleByVin(readings.getVin());
        ArrayList<Alerts> alerts = new ArrayList<>();
        Timestamp timeStamp = new java.sql.Timestamp(dateFormat.parse(readings.getTimestamp()).getTime());
        // Rule: engineRpm > readlineRpm, Priority: HIGH
        if(readings.getEngineRpm() > vehicle.getRedlineRpm()){
            Alerts alert = new Alerts();
            alert.setVin(readings.getVin());
            alert.setAlertId(UUID.randomUUID().toString());
            alert.setRule(Rule.ENGINE_RPM);
            alert.setPriority(Priority.HIGH);
            alert.setAlertTime(timeStamp);
            alerts.add(alert);
            // alerts.add(new Alerts(UUID.randomUUID().toString(), readings.getVin(), Rule.ENGINE_RPM, Priority.HIGH));
        }
        // Rule: fuelVolume < 10% of maxFuelVolume, Priority: MEDIUM
        if (readings.getFuelVolume() < (0.1 * vehicle.getMaxFuelVolume())){
            Alerts alert = new Alerts();
            alert.setVin(readings.getVin());
            alert.setAlertId(UUID.randomUUID().toString());
            alert.setRule(Rule.FUEL_VOLUME);
            alert.setPriority(Priority.HIGH);
            alert.setAlertTime(timeStamp);
            alerts.add(alert);
        }
        // Rule: tire pressure of any tire < 32psi || > 36psi , Priority: LOW
        // Rule: engineCoolantLow = true || checkEngineLightOn = true, Priority: LOW
        if(Boolean.TRUE.equals(readings.getEngineCoolantLow())){
            Alerts alert = new Alerts();
            alert.setVin(readings.getVin());
            alert.setAlertId(UUID.randomUUID().toString());
            alert.setRule(Rule.ENGINE_COOLANT);
            alert.setPriority(Priority.LOW);
            alert.setAlertTime(timeStamp);
            alerts.add(alert);
        }
        if(Boolean.TRUE.equals(readings.getCheckEngineLightOn())){
            Alerts alert = new Alerts();
            alert.setVin(readings.getVin());
            alert.setAlertId(UUID.randomUUID().toString());
            alert.setRule(Rule.ENGINE_LIGHT);
            alert.setPriority(Priority.LOW);
            alert.setAlertTime(timeStamp);
            alerts.add(alert);
        }
        Iterator<Alerts> alertsIterator = alerts.iterator();
        while (alertsIterator.hasNext()) {
            alertsRepository.save(alertsIterator.next());
        }
        return "saved";
    }
}
