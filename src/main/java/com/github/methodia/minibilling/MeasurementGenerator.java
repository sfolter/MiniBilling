package com.github.methodia.minibilling;

import java.math.BigDecimal;
import java.sql.ClientInfoStatus;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Miroslav Kovachev
 * 28.07.2022
 * Methodia Inc.
 */
public class MeasurementGenerator {

    private User user;
    private Collection<Reading> readings;

    public MeasurementGenerator(User user, Collection<Reading> readings) {
        this.user = user;
        this.readings = readings;
    }
        /**Calculating the quantity between every reading and adding it to list*/
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

