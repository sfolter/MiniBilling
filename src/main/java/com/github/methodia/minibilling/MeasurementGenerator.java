package com.github.methodia.minibilling;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
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
    private Collection<Reading> readings;

    Collection<Measurement> generate() {
        List<Measurement> measurements = new ArrayList<>();
        List<Reading> readingsList = CSVReadingsReader.getReadingsList();
        for (int i = 0; i < readingsList.size()/2; i++) {
            for (int j = i+1; j < readingsList.size(); j++) {
                if(readingsList.get(i).getUser().equals(readingsList.get(j).getUser())){
                    BigDecimal value = readingsList.get(j).getValue().subtract(readingsList.get(i).getValue());
                    LocalDateTime startDate = readingsList.get(i).getTime().toLocalDateTime();
                    LocalDateTime endDate = readingsList.get(j).getTime().toLocalDateTime();
                    measurements.add(new Measurement(startDate,endDate,value, readingsList.get(i).getUser()));
                }
            }
        }
        return measurements;
    }

}
