package com.github.methodia.minibilling;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ReadingTest {
    @Test
    void readingGettersTest() {
        List<Price> priceList=new ArrayList<>();
        priceList.add(new Price("gas", LocalDate.of(2022,3,1),
                LocalDate.of(2022,3,2),new BigDecimal("2.50")));
        priceList.add(new Price("gas", LocalDate.of(2022,3,3),
                LocalDate.of(2022,4,3),new BigDecimal("3.50")));
        User user = new User("Georgi Ivanov Milanov","1",2,priceList,"EUR");
        Reading reading = new Reading(LocalDateTime.of(2022, 3, 4, 11, 15, 34),
                new BigDecimal("7.30"), user);

        Assertions.assertEquals(user,reading.getUser(),"User getter is incorrect");
        Assertions.assertEquals(LocalDateTime.of(2022, 3, 4, 11, 15, 34)
                ,reading.getTime(),"Reading getTime is incorrect");
        Assertions.assertEquals(new BigDecimal("7.30"),reading.getValue(),"Reading get value is incorrect");
    }
}
