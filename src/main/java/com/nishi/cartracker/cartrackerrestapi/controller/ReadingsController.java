package com.nishi.cartracker.cartrackerrestapi.controller;

import com.nishi.cartracker.cartrackerrestapi.entity.Readings;
import com.nishi.cartracker.cartrackerrestapi.repository.ReadingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@CrossOrigin
public class ReadingsController {

    @Autowired
    ReadingsRepository readingsRepository;

    @RequestMapping(path = "/readings/vehicle/position/{vin}", method = RequestMethod.GET)
    public  @ResponseBody
    ArrayList<Readings> getVehiclePosition (@PathVariable("vin") String vin) {
        return readingsRepository.getVehiclePosition(vin);
    }
}
