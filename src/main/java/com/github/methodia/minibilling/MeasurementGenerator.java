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

   final private User user;
    final private List<Reading> readings;

    public MeasurementGenerator(User user, Collection<Reading> readings) {
        this.user = user;
        this.readings = readings.stream().toList();
    }

    /**
     * Calculating the quantity between every reading and adding it to list
     */
    public Collection<Measurement> generate() {
        List<Measurement> measurements = new ArrayList<>();
        if (readings.size() >= 2) {
            for (int i = 0; i < readings.size() - 1; i++) {
                Reading current = readings.get(i);
                Reading next = readings.get(i + 1);
                BigDecimal value = next.getValue().subtract(current.getValue());
                measurements.add(new Measurement(current.getTime(), next.getTime(), value, user));
            }
        }
        return measurements;
    }
}

