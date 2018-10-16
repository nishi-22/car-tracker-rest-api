package com.nishi.cartracker.cartrackerrestapi.controller;

import com.nishi.cartracker.cartrackerrestapi.CarTrackerDAO;
import com.nishi.cartracker.cartrackerrestapi.entity.GuestUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class TestController {

    @Autowired
    CarTrackerDAO carTrackerDAO;

    @GetMapping("/")
    public String hello() {
        return "Hello Spring Boot!";
    }

    @RequestMapping(path = "/guestName/{id}", method = RequestMethod.GET)
    public String getGuestName(@PathVariable("id") String id){
        return carTrackerDAO.getTaskNameById(id);
    }

}
