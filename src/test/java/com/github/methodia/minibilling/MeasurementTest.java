package com.github.methodia.minibilling;

import com.github.methodia.minibilling.entityClasses.Price;
import com.github.methodia.minibilling.entityClasses.PriceList;
import com.github.methodia.minibilling.entityClasses.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MeasurementTest {
    @Test
    void measurementGettersTest(){
        final List<Price> prices=new ArrayList<>();
        prices.add(new Price("gas", LocalDate.of(2022,3,1),
                LocalDate.of(2022,3,2),new BigDecimal("2.50")));
        prices.add(new Price("gas", LocalDate.of(2022,3,3),
                LocalDate.of(2022,4,3),new BigDecimal("3.50")));
        final PriceList priceList= new PriceList(2,prices);
        final User user = new User("Georgi Ivanov Milanov","1",priceList, new ArrayList<>(),"EUR");

        final Measurement measurement = new Measurement(LocalDateTime.of(2022, 1, 1, 1, 1, 1),
                LocalDateTime.of(2022, 2, 2, 2, 2, 2), new BigDecimal("2.30"), user);

        Assertions.assertEquals(LocalDateTime.of(2022, 1, 1, 1, 1, 1)
                ,measurement.start(),"Measuremet getStart is incorrect");
        Assertions.assertEquals(LocalDateTime.of(2022, 2, 2, 2, 2, 2),
                measurement.end(),"Measurement getEnd is incorrect");
        Assertions.assertEquals(new BigDecimal("2.30"),measurement.value(),"Measurement getValue is incorrect");
        Assertions.assertEquals(user,measurement.user(),"Measurement getUser is incorrect");
    }
}
