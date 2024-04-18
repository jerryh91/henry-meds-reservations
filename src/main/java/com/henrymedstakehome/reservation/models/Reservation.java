package com.henrymedstakehome.reservation.models;

public class Reservation {

    private Provider provider;
    private FormattedZonedDateTime startTime;

    public Reservation(Provider provider, FormattedZonedDateTime startDateTime) {
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

    public FormattedZonedDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(FormattedZonedDateTime startTime) {
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
        return this.provider.equals(p.getProvider()) && this.startTime.getStartDateTime().isEqual(p.getStartTime().getStartDateTime());
    }
}
