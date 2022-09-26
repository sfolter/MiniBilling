package com.github.methodia.minibilling.entityClasses;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "invoicelines")
public class InvoiceLine {

    @Id
    @Column(name = "id",
            nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(name = "index")
    private int index;
    @Column(name = "quantity")
    private BigDecimal quantity;

    @Column(name = "linesStart")
    private LocalDateTime lineStart;
    @Column(name = "linesEnd")
    private LocalDateTime lineEnd;
    @Column(name = "product")
    private String product;
    @Column(name = "price")
    private BigDecimal price;
    @Column(name = "price_list")
    private int priceList;
    @Column(name = "amount")
    private BigDecimal amount;

    //    @Column(name = "document_numbers",insertable = false,updatable = false)
    //    Integer docNumbers;



    //    @ManyToOne
    //    @JoinColumn( name = "document_numbers",referencedColumnName = "document_numbers")
    //    @Expose(serialize = true,deserialize = false)
    //    Invoice invoice;

    //    public void setInvoice(Invoice invoice) {
    //        this.invoice = invoice;
    //    }

    public InvoiceLine() {
    }

    public InvoiceLine(final int index, final BigDecimal quantity, final LocalDateTime start, final LocalDateTime end, final String product,
                       final BigDecimal price, final int priceList, final BigDecimal amount) {
        this.index = index;
        this.quantity = quantity;
        lineStart = start;
        lineEnd = end;
        this.product = product;
        this.price = price;
        this.priceList = priceList;
        this.amount = amount;
    }

    public int getIndex() {
        return index;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public LocalDateTime getStart() {
        return lineStart;
    }

    public LocalDateTime getEnd() {
        return lineEnd;
    }

    public String getProduct() {
        return product;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public int getPriceList() {
        return priceList;
    }

    public BigDecimal getAmount() {
        return amount;
    }


}
