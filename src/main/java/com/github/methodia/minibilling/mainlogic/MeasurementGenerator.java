package com.github.methodia.minibilling.mainlogic;

import com.github.methodia.minibilling.Measurement;
import com.github.methodia.minibilling.entity.Reading;
import com.github.methodia.minibilling.entity.User;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class MeasurementGenerator {

    public List<Measurement> generate(final User user, final List<Reading> readings) {
        final List<Measurement> measurements = new ArrayList<>();
        Reading previous = null;
        for (final Reading reading : readings) {
            if (null != previous) {
                final BigDecimal value = reading.getValue().subtract(previous.getValue());
                measurements.add(new Measurement(previous.getTime(), reading.getTime(), value, user));
            }
            previous = reading;
        }
        return measurements;
    }

}
