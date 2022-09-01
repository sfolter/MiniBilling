package com.github.methodia.minibilling;

import java.math.BigDecimal;
import java.util.List;


public class Invoice {

    private final String documentNumber;
    private final String consumer;
    private final String reference;
    private final BigDecimal totalAmount;
    private final BigDecimal totalAmountWIthVat;
    private final String currencyFrom;
    private final String currencyTo;
    private final BigDecimal exchangedTotalAmount;
    private final List<InvoiceLine> lines;
    private final List<Tax> taxes;
    private final List<Vat> vat;
    private static long id = 10000;

    public Invoice(String documentNumber, String consumer, String reference, BigDecimal totalAmount,
                   BigDecimal totalAmountWIthVat, String currencyFrom, String currencyTo,
                   BigDecimal exchangedTotalAmount, List<InvoiceLine> invoiceLines, List<Tax> tax, List<Vat> vat) {

        this.documentNumber = documentNumber;
        this.consumer = consumer;
        this.reference = reference;
        this.totalAmount = totalAmount;
        this.totalAmountWIthVat = totalAmountWIthVat;
        this.currencyFrom = currencyFrom;
        this.currencyTo = currencyTo;
        this.lines = invoiceLines;
        this.vat = vat;
        this.taxes = tax;
        this.exchangedTotalAmount = exchangedTotalAmount;
    }

    public static synchronized String getDocumentNumber() {
        return String.valueOf(id++);
    }

    public String getDocNumber() {
        return documentNumber;
    }

    public String getConsumer() {
        return consumer;
    }

    public String getReference() {
        return reference;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public BigDecimal getTotalAmountWithVat() {
        return totalAmountWIthVat;
    }

    public BigDecimal getExchangedTotalAmount() {
        return exchangedTotalAmount;
    }

    public String getCurrencyFrom() {
        return currencyFrom;
    }

    public String getCurrencyTo() {
        return currencyTo;
    }
}