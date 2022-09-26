package com.github.methodia.minibilling.entityClasses;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "vats")
public class Vat {

    @Id
    @Column(name = "id",
            nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

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

    //    @ManyToOne
    //    @JoinColumn( name = "document_numbers",referencedColumnName = "document_numbers")
    //    Invoice invoice;


    public Vat() {
    }



    public Vat(final int index, final List<Integer> lines, final BigDecimal taxedAmountPercentage,
               final String percentage, final BigDecimal amount) {
        this.index = index;
        this.lines = lines;
        taxes = new ArrayList<>();
        this.taxedAmountPercentage = taxedAmountPercentage;
        this.percentage = new BigDecimal(String.valueOf(percentage));
        this.amount = amount;

    }

    public Vat(final int index, final BigDecimal taxedAmountPercentage, final String percentage,
               final BigDecimal amount, final List<Integer> taxes) {
        this(index, new ArrayList<>(), taxedAmountPercentage, percentage, amount);
        this.taxes.addAll(taxes);
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public List<Integer> getLines() {
        return lines;
    }


}
