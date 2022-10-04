package com.github.methodia.minibilling;

import javax.persistence.CascadeType;
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
    private final LocalDateTime documentDate;
    @Id
    @Column(name = "document_number")
    private final String documentNumber;
    @Column(name = "consumer")
    private final String consumer;
    @Column(name = "total_amount")
    private final BigDecimal totalAmount;
    @Column(name = "total_amount_with_vat")
    private final BigDecimal totalAmountWithVat;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "invoice_document_number")
    private final List<InvoiceLine> lines;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "invoice_document_number")
    private final List<VatLine> vatsLines;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "invoice_document_number")
    private final List<TaxLines> taxesLines;
    private static long counter = 9999;

    public Invoice(LocalDateTime documentDate, String documentNumber, String consumer, BigDecimal totalAmount,
                   BigDecimal totalAmountWithVat,
                   List<InvoiceLine> lines, List<VatLine> vatsLines, List<TaxLines> taxesLines) {
        this.documentDate = documentDate;
        this.documentNumber = documentNumber;
        this.consumer = consumer;
        this.totalAmount = totalAmount;
        this.totalAmountWithVat = totalAmountWithVat;
        this.lines = lines;
        this.taxesLines = taxesLines;
        this.vatsLines = vatsLines;
        counter++;
    }

    public LocalDateTime getDocumentDate() {return documentDate;}
    public String getDocumentNumberTest() {return documentNumber;}

    public static synchronized String getDocumentNumber() {
        return String.valueOf(counter);
    }
    public String getConsumer() {
        return consumer;
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
    public List<TaxLines> getTaxesLines() {
        return taxesLines;
    }
    public List<VatLine> getVatsLines() {
        return vatsLines;
    }
}
