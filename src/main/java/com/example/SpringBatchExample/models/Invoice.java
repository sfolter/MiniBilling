package com.example.SpringBatchExample.models;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "invoice")
public class Invoice {

    @Id
    @Column(name = "doc_number")
    private String documentNumber;
    @Column(name = "consumer")
    private String consumer;
    @Column(name = "ref_number")
    private int reference;
    @Column(name = "total_amount")
    private BigDecimal totalAmount;
    @Column(name = "total_amount_with_vat")
    private BigDecimal totalAmountWithVat;
    @Column(name = "lines")
    @OneToMany(cascade = CascadeType.ALL)
    private List<InvoiceLine> lines;
    @Column(name = "taxes")
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "doc_number")
    private List<Tax> taxes;
    @Column(name = "vats")
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "doc_number",
            nullable = false)
    private List<Vat> vats;
    @Transient
    public static int id = 10000;

    public Invoice() {

    }


    public Invoice(final String documentNumber, final String consumer, final int reference,
                   final BigDecimal totalAmount,
                   final BigDecimal totalAmountWIthVat, final List<InvoiceLine> invoiceLines, final List<Tax> tax,
                   final List<Vat> vat) {

        this.documentNumber = documentNumber;
        this.consumer = consumer;
        this.reference = reference;
        this.totalAmount = totalAmount;
        totalAmountWithVat = totalAmountWIthVat;
        lines = invoiceLines;
        vats = vat;
        taxes = tax;

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

    public int getReference() {
        return reference;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public BigDecimal getTotalAmountWithVat() {
        return totalAmountWithVat;
    }

    public List<InvoiceLine> getLines() {
        return lines;
    }

    public List<Tax> getTaxes() {
        return taxes;
    }

    public List<Vat> getVats() {
        return vats;
    }

    public static int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "documentNumber='" + documentNumber + '\'' +
                ", consumer='" + consumer + '\'' +
                ", reference=" + reference +
                ", totalAmount=" + totalAmount +
                ", totalAmountWithVat=" + totalAmountWithVat +
                ", lines=" + lines +
                ", taxes=" + taxes +
                ", vats=" + vats +
                '}';
    }
}