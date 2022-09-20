package com.github.methodia.minibilling.entity;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "tax")
public class Tax {

    @Id
    @Column(name = "id",
            nullable = false)
    private int id;
    @Column(name = "index")
    private int index;
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

}
