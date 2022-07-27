package com.github.methodia.minibilling;

import java.time.ZonedDateTime;

public class Readings {
  private   String product;

   private String referentNUmber;
    private ZonedDateTime date;

    private double metrics;
    public Readings(String referentNumber,String product, ZonedDateTime date, double metrics) {
        this.referentNUmber =referentNumber;
        this.product = product;
        this.date = date;
        this.metrics = metrics;
    }

    public String getProduct() {
        return product;
    }

    public String getReferentNUmber() {
        return referentNUmber;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public double getMetrics() {
        return metrics;
    }


}
