package com.github.methodia.minibilling;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Miroslav Kovachev
 * 28.07.2022
 * Methodia Inc.
 */
public class Invoice {

    private String documentDate;
    private String documentNumber;
    private String consumer;
    private BigDecimal totalAmount;
    private List<InvoiceLine> lines;

    private static long idContour=10000;

    public Invoice(String documentDate, String documentNumber, String consumer, BigDecimal totalAmount,
                   List<InvoiceLine> lines) {
        this.documentDate = documentDate;
        this.documentNumber = documentNumber;
        this.consumer = consumer;
        this.totalAmount = totalAmount;
        this.lines = lines;
    }

    public String getDocumentDate() {
        return documentDate;
    }

//    public String getDocumentNumber() {
//        return documentNumber;
//    }

    public String  getConsumer() {
        return consumer;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public List<InvoiceLine> getLines() {
        return lines;
    }
    public static synchronized String getDocumentNumber() {
        return String.valueOf(idContour++);
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
