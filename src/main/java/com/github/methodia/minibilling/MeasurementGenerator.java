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


    Collection<Measurement> generate(final User user, final Collection<Reading> readings) {
        final List<Measurement> measurements = new ArrayList<>();
        Reading previous = null;
        for (final Reading reading : readings) {
            if (user.ref().equals(reading.user().ref())) {
                if (null != previous) {
                    final BigDecimal value = reading.value().subtract(previous.value());
                    measurements.add(
                            new Measurement(previous.time().toLocalDateTime(), reading.time().toLocalDateTime(), value,
                                    user));
                }
                previous = reading;
            }
        }
        return measurements;
    }
}
