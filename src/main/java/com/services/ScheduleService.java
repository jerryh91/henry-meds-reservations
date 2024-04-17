package com.services;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.models.ProviderAvailability;
import com.models.ProviderTimeslot;

@Service
public class ScheduleService {
    //Storing timeslots in local memory due to time
    //In production will connect with an external db through ORM like hibernate
    final List<ProviderTimeslot> timeslots = new ArrayList<>();

    //Assume overwrites all existing timeslots for providerId.
    public void replaceTimeslots(final ProviderAvailability availability) {
        //create 15 min block of time between startTime and endTime inclusive


    }
    
    public List<ZonedDateTime> getTimeslots() {

        //iterate each providers' reservation list
        //combine all and sort asc by time all that have no expiration date or expiration date in past based on current utc time
        //remove duplicate dateTimes with same startTime
        final ZonedDateTime currentTime = ZonedDateTime.now(ZoneId.of("UTC"));


    }
}