package com.github.methodia.minibilling;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class BillingInformation {
    String documentDate;
    String documentNumber;
    String consumer;
    String reference;
    Double totalAmount;
    List<LinesForJsonFile> lines = new ArrayList<>();


    public String getDocumentDate() {
        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");

        documentDate = ZonedDateTime.now().format(formatter1);
        return documentDate;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public String getConsumer() {
        return consumer;
    }

    public String getReference() {
        return reference;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public List<LinesForJsonFile> getLines() {
        return lines;
    }
}
