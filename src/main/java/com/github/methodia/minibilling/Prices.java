package com.github.methodia.minibilling;

import java.text.SimpleDateFormat;
import java.time.LocalDate;

public class Prices {
    private String product;
    private LocalDate startDate;
    private LocalDate endDate;
    private double price;


    public Prices(String product, LocalDate startDate, LocalDate endDate, double price) {

        this.product = product;
        this.startDate = startDate;
        this.endDate = endDate;
        this.price = price;
    }

    public String getProduct() {
        return product;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return  product + " " +
                 startDate +
                " " + endDate +
                " " + price +
                "\n";
    }
}
