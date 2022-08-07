package com.github.methodia.minibilling;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Miroslav Kovachev
 * 28.07.2022
 * Methodia Inc.
 */
public class MeasurementGenerator {
    private User user;
    private List<Reading> readings;

    public MeasurementGenerator(User user, List<Reading> readings) {
        this.user = user;
        this.readings = readings;
    }

    Collection<Measurement> generate()
    {
        List<Measurement> measurements = new ArrayList<>();

        for (int i = 0; i <readings.size()-1 ; i++) {
            Reading earlierReading=readings.stream().toList().get(i);
            Reading laterReading=readings.stream().toList().get(i+1);
            BigDecimal value= laterReading.getValue().subtract(earlierReading.getValue());
            measurements.add(new Measurement(earlierReading.getTime(),laterReading.getTime(),value,user));
        }
        return measurements;
    }
}
