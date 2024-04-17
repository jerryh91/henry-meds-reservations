package com.models;

import java.time.ZonedDateTime;

public class Reservation {

    private Provider provider;
    
    private ZonedDateTime startTime;

    //duration 15 mins
    
    //null: never booked
    private ZonedDateTime expireTime;

}
