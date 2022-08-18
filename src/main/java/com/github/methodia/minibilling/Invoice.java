package com.github.methodia.minibilling;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class Invoice {

    private final LocalDateTime documentDate;
    private final String documentNumber;
    private final User consumer;
    private final BigDecimal totalAmount;
    private final BigDecimal totalAmountWithVat;
    private final List<InvoiceLine> lines;
    private final List<Vat> vatsLines;

    private final List<Taxes> taxesLines;
    private static long counter = 9999;

    public Invoice(LocalDateTime documentDate, String documentNumber, User consumer, BigDecimal totalAmount, BigDecimal totalAmountWithVat,
                   List<InvoiceLine> lines, List<Vat> vatsLines, List<Taxes> taxesLines) {
        this.documentDate = documentDate;
        this.documentNumber = documentNumber;
        this.consumer = consumer;
        this.totalAmount = totalAmount;
        this.totalAmountWithVat = totalAmountWithVat;
        this.lines = lines;
        this.taxesLines = taxesLines;
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

    public List<Taxes> getTaxesLines() {
        return taxesLines;
    }

    public List<Vat> getVatsLines() {
        return vatsLines;
    }

}
