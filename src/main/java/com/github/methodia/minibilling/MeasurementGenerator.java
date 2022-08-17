package com.github.methodia.minibilling;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class MeasurementGenerator {
    public List<Measurement> generate(User user, List<Reading> readings) {

        List<Measurement> measurements = new ArrayList<>();
        final List<Reading> previous = new ArrayList<>();

        readings.forEach(r -> {
            if (previous.isEmpty()) {
                previous.add(r);
            } else {
                BigDecimal value = r.getValue().subtract(previous.get(0).getValue());
                measurements.add(new Measurement(previous.get(0).getTime(), r.getTime(), value, user));
                previous.set(0, r);
            }

        });

        return measurements;


    }


}