package com.github.methodia.minibilling.mainlogic;

import com.github.methodia.minibilling.Measurement;
import com.github.methodia.minibilling.entity.Reading;
import com.github.methodia.minibilling.entity.User;

import java.math.BigDecimal;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.github.methodia.minibilling.Main.ZONE_ID;

public class MeasurementGenerator {

    public List<Measurement> generate(final User user, final List<Reading> readings) {
        final List<Measurement> measurements = new ArrayList<>();
        Reading previous = null;
        for (final Reading reading : readings) {
            if (null != previous) {
                final BigDecimal value = reading.getValue().subtract(previous.getValue());
                ZonedDateTime start = previous.getTime().withZoneSameInstant(ZoneId.of(ZONE_ID));
                ZonedDateTime end = reading.getTime().withZoneSameInstant(ZoneId.of(ZONE_ID));

                measurements.add(new Measurement(start,end, value, user));
            }
            previous = reading;
        }
        return measurements;
    }

}
