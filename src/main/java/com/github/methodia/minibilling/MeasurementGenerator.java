package com.github.methodia.minibilling;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class MeasurementGenerator {

    public List<Measurement> generate(User user, List<Reading> readings) {
        List<Measurement> measurements = new ArrayList<>();
        Reading previous = null;
        for (Reading reading : readings) {
            if (user.getRef().equals(reading.getUser().getRef())) {
                if (previous != null) {
                    BigDecimal value = reading.getValue().subtract(previous.getValue());
                    measurements.add(new Measurement(previous.getTime(), reading.getTime(), value, user));
                }
                previous = reading;
            }
        }
        return measurements;
    }

}
