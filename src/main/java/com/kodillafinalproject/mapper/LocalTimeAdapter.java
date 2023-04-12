package com.kodillafinalproject.mapper;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class LocalTimeAdapter extends TypeAdapter<LocalTime> {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm");

    @Override
    public void write(JsonWriter out, LocalTime time) throws IOException {
        out.value(formatter.format(time));
    }

    @Override
    public LocalTime read(JsonReader in) throws IOException {
        String dateString = in.nextString();
        return LocalTime.parse(dateString, formatter);
    }
}
