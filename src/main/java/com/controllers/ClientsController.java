package com.controllers;

import java.time.ZonedDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.models.Reservation;

// - Allows a client to retrieve a list of available appointment slots
//     - Appointment slots are 15 minutes long
//default: next 5 days 

// - Allows clients to reserve an available appointment slot: 
    //update expiration time 30 mins after current time

// - Allows clients to confirm their reservation: remove from timeslot map

// Additional Requirements:

// - Reservations expire after 30 minutes if not confirmed and are again available for other clients to reserve that appointment slot
// - Reservations must be made at least 24 hours in advance
@RestController(value ="/api-reservations/v3/clients")
public class ClientsController {
    


    //reserve timeslot: 
    @PostMapping(value = "/reservations")
    public ResponseEntity<Reservation> reserveTimeslot(@RequestBody ZonedDateTime startTime) {
        //find first doctor with this available time slot and update expireTime.
        //if not available 
        //return provider data
    }


    @GetMapping(value = "/{}")

}
