package com.controllers;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.models.Availability;
import com.services.ScheduleService;

@RestController(value ="/api-reservations/v3/providers")
public class ProvidersController {
    
    
    @Autowired
    private ScheduleService scheduleService;

//     - Allows providers to submit times they are available for appointments
//     - e.g. On Friday the 13th of August, Dr. Jekyll wants to work between 8am and 3pm
    @PostMapping(value = "/{providerId}/timeslots", consumes = "application/json")
    public ResponseEntity<String> postTimes(@PathVariable String providerId, @RequestBody Availability availability) {
            //edge cases: 
            if (!availability.isValid()) return ResponseEntity.badRequest().body("StartTime or endTime provided is empty");
            //if less than 15 mins between end - start 
            if (ChronoUnit.MINUTES.between(availability.getStartDateTime(), availability.getEndDateTime()) < 15) {
                return ResponseEntity.badRequest().body("EndTime is 15 minutes less than StartTime");
            }
            //Convert each UTC time to ensure date is in UTC
            availability.setEndDateTime(availability.getEndDateTime().withZoneSameInstant(ZoneId.of("UTC")));
            availability.setStartDateTime(availability.getStartDateTime().withZoneSameInstant(ZoneId.of("UTC")));
            //call scheduleService to persist available time slots 
            scheduleService.replaceTimeslots(providerId, availability);

            return ResponseEntity.ok().body("Success");
    } 


}
