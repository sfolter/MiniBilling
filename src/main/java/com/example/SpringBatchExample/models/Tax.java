package com.example.SpringBatchExample.models;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "tax")
public class Tax {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "index")
    private int index;
    @Column(name = "lines")
    @ElementCollection
    private List<Integer> lines;
    @Column(name = "name")
    private String name;
    @Column(name = "quantity")
    private BigDecimal quantity;
    @Column(name = "unit")
    private String unit;
    @Column(name = "price")
    private BigDecimal price;
    @Column(name = "amount")
    private BigDecimal amount;

    Tax() {
    }

    public Tax(final int index, final List<Integer> lines, final BigDecimal quantity, final BigDecimal price,
               final BigDecimal amount) {
        this.index = index;
        this.lines = lines;
        name = "Standing charge";
        this.quantity = quantity;
        unit = "days";
        this.price = price;
        this.amount = amount;
    }

    public int getIndex() {
        return index;
    }

    public List<Integer> getLines() {
        return lines;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "Tax{" +
                "id=" + id +
                ", index=" + index +
                ", lines=" + lines +
                ", name='" + name + '\'' +
                ", quantity=" + quantity +
                ", unit='" + unit + '\'' +
                ", price=" + price +
                ", amount=" + amount +
                '}';
    }
}
