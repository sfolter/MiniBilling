package com.github.methodia.minibilling;

import java.util.ArrayList;
import java.util.List;

public class Bill {
    private static long idContour= 10000;
    String documentDate;
    String documentNumber;
    String consumer;
    String reference;
    Double totalAmount;

    public void setDocumentDate(String documentDate) {
        this.documentDate = documentDate;
    }

    public void setDocumentNumber(String documentNum) {
        this.documentNumber = documentNum;
    }

    public void setConsumer(String consumer) {
        this.consumer = consumer;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public void setLines(List<Line> lines) {
        this.lines = lines;
    }

    public String getDocumentDate() {
        return documentDate;
    }

    public static synchronized String getDocumentNumber() {
        return String.valueOf(idContour++);
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

    public List<Line> getLines() {
        return lines;
    }

    List<Line> lines = new ArrayList<>();

}
