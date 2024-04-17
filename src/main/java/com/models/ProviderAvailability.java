
package com.models;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

public class ProviderAvailability {

    private Provider provider;
    
    private ZonedDateTime startDateTime;

    private ZonedDateTime endDateTime;

    public ProviderAvailability(Provider provider, ZonedDateTime startDateTime, ZonedDateTime endDateTime) {
        //TODO: Add null checks
        this.provider = provider;
        //Convert each dateTime to UTC
        this.startDateTime = startDateTime.withZoneSameInstant(ZoneId.of("UTC"));
        this.endDateTime = endDateTime.withZoneSameInstant(ZoneId.of("UTC"));
    }

    public ZonedDateTime getStartDateTime (){
        return this.startDateTime;
        
    }

    public ZonedDateTime getEndDateTime (){
        return this.endDateTime;
    }

    public Provider getProvider() {
        return this.provider;
    }

    public boolean isValid () {
        return ChronoUnit.MINUTES.between(this.startDateTime, this.endDateTime) >= 15;
    }
}
