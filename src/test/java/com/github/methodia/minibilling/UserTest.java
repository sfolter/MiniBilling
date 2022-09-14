//package com.github.methodia.minibilling;
//
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//
//import java.math.BigDecimal;
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.List;
//
//public class UserTest {
//    @Test
//    void userGetters(){
//        List<Price> priceList=new ArrayList<>();
//        priceList.add(new Price("gas", LocalDate.of(2022,3,1),
//                LocalDate.of(2022,3,2),new BigDecimal("2.50"), 1));
//        priceList.add(new Price("gas", LocalDate.of(2022,3,3),
//                LocalDate.of(2022,4,3),new BigDecimal("3.50"), 1));
//        User user = new User("Georgi Ivanov Milanov","1",2,priceList,"EUR");
//
//        Assertions.assertEquals( "1",user.getRefNumber(),"User getRef is incorrect");
//        Assertions.assertEquals("Georgi Ivanov Milanov",user.getName(),"User name is incorrect");
//        Assertions.assertEquals(2,user.getPricesList().getId(),"User number pricing list is incorrect");
//        Assertions.assertEquals(priceList,user.getPriceList().prices,"User price list is incorrect");
//        Assertions.assertEquals("EUR",user.getCurrency(),"User currency getter is incorrect");
//    }
//}
