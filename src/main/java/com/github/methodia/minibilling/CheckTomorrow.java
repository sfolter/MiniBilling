package com.github.methodia.minibilling;

import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.time.LocalDate;



public class CheckTomorrow {
    public static void main(String[] args) {
        // resource: https://beginnersbook.com/2017/10/java-8-calculate-days-between-two-dates/

        //24-May-2017, change this to your desired Start Date
            LocalDate dateBefore = LocalDate.of(2017, Month.MAY, 24);
            //29-July-2017, change this to your desired End Date
            LocalDate dateAfter = LocalDate.of(2017, Month.JULY, 29);
            long noOfDaysBetween = ChronoUnit.DAYS.between(dateBefore, dateAfter);
            System.out.println(noOfDaysBetween);


        /*long noOfDaysBetween = DAYS.between(startDate, endDate);
// or alternatively
        long noOfDaysBetween = startDate.until(endDate, DAYS);*/
        }
    }

