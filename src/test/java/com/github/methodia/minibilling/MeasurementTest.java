//package com.github.methodia.minibilling;
//
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//
//import java.math.BigDecimal;
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//
//public class MeasurementTest {
//    @Test
//    void measurementGettersTest(){
//        List<Price> priceList=new ArrayList<>();
//        priceList.add(new Price("gas", LocalDate.of(2022,3,1),
//                LocalDate.of(2022,3,2),new BigDecimal("2.50"),1));
//        priceList.add(new Price("gas", LocalDate.of(2022,3,3),
//                LocalDate.of(2022,4,3),new BigDecimal("3.50"),1));
//        User user = new User("Georgi Ivanov Milanov","1",2,priceList,"EUR");
//
//        Measurement measurement = new Measurement(LocalDateTime.of(2022, 1, 1, 1, 1, 1),
//                LocalDateTime.of(2022, 2, 2, 2, 2, 2), new BigDecimal("2.30"), user);
//
//        Assertions.assertEquals(LocalDateTime.of(2022, 1, 1, 1, 1, 1)
//                ,measurement.start(),"Measuremet getStart is incorrect");
//        Assertions.assertEquals(LocalDateTime.of(2022, 2, 2, 2, 2, 2),
//                measurement.end(),"Measurement getEnd is incorrect");
//        Assertions.assertEquals(new BigDecimal("2.30"),measurement.value(),"Measurement getValue is incorrect");
//        Assertions.assertEquals(user,measurement.user(),"Measurement getUser is incorrect");
//    }
//}
