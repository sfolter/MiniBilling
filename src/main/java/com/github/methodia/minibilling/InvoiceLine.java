package com.github.methodia.minibilling;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
    @Column(name = "indexes")
    private  int index;
    @Column(name = "quantities")
    private  BigDecimal quantity;

    @Column(name = "linesStart")
    private  LocalDateTime lineStart;
    @Column(name = "linesEnd")
    private  LocalDateTime lineEnd;
    @Column(name = "products")
    private  String product;
    @Column(name = "prices")
    private  BigDecimal price;
    @Column(name = "price_lists")
    private  int priceList;
    @Column(name = "amounts")
    private  BigDecimal amount;


//    @ManyToOne
//    @JoinColumn(name = "document_numbers")
//    Invoice invoice;

    public InvoiceLine() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public InvoiceLine(int index, BigDecimal quantity, LocalDateTime start, LocalDateTime end, String product,
                       BigDecimal price, int priceList, BigDecimal amount) {
        this.index = index;
        this.quantity = quantity;
        this.lineStart = start;
        this.lineEnd = end;
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
