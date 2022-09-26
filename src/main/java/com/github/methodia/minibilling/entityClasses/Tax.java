package com.github.methodia.minibilling.entityClasses;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "taxes")
public class Tax {

    @Id
    @Column(name = "id",
            nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(name = "index")
    private int index;
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

    //    @ManyToOne(cascade = CascadeType.ALL)
    //    @JoinColumn( name = "document_numbers",referencedColumnName = "document_numbers")
    //    Invoice invoice;
    //
    //    public void setInvoice(Invoice invoice) {
    //        this.invoice = invoice;
    //    }

    public Tax() {
    }

    public Tax(final int index, final List<Integer> lines, final BigDecimal quantity, final BigDecimal amount) {
        this.index = index;
        this.lines = lines;
        name = "Standing charge";
        this.quantity = quantity;
        unit = "days";
        price = new BigDecimal("1.6");
        this.amount = amount;
    }

    public BigDecimal getAmount() {
        return amount;
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
}
