package com.github.methodia.minibilling;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class MeasurementGenerator implements Measurements {

    public List<Measurement> generate(User user, List<Reading> readings) {

        List<Measurement> measurements = new ArrayList<>();
        final List<Reading> previous = new ArrayList<>();

        readings.forEach(reading -> {
            if (!previous.isEmpty()) {
                BigDecimal value = reading.getValue().subtract(previous.get(0).getValue());
                measurements.add(new Measurement(previous.get(0).getTime(), reading.getTime(), value, user));
                previous.set(0, reading);
            }
            previous.add(reading);

        });
        if (measurements.size()==0){
            throw new IllegalStateException();
        }

        return measurements;


    }


}