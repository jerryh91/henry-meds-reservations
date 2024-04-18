
package com.henrymedstakehome.reservation.models;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ProviderAvailability {

    private Provider provider;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mmXXX")
    private ZonedDateTime startDateTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mmXXX")
    private ZonedDateTime endDateTime;

    public ProviderAvailability() {
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

    public void setProvider(Provider provider) {
         this.provider = provider;
    }

    public void setStartDateTime (ZonedDateTime startDateTime){
         this.startDateTime = startDateTime;
    }

    public void setEndDateTime (ZonedDateTime endDateTime){
         this.endDateTime = endDateTime;
    }

    public boolean isValid () {
        return ChronoUnit.MINUTES.between(this.startDateTime, this.endDateTime) >= 15;
    }
}
