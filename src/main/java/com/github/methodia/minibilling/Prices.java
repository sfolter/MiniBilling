package com.github.methodia.minibilling;

import java.util.Date;

public class Prices {
    private String product;
    private Date start;
    private Date end;
    private double price;

    public Prices(String product, Date start, Date end, double price) {
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

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
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
