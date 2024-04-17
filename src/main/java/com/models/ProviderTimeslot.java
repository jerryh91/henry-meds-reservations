package com.models;

import java.time.ZonedDateTime;

public class ProviderTimeslot {
    private Provider provider;
    private ZonedDateTime startDateTime;
    private ZonedDateTime expireTime;
    
    public ProviderTimeslot(Provider provider, ZonedDateTime startDateTime, ZonedDateTime expireTime) {
        this.startDateTime = startDateTime;
        this.provider= provider;
        this.expireTime = expireTime;
    }

    public Provider getProvider() {
        return this.provider;
    }

    public ZonedDateTime getStartDateTime () {
        return this.startDateTime;
    }

    public void setStartDateTime (ZonedDateTime startDateTime) {
         this.startDateTime = startDateTime;
    }
    
    public void setProvider (Provider provider) {
        this.provider = provider;
   }
}
