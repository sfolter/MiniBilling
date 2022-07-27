package com.github.methodia.minibilling;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.ZonedDateTime;

public class ReadingsTest {
    Readings reading;
    private String referentNUmber= "1";
    private   String product= "gas";

    private ZonedDateTime date =ZonedDateTime.parse("2021-01-01T09:20:00+03:00");

    private double metrics=13;

    @Test
    public void testConstructorShouldCreateValidPrices(){
        reading=new Readings(referentNUmber,product,date,metrics);
        Assert.assertEquals("1",reading.getReferentNUmber());
        Assert.assertEquals("gas", reading.getProduct());
        Assert.assertEquals(ZonedDateTime.parse("2021-01-01T09:20:00+03:00"),reading.getDate());
        Assertions.assertEquals(13,reading.getMetrics());
    }
}
