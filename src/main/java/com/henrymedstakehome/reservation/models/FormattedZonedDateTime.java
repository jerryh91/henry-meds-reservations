package com.henrymedstakehome.reservation.models;

import java.time.ZonedDateTime;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.henrymedstakehome.reservation.config.ZonedDateTimeDeserializer;

public class FormattedZonedDateTime {
    
    @JsonDeserialize(using = ZonedDateTimeDeserializer.class)
    private ZonedDateTime startDateTime;

    public FormattedZonedDateTime (ZonedDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public FormattedZonedDateTime () {}

    public ZonedDateTime getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(ZonedDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }
    
}
