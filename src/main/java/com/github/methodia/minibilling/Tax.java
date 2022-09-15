package com.github.methodia.minibilling;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
    @Column(name = "indexes")
    private  int index;
    @ElementCollection
    private  List<Integer> lines;
    @Column(name = "names")
    private  String name;
    @Column(name = "quantities")
    private  BigDecimal quantity;
    @Column(name = "units")
    private  String unit;
    @Column(name = "prices")
    private  BigDecimal price;
    @Column(name = "amounts")
    private  BigDecimal amount;

//    @ManyToOne(cascade = CascadeType.ALL)
//    @JoinColumn( name = "document_numbers")
//
//    Invoice invoice;


    public Tax() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Tax(int index, List<Integer> lines, BigDecimal quantity, BigDecimal amount) {
        this.index = index;
        this.lines = lines;
        this.name = "Standing charge";
        this.quantity = quantity;
        this.unit = "days";
        this.price = new BigDecimal("1.6");
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
