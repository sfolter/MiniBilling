package com.github.methodia.minibilling;

import java.time.LocalDateTime;
import java.util.Date;

public class Report {
    String product;
    LocalDateTime date;
    double price;


    public Report(String product, LocalDateTime date, double price) {
        this.product = product;
        this.date = date;
        this.price = price;
    }
}
