package com.henrymedstakehome.reservation.controllers;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.henrymedstakehome.reservation.models.ProviderAvailability;
import com.henrymedstakehome.reservation.models.Reservation;
import com.henrymedstakehome.reservation.services.ScheduleService;

@RestController
@RequestMapping("/api-reservations/v3/providers")
public class ProvidersController {
    
    
    @Autowired
    private ScheduleService scheduleService;

//     - Allows providers to submit times they are available for appointments
//     - e.g. On Friday the 13th of August, Dr. Jekyll wants to work between 8am and 3pm
    @PostMapping(value = "/timeslots", consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> replaceTimelots( @RequestBody ProviderAvailability providerAvailability) {
        if (!providerAvailability.isValid()) return ResponseEntity.badRequest().body("endTime is 15 minutes less than StartTime");
       
        providerAvailability.setStartDateTime(providerAvailability.getStartDateTime().withZoneSameInstant(ZoneId.of("UTC")));
        providerAvailability.setEndDateTime(providerAvailability.getEndDateTime().withZoneSameInstant(ZoneId.of("UTC")));

        scheduleService.replaceTimeslots(providerAvailability);

        return ResponseEntity.ok().body("Success");
    } 

    // - Allows a client to retrieve a list of available appointment slots
    // - Appointment slots are 15 minutes long
    // Reservations must be made at least 24 hours in advance: so don't return available timeslots < 24 hrs from request time
    @GetMapping(value = "/timeslots", produces = "application/json")
    public ResponseEntity<List<ZonedDateTime>> getTimeslots() {
        return ResponseEntity.ok().body(this.scheduleService.getProviderTimeslots());
    }

    // - Allows clients to reserve an available appointment slot: 
    // - Reservations expire after 30 minutes if not confirme
    // Reservations must be made at least 24 hours in advance: validation

    //update expiration time 30 mins after current time
    @PostMapping(value = "/reservations")
    public ResponseEntity<Reservation> reserveTimeslot(@RequestBody ZonedDateTime startTime) {
        //find first doctor with this available time slot and update expireTime.
        //if not available 
        //return provider data
    }



    // - Allows clients to confirm their reservation: remove from timeslot map

}
