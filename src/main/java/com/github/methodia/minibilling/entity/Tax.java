package com.github.methodia.minibilling.entity;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "tax")
public class Tax {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "index")
    private int index;
    @Column(name="lines")
    @ElementCollection
    private List<Integer> lineIndex;
    @Column(name = "name")
    private String name;
    @Column(name = "quantity")
    private long quantity;
    @Column(name = "unit")
    private String unit;
    @Column(name = "price")
    private BigDecimal price;
    @Column(name = "amount")
    private BigDecimal amount;



    public Tax() {
    }

    public Tax(final int index, final List<Integer> lineIndex, final String name, final long quantity,
               final String unit, final BigDecimal price,
               final BigDecimal amount) {
        this.index = index;
        this.lineIndex = lineIndex;
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
        this.price = price;
        this.amount = amount;
    }

    public List<Integer> getLineIndex() {
        return lineIndex;
    }

    public long getQuantity() {
        return quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public BigDecimal getAmount() {
        return amount;
    }


    public void setIndex(final int index) {
        this.index = index;
    }

    public void setLineIndex(final List<Integer> lineIndex) {
        this.lineIndex = lineIndex;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setQuantity(final long quantity) {
        this.quantity = quantity;
    }

    public void setUnit(final String unit) {
        this.unit = unit;
    }

    public void setPrice(final BigDecimal price) {
        this.price = price;
    }

    public void setAmount(final BigDecimal amount) {
        this.amount = amount;
    }
}
