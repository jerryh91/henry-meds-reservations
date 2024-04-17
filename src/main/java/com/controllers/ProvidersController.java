package com.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController(value ="/api-reservations/v3/providers")
public class ProvidersController {
    
    @Autowired
    private ScheduleService scheduleService;

//     - Allows providers to submit times they are available for appointments
//     - e.g. On Friday the 13th of August, Dr. Jekyll wants to work between 8am and 3pm
    @PostMapping(value = "/{providerId}/timeslots", consumes = "application/json")
    public void postTimes(@PathVariable String providerId, @RequestBody List<> timeslots) {

    } 


}
