package com.github.methodia.minibilling;

import com.github.methodia.minibilling.InvoiceLine;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class Invoice {

    private static long idContour= 10000;
    private LocalDateTime documentDate;
    private String documentNumber;
    private String consumer;
    private BigDecimal totalAmount;
    private List<InvoiceLine> lines;


    public Invoice(LocalDateTime documentDate, String documentNumber, String consumer, BigDecimal totalAmount, List<InvoiceLine> lines) {
        this.documentDate = documentDate;
        this.documentNumber = documentNumber;
        this.consumer = consumer;
        this.totalAmount = totalAmount;
        this.lines = lines;
    }

    public LocalDateTime getDocumentDate() {
        return documentDate;
    }
    public String getDocNumber() {
        return documentNumber;
    }
    public static synchronized String getDocumentNumber() {
        return String.valueOf(idContour++);
    }

    public String getConsumer() {
        return consumer;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public List<InvoiceLine> getLines() {
        return lines;
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "documentDate=" + documentDate +
                ", documentNumber='" + documentNumber + '\'' +
                ", consumer='" + consumer + '\'' +
                ", totalAmount=" + totalAmount +
                ", lines=" + lines +
                '}';
    }
}