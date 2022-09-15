package com.github.methodia.minibilling;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.transaction.Status;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "vats")
public class Vat  {

    @Id
    @Column(name = "id",
            nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "indexes")
    private  int index;

    @ElementCollection

    private  List<Integer> lines;
    @ElementCollection

    private  List<Integer> taxes;
    @Column(name = "taxed_amount_percentages")
    private  BigDecimal taxedAmountPercentage;
    @Column(name = "percentages")
    private  BigDecimal percentage;
    @Column(name = "amounts")
    private  BigDecimal amount;

//    @ManyToOne
//    @JoinColumn(name = "document_numbers")
//    Invoice invoice;
//

    public Vat() {
    }




    public Vat(int index, List<Integer> lines, BigDecimal taxedAmountPercentage, String percentage, BigDecimal amount) {
        this.index = index;
        this.lines = lines;
        this.taxes = new ArrayList<>();
        this.taxedAmountPercentage = taxedAmountPercentage;
        this.percentage = new BigDecimal(String.valueOf(percentage));
        this.amount = amount;

    }

    public Vat(int index, BigDecimal taxedAmountPercentage, String percentage, BigDecimal amount, List<Integer> taxes) {
        this(index, new ArrayList<>(),taxedAmountPercentage, percentage, amount);
        this.taxes.addAll(taxes);
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public List<Integer> getLines() {
        return lines;
    }


}
