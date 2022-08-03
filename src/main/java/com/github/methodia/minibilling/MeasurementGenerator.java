package com.github.methodia.minibilling;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MeasurementGenerator {
    private User user;
    private Collection<Reading> readings;

    public MeasurementGenerator(User user, Collection<Reading> readings) {
        this.user = user;
        this.readings = readings;
    }

    Collection<Measurement> generate() {
        List<Measurement> measurements = new ArrayList<>();
        List<Reading> previous = new ArrayList<>();
        for (Reading reading : readings) {
            if (user.getRef().equals(reading.getUser().getRef())) {

                if (previous.isEmpty()) {
                    previous.add(reading);
                } else {
                    BigDecimal value = reading.getValue().subtract(previous.get(0).getValue());
                    measurements.add(new Measurement(previous.get(0).getTime(), reading.getTime(), value, user));
                    previous.set(0, reading);
                }
            }
        }
        return measurements;
    }

}
