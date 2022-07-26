package com.github.methodia.minibilling;

import org.joda.time.DateTime;


public class Readings {
    private String referenceNumber;
    private  String product;
    private DateTime date;
    private double indication;

    public Readings(String referenceNumber, String product, DateTime date, double indication) {


        this.referenceNumber = referenceNumber;
        this.product = product;
        this.date = date;
        this.indication = indication;

    }

    public Readings() {

    }

    public DateTime getDate() {
        return date;
    }

    public String getProduct() {
        return product;
    }


    public double getIndication() {
        return indication;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }
}
