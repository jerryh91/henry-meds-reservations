package com.henrymedstakehome.reservation.models;

import java.time.ZonedDateTime;

public class Reservation {

    private Provider provider;
    private ZonedDateTime startTime;

    public Reservation(Provider provider, ZonedDateTime startDateTime) {
        this.provider = provider;
        this.startTime = startDateTime;
    }

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
    

    @Override
    public boolean equals(Object o) {
         if (o == this) {
            return true;
        }

        if (!(o instanceof Reservation)) {
            return false;
        }
         
        Reservation p = (Reservation) o;
        return this.provider.equals(p.getProvider()) && this.startTime.isEqual(p.getStartTime());
    }
}
