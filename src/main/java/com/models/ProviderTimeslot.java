package com.models;

import java.time.ZonedDateTime;

public class ProviderTimeslot implements Comparable<ProviderTimeslot>{
    private Provider provider;
    private ZonedDateTime startDateTime;
    private ZonedDateTime expireDateTime;
    
    public ProviderTimeslot(Provider provider, ZonedDateTime startDateTime, ZonedDateTime expireDateTime) {
        this.startDateTime = startDateTime;
        this.provider= provider;
        this.expireDateTime = expireDateTime;
    }

    public Provider getProvider() {
        return this.provider;
    }

    public ZonedDateTime getStartDateTime () {
        return this.startDateTime;
    }

    public ZonedDateTime getExpiredDateTime() {
        return this.expireDateTime;
    }

    public void setStartDateTime (ZonedDateTime startDateTime) {
         this.startDateTime = startDateTime;
    }
    
    public void setProvider (Provider provider) {
        this.provider = provider;
    }

    public void setExpiredTime (ZonedDateTime expiredDateTime) {
        this.expireDateTime = expiredDateTime;
    }


    @Override
    public int compareTo(ProviderTimeslot providerTimeslot) {
        return this.startDateTime.compareTo(providerTimeslot.startDateTime);
    }


}
