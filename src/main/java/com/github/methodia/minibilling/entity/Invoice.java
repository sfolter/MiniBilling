package com.github.methodia.minibilling.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "invoice")
public class Invoice {

    @Column(name = "document_date")
    private LocalDateTime documentDate;
    @Id
    @Column(name = "document_number")
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
    @OneToMany(targetEntity = InvoiceLine.class,cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "invoice_document_number",referencedColumnName = "document_number")
    private List<InvoiceLine> lines=new ArrayList<>();
    @OneToMany(targetEntity = Tax.class,cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "invoice_document_number",referencedColumnName = "document_number")
    private List<Tax> taxes=new ArrayList<>();
    @OneToMany(targetEntity = Vat.class,cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "invoice_document_number",referencedColumnName = "document_number")
    private List<Vat> vat=new ArrayList<>();


    public Invoice() {
    }

    public Invoice(final LocalDateTime documentDate, final String documentNumber, final String consumer,
                   final String reference,
                   final BigDecimal totalAmount, final BigDecimal totalAmountWithVat, final String currency,
                   final List<InvoiceLine> lines,
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

    public BigDecimal getTotalAmountWithVat() {
        return totalAmountWithVat;
    }

    public List<InvoiceLine> getLines() {
        return lines;
    }

    public List<Tax> getTaxes() {
        return taxes;
    }

    public List<Vat> getVat() {
        return vat;
    }

    public void setDocumentDate(final LocalDateTime documentDate) {
        this.documentDate = documentDate;
    }

    public void setDocumentNumber(final String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public void setConsumer(final String consumer) {
        this.consumer = consumer;
    }

    public void setReference(final String reference) {
        this.reference = reference;
    }

    public void setTotalAmount(final BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public void setTotalAmountWithVat(final BigDecimal totalAmountWithVat) {
        this.totalAmountWithVat = totalAmountWithVat;
    }

    public void setCurrency(final String currency) {
        this.currency = currency;
    }

    public void setLines(final List<InvoiceLine> lines) {
        this.lines = lines;
    }

    public void setTaxes(final List<Tax> taxes) {
        this.taxes = taxes;
    }

    public void setVat(final List<Vat> vat) {
        this.vat = vat;
    }
}
