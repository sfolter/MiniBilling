package com.github.methodia.minibilling;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "invoice")
public class Invoice {
    @Transient
    private static long idContour = 10000;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id",
            nullable = false)
    private Integer id;

    @Column(name = "document_numbers")
    private  String documentNumber;
    @Column(name = "consumers")
    private  String consumer;
    @Column(name = "ref")
    private  String reference;
    @Column(name = "total_amounts")
    private  BigDecimal totalAmount;
    @Column(name = "total_amounts_with_vats")
    private  BigDecimal totalAmountWithVat;
    @OneToMany(mappedBy = "invoice")
    private  List<InvoiceLine> lines;
    @OneToMany(mappedBy = "invoice")
    private  List<Vat> vat;
    @OneToMany(mappedBy = "invoice")
    private  List<Tax> taxes;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Invoice() {
    }


    public Invoice(String documentNumber, String consumer, String reference, BigDecimal totalAmount,
                   BigDecimal totalAmountWithVat, List<InvoiceLine> lines, List<Vat> vat, List<Tax>taxes) {

        this.documentNumber = documentNumber;
        this.consumer = consumer;
        this.reference = reference;
        this.totalAmount = totalAmount;
        this.totalAmountWithVat = totalAmountWithVat;
        this.lines = lines;
        this.vat = vat;
        this.taxes=taxes;
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