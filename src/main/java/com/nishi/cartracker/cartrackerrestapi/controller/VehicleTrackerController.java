package com.nishi.cartracker.cartrackerrestapi.controller;

import com.nishi.cartracker.cartrackerrestapi.CarTrackerDAO;
import com.nishi.cartracker.cartrackerrestapi.entity.*;
import com.nishi.cartracker.cartrackerrestapi.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Optional;
import java.util.UUID;

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

    @RequestMapping(path="/vehicles", method = RequestMethod.GET)
    public @ResponseBody Iterable<Vehicle> getVehicleList(){
        return vehicleRepository.findAll();
    }

    @RequestMapping(path="/vehicles", method = RequestMethod.PUT, consumes = "application/json")
    public @ResponseBody String addNewVehicles(@RequestBody ArrayList<Vehicle> vehicles){
        Iterator<Vehicle> vehicleIterator = vehicles.iterator();
        while (vehicleIterator.hasNext()) {
            vehicleRepository.save(vehicleIterator.next());
        }
        return "saved";
    }

    @RequestMapping(path="/readings", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody String getReadings(@RequestBody Readings readings) throws ParseException {
        Optional<Vehicle> optionalVehicle = vehicleRepository.findById(readings.getVin());
        String readingsId = UUID.randomUUID().toString();
        String tireReadingId = UUID.randomUUID().toString();
        if(optionalVehicle.isPresent()){

            readings.setReadingId(readingsId);
            readings.setTireReadingId(tireReadingId);

            TireReadings tireReadings = new TireReadings();
            tireReadings.setFrontLeft(readings.getTireReadings().getFrontLeft());
            tireReadings.setFrontRight(readings.getTireReadings().getFrontRight());
            tireReadings.setRearLeft(readings.getTireReadings().getRearLeft());
            tireReadings.setRearRight(readings.getTireReadings().getRearRight());

            // saving readings to DB

            readingsRepository.save(readings);
            // saving tire Readings to DB
            tireReadings.setTireReadingId(tireReadingId);
            tireReadingsRepository.save(tireReadings);
            Vehicle vehicle = optionalVehicle.get();
            ArrayList<Alerts> alerts = new ArrayList<>();
            try{
                // Rule: engineRpm > readlineRpm, Priority: HIGH
                if(readings.getEngineRpm() > vehicle.getRedlineRpm()){
                    Alerts alert = new Alerts();
                    alert.setVin(readings.getVin());
                    alert.setAlertId(UUID.randomUUID().toString());
                    alert.setRule(Rule.ENGINE_RPM);
                    alert.setPriority(Priority.HIGH);
                    alert.setAlertTime(readings.getTimestamp());
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
                    alert.setAlertTime(readings.getTimestamp());
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
                    alert.setAlertTime(readings.getTimestamp());
                    alerts.add(alert);
                }
                if(Boolean.TRUE.equals(readings.getCheckEngineLightOn())){
                    Alerts alert = new Alerts();
                    alert.setVin(readings.getVin());
                    alert.setAlertId(UUID.randomUUID().toString());
                    alert.setRule(Rule.ENGINE_LIGHT);
                    alert.setPriority(Priority.LOW);
                    alert.setAlertTime(readings.getTimestamp());
                    alerts.add(alert);
                }
                Iterator<Alerts> alertsIterator = alerts.iterator();
                while (alertsIterator.hasNext()) {
                    alertsRepository.save(alertsIterator.next());
                }
                return "saved";
            }catch (Exception e){
                return e.toString();
            }
        }
        return "404";

    }
}
