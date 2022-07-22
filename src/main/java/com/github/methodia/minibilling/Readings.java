package com.github.methodia.minibilling;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

public class Readings {
    String product;

    String refferentNumber;
    ZonedDateTime date;

    double price;
    public Readings(String refferentNumber,String product, ZonedDateTime date, double price) {
        this.refferentNumber=refferentNumber;
        this.product = product;
        this.date = date;
        this.price = price;
    }
}
