package com.github.methodia.minibilling;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UserTest {
    @Test
    void userGetters(){
        List<Price> priceList=new ArrayList<>();
        priceList.add(new Price("gas", LocalDate.of(2022,03,01),
                LocalDate.of(2022,3,2),new BigDecimal("2.50")));
        priceList.add(new Price("gas", LocalDate.of(2022,3,3),
                LocalDate.of(2022,4,3),new BigDecimal("3.50")));
        User user = new User("Georgi Ivanov Milanov","1",2,priceList,"EUR");

        Assertions.assertEquals( "1",user.getRef(),"User getRef is incorrect");
        Assertions.assertEquals("Georgi Ivanov Milanov",user.getName(),"User name is incorrect");
        Assertions.assertEquals(2,user.getNumberPricingList(),"User number pricing list is incorrect");
        Assertions.assertEquals(priceList,user.getPrice(),"User price list is incorrect");
        Assertions.assertEquals("EUR",user.getCurrency(),"User currency getter is incorrect");
    }
}
