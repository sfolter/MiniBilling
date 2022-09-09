package com.github.methodia.minibilling;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class Invoice {

    private static long idContour = 9999;
    private final LocalDateTime documentDate;
    private final String documentNumber;
    private final User consumer;
    private final BigDecimal totalAmount;
    private final BigDecimal totalAmountWithVat;
    private final List<InvoiceLine> lines;
    private final List<VatLine> vatLines;
    private final List<TaxesLine> taxesLines;


    public Invoice(final LocalDateTime documentDate, final String documentNumber, final User consumer,
                   final BigDecimal totalAmount, final BigDecimal totalAmountWithVat, final List<InvoiceLine> lines,
                   final List<VatLine> vatLines, final List<TaxesLine> taxesLines) {
        this.documentDate = documentDate;
        this.documentNumber = documentNumber;
        this.consumer = consumer;
        this.totalAmount = totalAmount;
        this.totalAmountWithVat = totalAmountWithVat;
        this.lines = lines;
        this.vatLines = vatLines;
        idContour++;
        this.taxesLines = taxesLines;
    }

    public LocalDateTime getDocumentDate() {
        return documentDate;
    }

    public static synchronized String getDocumentNumber() {
        return String.valueOf(idContour);
    }

    public User getConsumer() {
        return consumer;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public List<InvoiceLine>

    getLines() {
        return lines;
    }

    public List<VatLine> getVatLines() {
        return vatLines;
    }

    public BigDecimal getTotalAmountWithVat() {
        return totalAmountWithVat;
    }

    public List<TaxesLine> getTaxesLines() {
        return taxesLines;
    }
}