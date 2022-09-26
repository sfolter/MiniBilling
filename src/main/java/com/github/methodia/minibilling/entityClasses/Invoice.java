package com.github.methodia.minibilling.entityClasses;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "invoice")
public class Invoice implements Serializable {

    @Transient
    private static long idContour = 10000;
    @Id
    @Column(name = "document_number",
            unique = true)
    private Integer documentNumber;
    @Column(name = "consumer")
    private String consumer;
    @Column(name = "ref")
    private String reference;
    @Column(name = "total_amount")
    private BigDecimal totalAmount;
    @Column(name = "total_amounts_with_vat")
    private BigDecimal totalAmountWithVat;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "document_number")
    private List<InvoiceLine> lines;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "document_number")
    private List<Vat> vat;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "document_number")
    private List<Tax> taxes;


    public Invoice() {
    }


    public Invoice(final Integer documentNumber, final String consumer, final String reference, final BigDecimal totalAmount,
                   final BigDecimal totalAmountWithVat, final List<InvoiceLine> lines, final List<Vat> vat, final List<Tax> taxes) {

        this.documentNumber = documentNumber;
        this.consumer = consumer;
        this.reference = reference;
        this.totalAmount = totalAmount;
        this.totalAmountWithVat = totalAmountWithVat;
        this.lines = lines;
        this.vat = vat;
        this.taxes = taxes;
        //        this.lines.stream().forEach(line-> line.setInvoice(Invoice.this));
    }

    public static String getDocumentNumber() {
        return String.valueOf(idContour++);
    }

    public Integer getDocNumber() {
        return documentNumber;
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

    public List<Vat> getVat() {
        return vat;
    }

    public List<Tax> getTaxes() {
        return taxes;
    }
}