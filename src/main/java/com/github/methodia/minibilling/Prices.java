package com.github.methodia.minibilling;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

public class Prices {
    private String product;
    private LocalDate startingDate;
    private LocalDate endDate;
    private double price;


    public Prices(String product, LocalDate startingDate, LocalDate endDate, double price) {
        this.product = product;
        this.startingDate = startingDate;
        this.endDate = endDate;
        this.price = price;
    }
    public String getProduct() {
        return product;
    }

    public LocalDate getStartingDate() {
        return startingDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public double getPrice() {
        return price;
    }




}