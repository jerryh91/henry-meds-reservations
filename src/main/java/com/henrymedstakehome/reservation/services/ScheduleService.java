package com.henrymedstakehome.reservation.services;

import java.time.ZonedDateTime;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.henrymedstakehome.reservation.models.ProviderAvailability;
import com.henrymedstakehome.reservation.models.ProviderTimeslot;

@Service
public class ScheduleService {
    //Storing timeslots in local memory due to time
    //In production will connect with an external db through ORM like hibernate
    TreeSet<ProviderTimeslot> timeslots = new TreeSet<>();

    //Assume: overwrites all existing timeslots for providerId.
    public void replaceTimeslots(final ProviderAvailability providerAvailability) {
        //create 15 min block of time between startTime and endTime inclusive
        //remove all existing provider timeslots
        this.timeslots = timeslots.stream().filter(t -> t.getProvider().equals(providerAvailability.getProvider())).collect(Collectors 
                            .toCollection(TreeSet::new));
        ZonedDateTime currStartTime = providerAvailability.getStartDateTime();
        ZonedDateTime endTime = providerAvailability.getEndDateTime();
        while (currStartTime.plusMinutes(15).isEqual(endTime) ||
               currStartTime.plusMinutes(15).isBefore(endTime)) {
            this.timeslots.add(new ProviderTimeslot(providerAvailability.getProvider(), currStartTime, null));
            currStartTime = currStartTime.plusMinutes(15);
        }
    }
    
    // public List<ZonedDateTime> getTimeslots() {

    //     //iterate timeslots
    //     //remove duplicate dateTimes with same startTime
    //     //map starttime
    //     //filter only startTime >= 24 hours currentTIme
    //     final ZonedDateTime currentTime = ZonedDateTime.now(ZoneId.of("UTC"));


    // }
}
