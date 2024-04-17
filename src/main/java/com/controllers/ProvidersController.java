package com.controllers;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.models.Availability;
import com.models.ProviderAvailability;
import com.services.ScheduleService;

@RestController(value ="/api-reservations/v3/providers")
public class ProvidersController {
    
    
    @Autowired
    private ScheduleService scheduleService;

//     - Allows providers to submit times they are available for appointments
//     - e.g. On Friday the 13th of August, Dr. Jekyll wants to work between 8am and 3pm
    @PostMapping(value = "/{providerId}/timeslots", consumes = "application/json")
    public ResponseEntity<String> replaceTimelots(@PathVariable String providerId, @RequestBody ProviderAvailability providerAvailability) {
        if (!providerAvailability.isValid()) return ResponseEntity.badRequest().body("endTime is 15 minutes less than StartTime");
       
        //call scheduleService to persist available time slots 
        scheduleService.replaceTimeslots(providerAvailability);

        return ResponseEntity.ok().body("Success");
    } 


    @GetMapping(value = "/timeslots",  consumes = "application/json", produces = "application/json")
    public ResponseEntity<List<ZonedDateTime>> getTimeslots() {
        return ResponseEntity.ok().body(this.scheduleService.getTimeslots());
    }
}
