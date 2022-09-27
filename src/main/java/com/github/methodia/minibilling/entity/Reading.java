package com.github.methodia.minibilling.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Entity
@Table(name = "readings")
public class Reading {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private String id;

    @Column(name = "product",
            nullable = false)
    private String product;
    @Column(name = "ref_number",
            nullable = false)
    private String referentNumber;
    @Column(name = "date",
            nullable = false)
    private ZonedDateTime time;
    @Column(name = "value",
            nullable = false)
    private BigDecimal value;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ref_number",
            insertable = false,
            updatable = false)
    private User user;

    public Reading() {
    }

    public Reading(final String referentNumber, final ZonedDateTime time, final BigDecimal value, final User user) {
        this.referentNumber = referentNumber;
        this.time = time;
        this.value = value;
        this.user = user;
    }

    public ZonedDateTime getTime() {
        return time;
    }

    public BigDecimal getValue() {
        return value;
    }

    public User getUser() {
        return user;
    }

    public void setTime(final ZonedDateTime time) {
        this.time = time;
    }
}
