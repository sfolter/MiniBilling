package com.github.methodia.minibilling;

import java.text.SimpleDateFormat;

public class Report {
    private String product;
    SimpleDateFormat  data;
    private double value;

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public SimpleDateFormat getData() {
        return data;
    }

    public void setData(SimpleDateFormat data) {
        this.data = data;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
