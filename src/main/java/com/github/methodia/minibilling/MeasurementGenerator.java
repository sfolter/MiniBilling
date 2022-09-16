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
    public Collection<Measurement> generate(User user, List<Reading> readings) {
        List<Measurement> measurements = new ArrayList<>();
        if (readings.size() >= 2) {
            for (int i = 0; i < readings.size() - 1; i++) {
                Reading current = readings.get(i);
                Reading next = readings.get(i + 1);
                BigDecimal value = next.getValue().subtract(current.getValue());
                LocalDateTime currentToLdt = convertZdtToLdt(current.getDate());
                LocalDateTime nextToLdt = convertZdtToLdt(next.getDate());
                measurements.add(new Measurement(currentToLdt, nextToLdt, value, user));
            }
        }
        return measurements;
    }

    private LocalDateTime convertZdtToLdt(ZonedDateTime zdt) {
        return zdt.withZoneSameInstant(ZoneId.of("Z")).toLocalDateTime();
    }

}

