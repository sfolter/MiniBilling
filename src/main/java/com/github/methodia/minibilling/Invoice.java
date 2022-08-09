package com.github.methodia.minibilling;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class Invoice {

    private final LocalDateTime documentDate;
    private final String documentNumber;
    private final String consumer;
    private final String reference;
    private final BigDecimal totalAmount;
    private final List<InvoiceLine> lines;


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

    public String getDocumentNumber() {
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
