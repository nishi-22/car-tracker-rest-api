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
    public @ResponseBody String getReadings(@RequestBody ReadingsRequest readingsRequest) throws ParseException {
        Optional<Vehicle> optionalVehicle = vehicleRepository.findById(readingsRequest.getVin());
        if(optionalVehicle.isPresent()){

            Readings readings = readingsRequest.getReadingsEntity();

            TireReadings tireReadings = readingsRequest.getTires();
            // saving readings to DB
            readingsRepository.save(readings);
            // saving tire Readings to DB
            tireReadings.setTireReadingId(readings.getTireReadingId());
            tireReadings.setVin(readings.getVin());
            tireReadingsRepository.save(tireReadings);
            Vehicle vehicle = optionalVehicle.get();
            ArrayList<Alerts> alerts = new ArrayList<>();
            try{
                // Rule: engineRpm > readlineRpm, Priority: HIGH
                if(readings.getEngineRpm() > vehicle.getRedlineRpm()){
                    alerts.add(new Alerts(UUID.randomUUID().toString(), readings.getVin(),
                            Rule.ENGINE_RPM, Priority.HIGH, readingsRequest.getTimestamp()));
                }
                // Rule: fuelVolume < 10% of maxFuelVolume, Priority: MEDIUM
                if (readings.getFuelVolume() < (0.1 * vehicle.getMaxFuelVolume())){
                    alerts.add(new Alerts(UUID.randomUUID().toString(), readings.getVin(),
                            Rule.FUEL_VOLUME, Priority.MEDIUM, readingsRequest.getTimestamp()));
                }
                // Rule: tire pressure of any tire < 32psi || > 36psi , Priority: LOW
                // Rule: engineCoolantLow = true || checkEngineLightOn = true, Priority: LOW
                if(Boolean.TRUE.equals(readings.getEngineCoolantLow())){
                    alerts.add(new Alerts(UUID.randomUUID().toString(), readings.getVin(),
                            Rule.ENGINE_COOLANT, Priority.LOW, readingsRequest.getTimestamp()));
                }
                if(Boolean.TRUE.equals(readings.getCheckEngineLightOn())){
                    alerts.add(new Alerts(UUID.randomUUID().toString(), readings.getVin(),
                            Rule.ENGINE_LIGHT, Priority.LOW, readingsRequest.getTimestamp()));
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
