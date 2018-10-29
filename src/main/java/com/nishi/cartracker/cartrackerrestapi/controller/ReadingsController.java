package com.nishi.cartracker.cartrackerrestapi.controller;

import com.nishi.cartracker.cartrackerrestapi.entity.*;
import com.nishi.cartracker.cartrackerrestapi.repository.AlertsRepository;
import com.nishi.cartracker.cartrackerrestapi.repository.ReadingsRepository;
import com.nishi.cartracker.cartrackerrestapi.repository.TireReadingsRepository;
import com.nishi.cartracker.cartrackerrestapi.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.*;

@RestController
@CrossOrigin
public class ReadingsController {

    @Autowired
    ReadingsRepository readingsRepository;

    @Autowired
    TireReadingsRepository tireReadingsRepository;

    @Autowired
    AlertsRepository alertsRepository;

    @Autowired
    VehicleRepository vehicleRepository;

    @RequestMapping(path = "/readings/vehicle/position/{vin}", method = RequestMethod.GET)
    public @ResponseBody
    Collection<VehicleLocations> getVehiclePosition(@PathVariable("vin") String vin) {
        return readingsRepository.getVehiclePosition(vin);
    }

    @RequestMapping(path = "/readings/{vin}/{field}/{timeDuration}", method = RequestMethod.GET)
    public @ResponseBody
    Collection<ChartReadings> getVehicleReadingForChart(@PathVariable("vin") String vin,
                                                        @PathVariable("field") String field,
                                                        @PathVariable("timeDuration") Integer timeDuration) {
        switch (field) {
            case "fuelVolume":
                return readingsRepository.getFuelVolumeReadings(vin, timeDuration);
            case "engineRpm":
                return readingsRepository.getEngineRPMReadings(vin, timeDuration);
            case "engineHp":
                return readingsRepository.getEngineHpReadings(vin, timeDuration);
            case "speed":
                return readingsRepository.getSpeedReadings(vin, timeDuration);
            default:
                return null;
        }
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
