package com.services;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.models.Availability;
import com.models.Reservation;

@Service
public class ScheduleService {
    //Storing timeslots in local memory due to time constraints
    //In production will connect with an external db through ORM like hibernate
    final Map<String,List<Reservation>> providerIdToTimeslots = new HashMap<>();

    //Assume overwrites all existing timeslots for providerId.
    public void replaceTimeslots(final String providerId, final Availability availability) {
      
    } 
    
    public List<ZonedDateTime> getTimeslots() {
        
    }
}
