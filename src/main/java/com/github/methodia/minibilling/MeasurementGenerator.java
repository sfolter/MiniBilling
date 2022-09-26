package com.github.methodia.minibilling;

import com.github.methodia.minibilling.entityClasses.Reading;
import com.github.methodia.minibilling.entityClasses.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MeasurementGenerator {

    /**
     * Calculating the quantity between every reading and adding it to list
     */
    public Collection<Measurement> generate(final User user, final List<Reading> readings) {
        final List<Measurement> measurements = new ArrayList<>();
        if (2 <= readings.size()) {
            for (int i = 0; i < readings.size() - 1; i++) {
                final Reading current = readings.get(i);
                final Reading next = readings.get(i + 1);
                final BigDecimal value = next.getValue().subtract(current.getValue());
                final LocalDateTime currentToLdt = convertZdtToLdt(current.getDate());
                final LocalDateTime nextToLdt = convertZdtToLdt(next.getDate());
                measurements.add(new Measurement(currentToLdt, nextToLdt, value, user));
            }
        }
        return measurements;
    }

    private LocalDateTime convertZdtToLdt(final ZonedDateTime zdt) {
        return zdt.withZoneSameInstant(ZoneId.of("Z")).toLocalDateTime();
    }

}

