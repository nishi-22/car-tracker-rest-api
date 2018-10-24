package com.nishi.cartracker.cartrackerrestapi.controller;

import com.nishi.cartracker.cartrackerrestapi.entity.ChartReadings;
import com.nishi.cartracker.cartrackerrestapi.entity.VehicleLocations;
import com.nishi.cartracker.cartrackerrestapi.repository.ReadingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@CrossOrigin
public class ReadingsController {

    @Autowired
    ReadingsRepository readingsRepository;

    @RequestMapping(path = "/readings/vehicle/position/{vin}", method = RequestMethod.GET)
    public  @ResponseBody
    Collection<VehicleLocations> getVehiclePosition (@PathVariable("vin") String vin) {
        return readingsRepository.getVehiclePosition(vin);
    }

    @RequestMapping(path = "/readings/{vin}/{field}/{timeDuration}", method = RequestMethod.GET)
    public @ResponseBody
    Collection<ChartReadings> getVehicleReadingForChart(@PathVariable("vin") String vin,
                                                        @PathVariable("field") String field,
                                                        @PathVariable("timeDuration") Integer timeDuration){
        switch (field){
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
}
