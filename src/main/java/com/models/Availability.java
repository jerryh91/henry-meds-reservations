package com.models;

import java.time.ZonedDateTime;

public class Availability {
    
    private ZonedDateTime startDateTime;
    private ZonedDateTime endDateTime;
    
    public Availability(ZonedDateTime startDateTime, ZonedDateTime endDateTime) {
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
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


    public boolean isValid () {
        return this.endDateTime != null && this.startDateTime != null;
    }
}
