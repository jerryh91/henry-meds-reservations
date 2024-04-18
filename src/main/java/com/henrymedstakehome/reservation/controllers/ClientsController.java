package com.henrymedstakehome.reservation.controllers;

import java.time.ZonedDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.henrymedstakehome.reservation.models.ProviderAvailability;
import com.henrymedstakehome.reservation.services.ScheduleService;

// - Allows clients to reserve an available appointment slot: 
    //update expiration time 30 mins after current time

// - Allows clients to confirm their reservation: remove from timeslot map

// Additional Requirements:

// - Reservations expire after 30 minutes if not confirmed and are again available for other clients to reserve that appointment slot
// - Reservations must be made at least 24 hours in advance
@RestController(value ="/api-reservations/v3/clients")
public class ClientsController {

    @Autowired
    private ScheduleService scheduleService;

// - Allows a client to retrieve a list of available appointment slots
// - Appointment slots are 15 minutes long
// default: next 5 days 

    @GetMapping(value = "/timeslots",  consumes = "application/json", produces = "application/json")
    public ResponseEntity<List<ZonedDateTime>> getTimeslots() {
        return ResponseEntity.ok().body(this.scheduleService.getProviderTimeslots());
    }

    // //reserve timeslot: 
    // @PostMapping(value = "/reservations")
    // public ResponseEntity<Reservation> reserveTimeslot(@RequestBody ZonedDateTime startTime) {
    //     //find first doctor with this available time slot and update expireTime.
    //     //if not available 
    //     //return provider data
    // }


    @GetMapping(value = "/{}", produces = "application/json")
    public ResponseEntity<String> replaceTimelots( @RequestBody ProviderAvailability providerAvailability) {
     
    }

    
}
