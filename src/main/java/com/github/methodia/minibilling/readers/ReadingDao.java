package com.github.methodia.minibilling.readers;

import com.github.methodia.minibilling.entity.Reading;
import org.hibernate.Session;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.github.methodia.minibilling.Main.ZONE_ID;

public class ReadingDao implements ReadingsReader {

    private Session session;

    public ReadingDao(final Session session) {
        this.session = session;
    }


    @Override
    public Map<String, List<Reading>> read() {

        List<Reading> readingsDao = session.createQuery("from Reading ", Reading.class).getResultList();
        return readingsDao.stream().map(this::setTime)
                .collect(Collectors.groupingBy(reading -> reading.getUser().getRef()));

    }

    private Reading setTime(Reading reading) {
        ZonedDateTime withZoneSameInstant = reading.getTime().withZoneSameInstant(ZoneId.of(ZONE_ID));
        reading.setTime(withZoneSameInstant);
        return reading;
    }
}
