package com.henrymedstakehome.reservation.services;

import java.time.Duration;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


import org.springframework.stereotype.Service;

import com.henrymedstakehome.reservation.models.FormattedZonedDateTime;
import com.henrymedstakehome.reservation.models.ProviderAvailability;
import com.henrymedstakehome.reservation.models.ProviderTimeslot;
import com.henrymedstakehome.reservation.models.Reservation;

@Service
public class ScheduleService {
    //Storing timeslots in local memory due to time
    //In production will connect with an external db through ORM like hibernate
    List<ProviderTimeslot> timeslots = new ArrayList<>();
    private static final int SLOT_DURATION_MINUTES = 15;
    private static final int MINIMUM_NEXT_APPOINTMENT_HOURS = 24;

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
        while (currStartTime.plusMinutes(SLOT_DURATION_MINUTES).isEqual(endTime) ||
               currStartTime.plusMinutes(SLOT_DURATION_MINUTES).isBefore(endTime)) {
            this.timeslots.add(new ProviderTimeslot(providerAvailability.getProvider(), currStartTime, null));
            currStartTime = currStartTime.plusMinutes(SLOT_DURATION_MINUTES);
        }
        //sort asc to return to user later
        Collections.sort(timeslots);
    }
    
    public List<ZonedDateTime> getProviderTimeslots() {
       final ZonedDateTime earliestTimeslot = ZonedDateTime.now(ZoneId.of("UTC")).plusHours(MINIMUM_NEXT_APPOINTMENT_HOURS);
       return timeslots.stream()
       //first bookable timeslot is >= 24 hours currentTime
       .filter(t -> t.getStartDateTime().isEqual(earliestTimeslot) || t.getStartDateTime().isAfter(earliestTimeslot))
       .map(t -> t.getStartDateTime())
       //may be duplicate availabilities from multiple providers
       .distinct()
       .collect(Collectors.toList());
    }

    public Optional<Reservation> reserveProviderTimeslot(FormattedZonedDateTime startTime) {  
        final ZonedDateTime currentUTCTime = ZonedDateTime.now(ZoneId.of("UTC"));
       
        final ZonedDateTime startUTCTime = startTime.getStartDateTime().withZoneSameInstant(ZoneId.of("UTC"));
       //validate request start time is >= 24 hours from currentTime
       if (Duration.between(currentUTCTime, startUTCTime).toHours() < MINIMUM_NEXT_APPOINTMENT_HOURS) return Optional.empty();

        //find first doctor with this available time slot and update expireTime.
        Optional<ProviderTimeslot> timeslot = timeslots.stream()
        //** ensure this time slot is not already reserved by another user */
        .filter(t -> t.getStartDateTime().isEqual(startUTCTime) &&  (t.getExpiredDateTime() == null || currentUTCTime.isAfter(t.getExpiredDateTime())) )
        .findFirst();
        if (timeslot.isEmpty()) return Optional.empty();
        
        final ProviderTimeslot providerTimeslot = timeslot.get();

      // - Reservations expire after 30 minutes if not confirmed
        providerTimeslot.setExpiredTime(currentUTCTime.plusMinutes(30));

        return Optional.of(new Reservation(providerTimeslot.getProvider(), startTime));
     }

     public Optional<Reservation> bookProviderTimeslot(Reservation reservation) {
        final ZonedDateTime utcCurrentTime = ZonedDateTime.now(ZoneId.of("UTC"));

        //check current time is NOT past reservation expire time 
        Optional<ProviderTimeslot> timeslot = timeslots.stream()
        //** ensure this time slot with same provider, start time exists AND not past its expire time */
        .filter(t -> t.getStartDateTime().isEqual(reservation.getStartTime().getStartDateTime()) && (t.getProvider().equals(reservation.getProvider())) && t.getExpiredDateTime().isAfter(utcCurrentTime))
        .findFirst();
        if (timeslot.isEmpty()) return Optional.empty();
        //remove from available timeslots
        timeslots.remove(timeslot.get());
        return Optional.of(reservation);
     }  
}
