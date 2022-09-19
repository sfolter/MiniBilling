package com.github.methodia.minibilling.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Entity
@Table(name = "prices")
public class Price {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "price_list_id")
    int priceList;
    @Column(name = "product",
            nullable = false)
    private String product;
    @Column(name = "start_date",
            nullable = false)
    private ZonedDateTime start;
    @Column(name = "end_date",
            nullable = false)
    private ZonedDateTime end;
    @Column(name = "price",
            nullable = false)
    private BigDecimal value;

    public Price() {
    }

    public Price(final String product, final ZonedDateTime start, final ZonedDateTime end, final BigDecimal value) {
        this.product = product;
        this.start = start;
        this.end = end;
        this.value = value;
    }

    public Price(final int id, final String product, final ZonedDateTime start, final ZonedDateTime end,
                 final BigDecimal value) {
        this.id = id;
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

    public void setStart(final ZonedDateTime start) {
        this.start = start;
    }

    public void setEnd(final ZonedDateTime end) {
        this.end = end;
    }

    @Override
    public String toString() {
        return "Price{" +
                "product='" + product + '\'' +
                ", start=" + start +
                ", end=" + end +
                ", value=" + value +
                '}';
    }
}
