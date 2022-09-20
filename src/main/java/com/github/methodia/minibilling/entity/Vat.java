package com.github.methodia.minibilling.entity;

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
    @Column(name = "id",
            nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "index")
    private int index;
    @ElementCollection
    private List<Integer> lines;
    @ElementCollection
    private List<Integer> taxes;
    @Column(name = "taxed_amount_percentage")
    private BigDecimal taxedAmountPercentage;
    @Column(name = "percentage")
    private BigDecimal percentage;
    @Column(name = "amount")
    private BigDecimal amount;

    public Vat() {
    }

    public Vat(final int index, final List<Integer> lines, final List<Integer> taxes,
               final BigDecimal taxedAmountPercentage,
               final BigDecimal percentage, final BigDecimal amount) {
        this.index = index;
        this.lines = lines;
        this.taxes = taxes;
        this.taxedAmountPercentage = taxedAmountPercentage;
        this.percentage = percentage;
        this.amount = amount;
    }


    public List<Integer> getLines() {
        return lines;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
