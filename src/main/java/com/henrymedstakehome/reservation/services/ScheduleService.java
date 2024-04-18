package com.henrymedstakehome.reservation.services;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.henrymedstakehome.reservation.models.ProviderAvailability;
import com.henrymedstakehome.reservation.models.ProviderTimeslot;
import com.henrymedstakehome.reservation.models.Reservation;

@Service
public class ScheduleService {
    //Storing timeslots in local memory due to time
    //In production will connect with an external db through ORM like hibernate
    List<ProviderTimeslot> timeslots = new ArrayList<>();

    //Assume: overwrites all existing timeslots for providerId.
    public void replaceTimeslots(final ProviderAvailability providerAvailability) {
        providerAvailability.setStartDateTime(providerAvailability.getStartDateTime().withZoneSameInstant(ZoneId.of("UTC")));
        providerAvailability.setEndDateTime(providerAvailability.getEndDateTime().withZoneSameInstant(ZoneId.of("UTC")));

        //remove all existing provider timeslots
        this.timeslots = timeslots.stream().filter(t -> !t.getProvider().equals(providerAvailability.getProvider())).collect(Collectors 
                            .toCollection(ArrayList::new));
        ZonedDateTime currStartTime = providerAvailability.getStartDateTime();
        final ZonedDateTime endTime = providerAvailability.getEndDateTime();

        //create 15 min block of time between startTime and endTime inclusive
        while (currStartTime.plusMinutes(15).isEqual(endTime) ||
               currStartTime.plusMinutes(15).isBefore(endTime)) {
            this.timeslots.add(new ProviderTimeslot(providerAvailability.getProvider(), currStartTime, null));
            currStartTime = currStartTime.plusMinutes(15);
        }
        //sort asc to return to user later
        Collections.sort(timeslots);
    }
    
    public List<ZonedDateTime> getProviderTimeslots() {
       final ZonedDateTime earliestTimeslot = ZonedDateTime.now(ZoneId.of("UTC")).plusHours(24);
       return timeslots.stream()
       //first bookable timeslot is >= 24 hours currentTime
       .filter(t -> t.getStartDateTime().isEqual(earliestTimeslot) || t.getStartDateTime().isAfter(earliestTimeslot))
       .map(t -> t.getStartDateTime())
       //may be duplicate availabilities from multiple providers
       .distinct()
       .collect(Collectors.toList());
    }

    public Optional<Reservation> reserveProviderTimeslot(ZonedDateTime startTime) {
        //find first doctor with this available time slot and update expireTime.
        //if not available return empty
        final ZonedDateTime currentTime = ZonedDateTime.now(ZoneId.of("UTC"));
        final ZonedDateTime utcStartTime = startTime.withZoneSameInstant(ZoneId.of("UTC"));

        Optional<ProviderTimeslot> timeslot = timeslots.stream()
        //** ensure this time slot is not already reserved by another user */
        .filter(t -> t.getStartDateTime().isEqual(utcStartTime) && (t.getExpiredDateTime() == null || currentTime.isAfter(t.getExpiredDateTime())))
        .findFirst();
        if (timeslot.isEmpty()) return Optional.empty();
        
        final ProviderTimeslot providerTimeslot = timeslot.get();
        providerTimeslot.setExpiredTime(currentTime.plusMinutes(30));

        return Optional.of(new Reservation(providerTimeslot.getProvider(), startTime));
     }

     public Optional<Reservation> bookProviderTimeslot(Reservation reservation) {
        final ZonedDateTime utcCurrentTime = ZonedDateTime.now(ZoneId.of("UTC"));

        //check current time is NOT past reservation expire time 
        Optional<ProviderTimeslot> timeslot = timeslots.stream()
        //** ensure this time slot with same provider, start time exists AND not past its expire time */
        .filter(t -> t.getStartDateTime().isEqual(reservation.getStartTime()) && (t.getProvider().equals(reservation.getProvider())) && t.getExpiredDateTime().isAfter(utcCurrentTime))
        .findFirst();
        if (timeslot.isEmpty()) return Optional.empty();
        //remove from available timeslots
        timeslots.remove(timeslot.get());
        return Optional.of(reservation);
     }  
}
