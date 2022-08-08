package com.github.methodia.minibilling;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class Invoice {

    private static long idContour = 10000;
    private LocalDateTime documentDate;
    private String documentNumber;
    private String consumer;

    private String reference;
    private BigDecimal totalAmount;
    private List<InvoiceLine> lines;


    public Invoice(LocalDateTime documentDate, String documentNumber, String consumer, String reference, BigDecimal totalAmount, List<InvoiceLine> lines) {
        this.documentDate = documentDate;
        this.documentNumber = documentNumber;
        this.consumer = consumer;
        this.reference = reference;
        this.totalAmount = totalAmount;
        this.lines = lines;
    }

    public LocalDateTime getDocumentDate() {
        return documentDate;
    }

    public static synchronized String getDocumentNumber() {
        return String.valueOf(idContour++);
    }

    public String getDocumentNum() {
        return documentNumber;
    }

    public String getConsumer() {
        return consumer;
    }

    public String getReference() {
        return reference;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public List<InvoiceLine> getLines() {
        return lines;
    }


}
