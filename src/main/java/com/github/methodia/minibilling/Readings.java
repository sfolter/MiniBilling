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

    public String getProduct() {
        return product;
    }

    public String getRefferentNumber() {
        return refferentNumber;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Readings{" +
                " refferentNumber='" + refferentNumber + '\'' +
                "product='" + product + '\'' +
                ", date=" + date +
                ", price=" + price +
                '}';
    }
}
