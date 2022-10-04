package com.github.methodia.minibilling;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "vat")
public class VatLine {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "index")
    private int index;
    @Column(name = "lines")
    private int lines;
    @Column(name = "percentage")
    private int percentage;
    @Column(name = "amount")
    private BigDecimal amount;
    @Column(name = "taxed_amount_percentage")
    private final int taxedAmountPercentage;
    @Column(name = "taxes")
    private final int taxes;


    public VatLine(int index, int lines, int taxes, int taxedAmountPercentage, int percentage, BigDecimal amount) {
        this.index = index;
        this.lines = lines;
        this.percentage = percentage;
        this.amount = amount;
        this.taxedAmountPercentage = taxedAmountPercentage;
        this.taxes = taxes;
    }

    public int getIndex() {
        return index;
    }

    public int getLines() {return lines;}

    public int getTaxes() {return taxes;}
    public int getTaxedAmountPercentage() {return taxedAmountPercentage;}

    public int getPercentage() {
        return percentage;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
