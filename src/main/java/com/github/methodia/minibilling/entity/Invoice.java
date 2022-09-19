package com.github.methodia.minibilling.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class Invoice {

    private final LocalDateTime documentDate;
    private final String documentNumber;
    private final String consumer;
    private final String reference;
    private final BigDecimal totalAmount;
    private final BigDecimal totalAmountWithVat;
    private final String currency;
    private final List<InvoiceLine> lines;
    private final List<Tax> taxes;
    private final List<Vat> vat;


    public Invoice(final LocalDateTime documentDate, final String documentNumber, final String consumer,
                   final String reference,
                   final BigDecimal totalAmount, final BigDecimal totalAmountWithVat, final String currency, final List<InvoiceLine> lines,
                   final List<Tax> taxes,
                   final List<Vat> vat) {
        this.documentDate = documentDate;
        this.documentNumber = documentNumber;
        this.consumer = consumer;
        this.reference = reference;
        this.totalAmount = totalAmount;
        this.totalAmountWithVat = totalAmountWithVat;
        this.currency = currency;
        this.lines = lines;
        this.taxes = taxes;
        this.vat = vat;
    }

    public LocalDateTime getDocumentDate() {
        return documentDate;
    }

    public String getDocumentNumber() {
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

    public BigDecimal getTotalAmountWithVat() {return totalAmountWithVat;}

    public List<InvoiceLine> getLines() {
        return lines;
    }

    public List<Tax> getTaxes() {return taxes;}

    public List<Vat> getVat() {return vat;}
}
