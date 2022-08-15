package com.github.methodia.minibilling;

import java.math.BigDecimal;
import java.util.List;


public class Invoice {
    private final String documentNumber;
    private final String consumer;
    private final String reference;
    private final BigDecimal totalAmount;
    private final BigDecimal totalAmountWIthVat;
    private final List<InvoiceLine> lines;
    private final List<Vat> vat;
    private final List<Tax> tax;
    private static long id = 10000;

    public Invoice(String documentNumber, String consumer, String reference, BigDecimal totalAmount, BigDecimal totalAmountWIthVat, List<InvoiceLine> invoiceLines, List<Vat> vat, List<Tax> tax) {

        this.documentNumber = documentNumber;
        this.consumer = consumer;
        this.reference = reference;

        this.totalAmount = totalAmount;
        this.totalAmountWIthVat = totalAmountWIthVat;
        this.lines = invoiceLines;

        this.vat = vat;
        this.tax = tax;
    }

    public static synchronized String getDocumentNumber() {
        return String.valueOf(id++);
    }

    public String getDocNumber() {
        return documentNumber;
    }


}