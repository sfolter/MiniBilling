package com.github.methodia.minibilling;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Miroslav Kovachev
 * 28.07.2022
 * Methodia Inc.
 */
public class Invoice {


    private final String documentNumber;
    private final String consumer;
    private final String reference;
    private final BigDecimal totalAmount;
    private final List<InvoiceLine> lines;
    private static long id = 10000;

    public Invoice(String documentNumber, String consumer, String reference, BigDecimal totalAmount, List<InvoiceLine> invoiceLines) {

        this.documentNumber = documentNumber;
        this.consumer = consumer;
        this.reference = reference;

        this.totalAmount = totalAmount;
        this.lines = invoiceLines;
    }

    public String getReference() {
        return reference;
    }

    public static synchronized String getDocumentNumber() {
        return String.valueOf(id++);
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "documentNumber='" + documentNumber + '\'' +
                ", consumer='" + consumer + '\'' +
                ", reference='" + reference + '\'' +
                ", totalAmount=" + totalAmount +
                ", lines=" + lines +
                '}';
    }
}