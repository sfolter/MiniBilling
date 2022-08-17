package com.github.methodia.minibilling;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Miroslav Kovachev
 * 28.07.2022
 * Methodia Inc.
 */
public class MeasurementGenerator {


    Collection<Measurement> generate(User user, Collection<Reading> readings) {
        List<Measurement> measurements = new ArrayList<>();
        Reading previous = null;
        for (Reading reading : readings)
            if (user.getRef().equals(reading.getUser().getRef())) {
                if (previous != null) {
                    BigDecimal value = reading.getValue().subtract(previous.getValue());
                    measurements.add(new Measurement(previous.getTime().toLocalDateTime(), reading.getTime().toLocalDateTime(), value, user));
                }
                previous = reading;
            }
        return measurements;
    }
}
