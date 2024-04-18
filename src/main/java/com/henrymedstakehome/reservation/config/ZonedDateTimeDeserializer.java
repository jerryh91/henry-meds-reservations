package com.henrymedstakehome.reservation.config;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class ZonedDateTimeDeserializer extends JsonDeserializer<ZonedDateTime> {

    private static final DateTimeFormatter FORMATTER = new DateTimeFormatterBuilder()
            .parseCaseInsensitive()
            .appendPattern("yyyy-MM-dd'T'HH:mmXXX")
            .toFormatter();


    @Override
    public ZonedDateTime deserialize(JsonParser jsonParser, DeserializationContext ctxt) throws IOException, JacksonException {
        String dateString = jsonParser.getText();
        return ZonedDateTime.parse(dateString, FORMATTER);
    }
}
