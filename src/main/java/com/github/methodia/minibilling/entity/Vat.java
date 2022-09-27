package com.github.methodia.minibilling.entity;


import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "vat")
public class Vat {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "index")
    private int index;
    @ElementCollection
    private List<Integer> lines = new ArrayList<>();
    @ElementCollection
    private List<Integer> taxes = new ArrayList<>();
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

    public void setIndex(final int index) {
        this.index = index;
    }

    public void setLines(final List<Integer> lines) {
        this.lines = lines;
    }

    public void setTaxes(final List<Integer> taxes) {
        this.taxes = taxes;
    }

    public void setTaxedAmountPercentage(final BigDecimal taxedAmountPercentage) {
        this.taxedAmountPercentage = taxedAmountPercentage;
    }

    public void setPercentage(final BigDecimal percentage) {
        this.percentage = percentage;
    }

    public void setAmount(final BigDecimal amount) {
        this.amount = amount;
    }
}
