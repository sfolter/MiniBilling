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
@Table(name = "vat")
public class Vat {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "index")
    private int index;
    @Column(name = "lines")
    @ElementCollection
    private List<Integer> lines;
    @Column(name = "taxes")
    @ElementCollection
    private List<Integer> taxes;
    @Column(name = "taxed_amount_percentage")
    private BigDecimal taxedAmountPercentage;
    @Column(name = "percentage")
    private BigDecimal percentage;
    @Column(name = "amount")
    private BigDecimal amount;

    Vat() {

    }

    public Vat(final int index, final List<Integer> lines, final List<Integer> tax,
               final BigDecimal taxedAmountPercentage,
               final BigDecimal percentage, final BigDecimal amount) {
        this.index = index;
        this.lines = lines;
        taxes = tax;
        this.taxedAmountPercentage = taxedAmountPercentage;
        this.percentage = percentage;
        this.amount = amount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public List<Integer> getLines() {
        return lines;
    }

    @Override
    public String toString() {
        return "Vat{" +
                "id=" + id +
                ", index=" + index +
                ", lines=" + lines +
                ", taxes=" + taxes +
                ", taxedAmountPercentage=" + taxedAmountPercentage +
                ", percentage=" + percentage +
                ", amount=" + amount +
                '}';
    }
}
