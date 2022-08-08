package com.github.methodia.minibilling;

import com.github.methodia.minibilling.InvoiceLine;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class Invoice {

    private static long idContour= 10000;

    private String documentNumber;
    private String consumer;
    private String reference;
    private BigDecimal totalAmount;
    private List<InvoiceLine> lines;


    public Invoice( String documentNumber, String consumer, String reference, BigDecimal totalAmount, List<InvoiceLine> lines) {

        this.documentNumber = documentNumber;
        this.consumer = consumer;
        this.reference=reference;
        this.totalAmount = totalAmount;
        this.lines = lines;
    }

    public String getDocNumber() {
        return documentNumber;
    }
    public static  String getDocumentNumber() {
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

    public String getReference() {
        return reference;
    }


}