package com.henrymedstakehome.reservation.services;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.henrymedstakehome.reservation.models.ProviderAvailability;
import com.henrymedstakehome.reservation.models.ProviderTimeslot;

@Service
public class ScheduleService {
    //Storing timeslots in local memory due to time
    //In production will connect with an external db through ORM like hibernate
    List<ProviderTimeslot> timeslots = new ArrayList<>();

    //Assume: overwrites all existing timeslots for providerId.
    public void replaceTimeslots(final ProviderAvailability providerAvailability) {
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
}
