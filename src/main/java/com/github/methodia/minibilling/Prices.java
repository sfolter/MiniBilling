package com.github.methodia.minibilling;


import java.math.BigDecimal;
import java.util.Date;

public class Prices {
    String product;
    Date startingDate;
    Date endDate;
    double price;

    public Prices(String product, Date startingDate, Date endDate, double price) {
        this.product = product;
        this.startingDate = startingDate;
        this.endDate = endDate;
        this.price = price;
    }
    @Override
    public String toString() {
        return "Prices{" +
                "product='" + product + '\'' +
                ", startingDate=" + startingDate +
                ", endDate=" + endDate +
                ", price=" + price +
                '}';
    }


}