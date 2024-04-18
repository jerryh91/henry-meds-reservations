package com.henrymedstakehome.reservation.models;

import java.time.ZonedDateTime;

public class Reservation {

    private Provider provider;
    private ZonedDateTime startTime;

    public Reservation() {}

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public ZonedDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(ZonedDateTime startTime) {
        this.startTime = startTime;
    }
    
}
