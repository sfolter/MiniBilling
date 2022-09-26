package com.github.methodia.minibilling.entityClasses;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Entity
@Table(name = "readings")
public class Reading {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",
            nullable = false)
    int id;

    @Column(name = "ref_number")
    String refNumber;
    @Column(name = "product")
    private String product;
    @Column(name = "date")
    private ZonedDateTime date;
    @Column(name = "value")
    private BigDecimal value;

    public Reading() {
    }

    public Reading(final String refNumber, final String product, final ZonedDateTime date, final BigDecimal value) {
        this.refNumber = refNumber;
        this.product = product;
        this.date = date;
        this.value = value;
    }



    public String getRefNumber() {
        return refNumber;
    }

    public String getProduct() {
        return product;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public BigDecimal getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Readings{" +
                " product='" + product + '\'' +
                ", date=" + date +
                ", value=" + value +
                '}';
    }
}

