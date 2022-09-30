package com.example.SpringBatchExample.generators;

import com.example.SpringBatchExample.Measurement;
import com.example.SpringBatchExample.Measurements;
import com.example.SpringBatchExample.models.Reading;
import com.example.SpringBatchExample.models.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class MeasurementGenerator implements Measurements {

    @Override
    public List<Measurement> generate(final User user, final List<Reading> readings) {

        final List<Measurement> measurements = new ArrayList<>();
        final List<Reading> previous = new ArrayList<>();

        readings.forEach(reading -> {
            if (!previous.isEmpty()) {
                final LocalDateTime current = LocalDateTime.from(previous.get(0).getDate());
                final LocalDateTime next = LocalDateTime.from(reading.getDate());
                final BigDecimal value = reading.getValue().subtract(previous.get(0).getValue());

                measurements.add(new Measurement(current, next, value, user));
                previous.set(0, reading);
            }
            previous.add(reading);
        });
        if (measurements.isEmpty()) {
            throw new IllegalStateException();
        }

        return measurements;


    }


}