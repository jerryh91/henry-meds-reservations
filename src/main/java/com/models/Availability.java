package com.models;

import java.time.ZonedDateTime;

public class Availability {
    private Provider provider;
    private ZonedDateTime startDateTime;
    private ZonedDateTime endDateTime;
    
    public Availability(Provider provider, ZonedDateTime startDateTime, ZonedDateTime endDateTime) {
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.provider= provider;
    }

    public Provider getProvider() {
        return this.provider;
    }

    public ZonedDateTime getStartDateTime () {
        return this.startDateTime;
    }

    public ZonedDateTime getEndDateTime () {
        return this.endDateTime;
    }

    public void setStartDateTime (ZonedDateTime startDateTime) {
         this.startDateTime = startDateTime;
    }

    public void setEndDateTime (ZonedDateTime endDateTime) {
         this.endDateTime = endDateTime;
    }

    public void setProvider (Provider provider) {
        this.provider = provider;
   }

    public boolean isValid () {
        return this.endDateTime != null && this.startDateTime != null;
    }
}
