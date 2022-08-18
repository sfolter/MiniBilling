package com.github.methodia.minibilling;



import java.math.BigDecimal;
import java.util.List;

public class Invoice {

    private static long idContour = 10000;
    private final String documentNumber;
    private final String consumer;
    private final String reference;
    private final BigDecimal totalAmount;
    private final BigDecimal totalAmountWithVat;
    private final List<InvoiceLine> lines;
    private final List<Vat> vat;

    public Invoice(String documentNumber, String consumer, String reference, BigDecimal totalAmount,
                   BigDecimal totalAmountWithVat, List<InvoiceLine> lines, List<Vat> vat) {

        this.documentNumber = documentNumber;
        this.consumer = consumer;
        this.reference = reference;
        this.totalAmount = totalAmount;
        this.totalAmountWithVat = totalAmountWithVat;
        this.lines = lines;
        this.vat = vat;
    }

    public String getDocNumber() {
        return documentNumber;
    }

    public static String getDocumentNumber() {
        return String.valueOf (idContour++);
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

    public String getReference() {
        return reference;
    }

    public BigDecimal getTotalAmountWithVat() {
        return totalAmountWithVat;
    }
}