package com.github.methodia.minibilling;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class Invoice {

    private LocalDateTime documentDate;
    private String documentNumber;
    private User consumer;
    private BigDecimal totalAmount;

    private BigDecimal totalAmountWithVat;
    private List<InvoiceLine> lines;

    private List<VAT> vatsLines;
    private static long counter = 9999;

    public Invoice(LocalDateTime documentDate, String documentNumber, User consumer, BigDecimal totalAmount, BigDecimal totalAmountWithVat,
                   List<InvoiceLine> lines, List<VAT> vatsLines) {
        this.documentDate = documentDate;
        this.documentNumber = documentNumber;
        this.consumer = consumer;
        this.totalAmount = totalAmount;
        this.totalAmountWithVat = totalAmountWithVat;
        this.lines = lines;
        this.vatsLines = vatsLines;
        counter++;
    }

    public LocalDateTime getDocumentDate() {
        return documentDate;
    }

    public static synchronized String getDocumentNumber() {
        return String.valueOf(counter);
    }

    public User getConsumer() {
        return consumer;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public BigDecimal getTotalAmountWithVat() {
        return totalAmountWithVat;
    }

    public List<InvoiceLine> getLines() {
        return lines;
    }

    public List<VAT> getVatsLines() {
        return vatsLines;
    }
}
