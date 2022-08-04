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
    private final LocalDateTime documentDate;
    private final String documentNumber;
    private final String consumer;
    private final BigDecimal totalAmount;
    private final List<InvoiceLine> lines;
    private static long id = 10000;

    public Invoice(LocalDateTime documentDate, String documentNumber, String consumer, BigDecimal totalAmount, List<InvoiceLine> invoiceLines) {
        this.documentDate = documentDate;
        this.documentNumber = documentNumber;
        this.consumer = consumer;
        this.totalAmount = totalAmount;
        this.lines = invoiceLines;
    }

    public static synchronized String getDocumentNumber() {
        return String.valueOf(id++);
    }

    @Override
    public String toString() {
        return "{" +
                "documentDate:" + documentDate +
                ", documentNumber:'" + documentNumber + '\'' +
                ", consumer:" + consumer +
                ", totalAmount:" + totalAmount +
                ", lines:" + lines +
                '}';
    }
}
