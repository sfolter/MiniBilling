package com.github.methodia.minibilling;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class Invoice {

    private static long idContour = 9999;
    private LocalDateTime documentDate;
    private String documentNumber;
    private User consumer;
    private BigDecimal totalAmount;
    private BigDecimal totalAmountWithVat;
    private List<InvoiceLine> lines;
    private List<VatLine> vatLines;


    public Invoice(LocalDateTime documentDate, String documentNumber, User consumer,
                   BigDecimal totalAmount, BigDecimal totalAmountWithVat, List<InvoiceLine> lines, List<VatLine> vatLines) {
        this.documentDate = documentDate;
        this.documentNumber = documentNumber;
        this.consumer = consumer;
        this.totalAmount = totalAmount;
        this.totalAmountWithVat = totalAmountWithVat;
        this.lines = lines;
        this.vatLines = vatLines;
        idContour++;
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
}