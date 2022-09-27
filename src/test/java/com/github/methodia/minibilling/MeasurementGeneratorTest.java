package com.github.methodia.minibilling;

import com.github.methodia.minibilling.entityClasses.Price;
import com.github.methodia.minibilling.entityClasses.PriceList;
import com.github.methodia.minibilling.entityClasses.Reading;
import com.github.methodia.minibilling.entityClasses.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

class MeasurementGeneratorTest {
    @Test
    void generateOneMeasurement(){
        final List<Price> prices=new ArrayList<>();
        prices.add(new Price("gas", LocalDate.of(2020,1,1),LocalDate.of(2022,2,2),new BigDecimal("2")));
        final PriceList priceList=new PriceList(1,prices);
        final User test=new User( "1","Test Testov", priceList, Collections.emptyList(),"GBP");
        final List<Reading> readings= new ArrayList<>();
        final String timeReading1Zdt="2021-03-06T13:23:01+03:00";
        final Reading reading1=new Reading("1","gas",ZonedDateTime.parse(timeReading1Zdt).withZoneSameInstant(ZoneId.of("GMT")), new BigDecimal("100"));
        final String timeReading2Zdt="2021-05-06T13:23:01+03:00";
        final Reading reading2=new Reading("1","gas", ZonedDateTime.parse(timeReading2Zdt).withZoneSameInstant(ZoneId.of("GMT")) ,new BigDecimal("200"));
        readings.add(reading1);
        readings.add(reading2);
        final MeasurementGenerator measurementGenerator= new MeasurementGenerator();
        final Collection<Measurement> measurementCollections=measurementGenerator.generate(test,readings);
        final Measurement singleMeasurement = measurementCollections.iterator().next();
        final LocalDateTime measurementStart = singleMeasurement.start();
        final LocalDateTime measurementEnd = singleMeasurement.end();
        final BigDecimal value=singleMeasurement.value();
        Assertions.assertEquals(reading1.getDate().toLocalDateTime(),measurementStart,
                "Start time of measurement doesn't match with time of reading");
        Assertions.assertEquals(reading2.getDate().toLocalDateTime(),measurementEnd,
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
        final PriceList priceList=new PriceList();
        final User test=new User("Test Testov", "ref",priceList, Collections.emptyList(), "GBP");
        final List<Reading> readings= new ArrayList<>();
        final String reading1Zdt="2021-03-06T13:23:01+03:00";
        final String reading2Zdt="2021-05-06T13:23:01+03:00";
        final String reading3Zdt="2021-07-06T13:23:01+03:00";
        final String reading4Zdt="2021-09-06T13:23:01+03:00";
        final Reading reading1=new Reading("1","gas", ZonedDateTime.parse(reading1Zdt).withZoneSameInstant(ZoneId.of("GMT")),new BigDecimal("100"));
        final Reading reading2=new Reading("1","gas",ZonedDateTime.parse(reading2Zdt).withZoneSameInstant(ZoneId.of("GMT")),new BigDecimal("200"));
        final Reading reading3=new Reading("1","gas",ZonedDateTime.parse(reading3Zdt).withZoneSameInstant(ZoneId.of("GMT")),new BigDecimal("270"));
        final Reading reading4=new Reading("1","gas",ZonedDateTime.parse(reading4Zdt).withZoneSameInstant(ZoneId.of("GMT")),new BigDecimal("300"));
        readings.add(reading1);
        readings.add(reading2);
        readings.add(reading3);
        readings.add(reading4);
        final MeasurementGenerator measurementGenerator= new MeasurementGenerator();
        final List<Measurement> measurementCollections=measurementGenerator.generate(test,readings).stream().toList();

        Assertions.assertEquals(reading1.getDate().toLocalDateTime(),measurementCollections.get(0).start(),
                "Start time of measurement2 doesn't match with time of reading" );
        Assertions.assertEquals(reading2.getDate().toLocalDateTime(),measurementCollections.get(0).end(),
                "End time of measurement2 doesn't match with time of reading" );
        Assertions.assertEquals(new BigDecimal("100"),measurementCollections.get(0).value(),
                "Quantity is different.");

        Assertions.assertEquals(reading2.getDate().toLocalDateTime(),measurementCollections.get(1).start(),
                "Start time of measurement2 doesn't match with time of reading" );
        Assertions.assertEquals(reading3.getDate().toLocalDateTime(),measurementCollections.get(1).end(),
                "End time of measurement2 doesn't match with time of reading" );
        Assertions.assertEquals(new BigDecimal("70"),measurementCollections.get(1).value(),
                "Quantity is different.");

        Assertions.assertEquals(reading3.getDate().toLocalDateTime(),measurementCollections.get(2).start(),
                "Start time of measurement2 doesn't match with time of reading" );
        Assertions.assertEquals(reading4.getDate().toLocalDateTime(),measurementCollections.get(2).end(),
                "End time of measurement2 doesn't match with time of reading" );
        Assertions.assertEquals(new BigDecimal("30"),measurementCollections.get(2).value(),
                "Quantity is different.");

    }
}

