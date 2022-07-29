package com.github.methodia.minibilling;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Miroslav Kovachev
 * 28.07.2022
 * Methodia Inc.
 */
public class Invoice {

    private LocalDateTime documentDate;
    private String documentNumber;
    private User consumer;
    private BigDecimal totalAmount;
    private List<InvoiceLine> lines;

    public Invoice(LocalDateTime documentDate, String documentNumber, User consumer, BigDecimal totalAmount,
                   List<InvoiceLine> lines) {
        this.documentDate = documentDate;
        this.documentNumber = documentNumber;
        this.consumer = consumer;
        this.totalAmount = totalAmount;
        this.lines = lines;
    }

    public LocalDateTime getDocumentDate() {
        return documentDate;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public User getConsumer() {
        return consumer;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public List<InvoiceLine> getLines() {
        return lines;
    }
}