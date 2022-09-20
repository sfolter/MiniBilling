package com.github.methodia.minibilling.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
@Entity
@Table(name = "invoice")
public class Invoice {
@Column(name = "document_date")
    private LocalDateTime documentDate;
    @Id
    @Column(name = "document_number",nullable = false)
    private String documentNumber;
    @Column(name = "consumer")
    private String consumer;
    @Column(name = "referent_number")
    private String reference;
    @Column(name = "total_amount")
    private BigDecimal totalAmount;
    @Column(name = "total_amount_with_vat")

    private BigDecimal totalAmountWithVat;
    @Column(name = "currency")

    private String currency;
    @OneToMany
    @JoinColumn(name = "document_number")
    private List<InvoiceLine> lines;
    @OneToMany
    @JoinColumn(name = "document_number")
    private List<Tax> taxes;
    @OneToMany
    @JoinColumn(name = "document_number")
    private List<Vat> vat;

public Invoice(){}
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
