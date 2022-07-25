package com.github.methodia.minibilling;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

public class Information {
    String documentDate;
    String documentNumber;
    String consumer;
    String reference;
    double totalAmount;
    List<Lines> lines = new ArrayList<>();


}