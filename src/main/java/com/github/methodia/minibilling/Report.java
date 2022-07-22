package com.github.methodia.minibilling;

import org.joda.time.DateTime;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Date;

public class Report {
    private String refernceNumber;
    private String product;
    private ZonedDateTime date;
    private double value;

    public Report(String refernceNumber, String product, ZonedDateTime date, double value) {
        this.refernceNumber = refernceNumber;
        this.product = product;
        this.date = date;
        this.value = value;
    }

    public String getRefernceNumber() {
        return refernceNumber;
    }

    public void setRefernceNumber(String refernceNumber) {
        this.refernceNumber = refernceNumber;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public ZonedDateTime getData() {
        return date;
    }

    public void setData(ZonedDateTime data) {
        this.date = data;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Report{" +
                "refernceNumber='" + refernceNumber + '\'' +
                ", product='" + product + '\'' +
                ", date=" + date +
                ", value=" + value +
                '}';
    }
}
