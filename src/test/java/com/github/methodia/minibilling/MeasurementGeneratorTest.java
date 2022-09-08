package com.github.methodia.minibilling;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

class MeasurementGeneratorTest {
    @Test
    void generateOneMeasurement(){

        User test=new User("Test Testov", "ref", 1, Collections.emptyList(),"GBP");
        List<Reading> readings= new ArrayList<>();
        Reading reading1=new Reading(LocalDateTime.of(2021, 3, 6, 13, 23),new BigDecimal("100"),test);
        Reading reading2=new Reading(LocalDateTime.of(2021, 5, 6, 13, 23),new BigDecimal("200"),test);
        readings.add(reading1);
        readings.add(reading2);
        final MeasurementGenerator measurementGenerator= new MeasurementGenerator();
        Collection<Measurement> measurementCollections=measurementGenerator.generate(test,readings);
        final Measurement singleMeasurement = measurementCollections.iterator().next();
        LocalDateTime measurementStart = singleMeasurement.start();
        LocalDateTime measurementEnd = singleMeasurement.end();
        BigDecimal value=singleMeasurement.value();
        Assertions.assertEquals(reading1.getTime(),measurementStart,
                "Start time of measurement doesn't match with time of reading");
        Assertions.assertEquals(reading2.getTime(),measurementEnd,
                "End time of measurement doesn't match with time of reading");
        Assertions.assertEquals(new BigDecimal("100"),value,
                "Quantity doesn't match.");
    }
//    private static List<Reading> generateCollectionOfReadings(User user){
//        List<Reading> readings= new ArrayList<>();
//        readings.add(new Reading(LocalDateTime.of(2021, 3, 6, 13, 23),new BigDecimal("100"),user));
//        readings.add(new Reading(LocalDateTime.of(2021, 5, 6, 13, 23),new BigDecimal("200"),user));
//        return readings;
//   }

    @Test
    void generateTwoMeasurements(){
        User test=new User("Test Testov", "ref",1, Collections.emptyList(), "GBP");
        List<Reading> readings= new ArrayList<>();
        Reading reading1=new Reading(LocalDateTime.of(2021, 3, 6, 13, 23),new BigDecimal("100"),test);
        Reading reading2=new Reading(LocalDateTime.of(2021, 5, 6, 13, 23),new BigDecimal("200"),test);
        Reading reading3=new Reading(LocalDateTime.of(2021, 7, 6, 13, 23),new BigDecimal("270"),test);
        Reading reading4=new Reading(LocalDateTime.of(2021, 9, 6, 13, 23),new BigDecimal("300"),test);
        readings.add(reading1);
        readings.add(reading2);
        readings.add(reading3);
        readings.add(reading4);
        final MeasurementGenerator measurementGenerator= new MeasurementGenerator();
        List<Measurement> measurementCollections=measurementGenerator.generate(test,readings).stream().toList();

        Assertions.assertEquals(reading1.getTime(),measurementCollections.get(0).start(),
                "Start time of measurement2 doesn't match with time of reading" );
        Assertions.assertEquals(reading2.getTime(),measurementCollections.get(0).end(),
                "End time of measurement2 doesn't match with time of reading" );
        Assertions.assertEquals(new BigDecimal("100"),measurementCollections.get(0).value(),
                "Quantity is different.");

        Assertions.assertEquals(reading2.getTime(),measurementCollections.get(1).start(),
                "Start time of measurement2 doesn't match with time of reading" );
        Assertions.assertEquals(reading3.getTime(),measurementCollections.get(1).end(),
                "End time of measurement2 doesn't match with time of reading" );
        Assertions.assertEquals(new BigDecimal("70"),measurementCollections.get(1).value(),
                "Quantity is different.");

        Assertions.assertEquals(reading3.getTime(),measurementCollections.get(2).start(),
                "Start time of measurement2 doesn't match with time of reading" );
        Assertions.assertEquals(reading4.getTime(),measurementCollections.get(2).end(),
                "End time of measurement2 doesn't match with time of reading" );
        Assertions.assertEquals(new BigDecimal("30"),measurementCollections.get(2).value(),
                "Quantity is different.");

    }
}

