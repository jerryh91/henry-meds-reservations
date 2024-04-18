package com.henrymedstakehome.reservation.controllers;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.henrymedstakehome.reservation.models.FormattedZonedDateTime;
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
    public ResponseEntity<Reservation> reserveTimeslot(@RequestBody FormattedZonedDateTime startTime) {
       Optional<Reservation> reservation = this.scheduleService.reserveProviderTimeslot( startTime);
       if (reservation.isPresent()) {
        return ResponseEntity.ok().body(reservation.get());
       } else {
        return ResponseEntity.notFound().build();
       }
    }

    // - Allows clients to confirm their reservation:
    @PostMapping(value = "/bookings")
    public ResponseEntity<Reservation> bookTimeslot(@RequestBody Reservation reservation) {
       Optional<Reservation> confirmedReservation = this.scheduleService.bookProviderTimeslot(reservation);
       if (confirmedReservation.isPresent()) {
        return ResponseEntity.ok().body(confirmedReservation.get());
       } else {
        return ResponseEntity.notFound().build();
       }
    }

}
