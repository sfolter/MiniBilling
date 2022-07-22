package com.github.methodia.minibilling;

import java.time.LocalDate;
import java.util.Date;

public class Prices {
    private String product;
    private LocalDate start;
    private LocalDate end;
    private double price;

    public Prices(String product, LocalDate start, LocalDate end, double price) {
        this.product = product;
        this.start = start;
        this.end = end;
        this.price = price;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public LocalDate getStart() {
        return start;
    }

    public void setStart(LocalDate start) {
        this.start = start;
    }

    public LocalDate getEnd() {
        return end;
    }

    public void setEnd(LocalDate end) {
        this.end = end;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Prices{" +
                "product='" + product + '\'' +
                ", start=" + start +
                ", end=" + end +
                ", price=" + price +
                '}';
    }
}
