package com.github.methodia.minibilling;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MeasurementGenerator {

    //   final private User user;
    //    final private
    //    public MeasurementGenerator(User user, Collection<Reading> readings) {
    //        this.user = user;
    //        this.readings = readings.stream().toList();
    //    }

    /**
     * Calculating the quantity between every reading and adding it to list
     */
    public Collection<Measurement> generate(User user, List<Reading> readings) {
        List<Measurement> measurements = new ArrayList<>();
        if (readings.size() >= 2) {
            for (int i = 0; i < readings.size() - 1; i++) {
                Reading current = readings.get(i);
                Reading next = readings.get(i + 1);
                BigDecimal value = next.value().subtract(current.value());
                measurements.add(new Measurement(current.time(), next.time(), value, user));
            }
        }
        return measurements;
    }
}

