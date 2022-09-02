package com.github.methodia.minibilling;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MeasurementGenerator {

    private final User user;
    private final Collection<Reading> readings;

    public MeasurementGenerator(final User user, final Collection<Reading> readings) {
        this.user = user;
        this.readings = readings;
    }

    Collection<Measurement> generate() {
        final List<Measurement> measurements = new ArrayList<>();
        final List<Reading> previous = new ArrayList<>();
        for (final Reading reading : readings) {
            if (user.getRef().equals(reading.getUser().getRef())) {
                if (previous.isEmpty()) {
                    previous.add(reading);
                } else {
                    final BigDecimal value = reading.getValue().subtract(previous.get(0).getValue());
                    measurements.add(new Measurement(previous.get(0).getTime().toLocalDateTime(),
                            reading.getTime().toLocalDateTime(), value, user));
                    previous.set(0, reading);
                }
            }
        }
        if (measurements.isEmpty()) {
            throw new IllegalStateException();
        }
        return measurements;
    }

}
