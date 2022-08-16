package com.github.methodia.minibilling;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MeasurementGenerator {
    private final User user;
    private final List<Reading> readings;

    public MeasurementGenerator(User user, List<Reading> readings) {
        this.user = user;
        this.readings = readings;
    }

    public List<Measurement> generate() {
        List<Measurement> measurements = new ArrayList<>();
        Reading previous = null;
        for (Reading reading : readings) {
            if (user.getRef().equals(reading.getUser().getRef())) {
                if (previous == null) {
                    previous = reading;
                } else {
                    BigDecimal value = reading.getValue().subtract(previous.getValue());
                    measurements.add(new Measurement(previous.getTime(), reading.getTime(), value, user));
                    previous = reading;
                }
            }
        }
        return measurements;
    }

}
