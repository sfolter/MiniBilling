package com.example.SpringBatchExample.models;

import com.sun.istack.NotNull;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Entity
@Table(name = "prices")
public class Price {

    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "price_list_id")
    private int priceListId;
    @Column(name = "product")
    private String product;
    @Column(name = "start_date")
    @NotNull
    private ZonedDateTime start;
    @Column(name = "end_date")
    @NotNull
    private ZonedDateTime end;
    @Column(name = "price")
    private BigDecimal value;


    public Price() {

    }

    public Price(final String product, final ZonedDateTime start, final ZonedDateTime end, final BigDecimal value) {
        this.product = product;
        this.start = start;
        this.end = end;
        this.value = value;
    }

    public String getProduct() {
        return product;
    }

    public ZonedDateTime getStart() {
        return start;
    }

    public ZonedDateTime getEnd() {
        return end;
    }

    public BigDecimal getValue() {
        return value;
    }



}
