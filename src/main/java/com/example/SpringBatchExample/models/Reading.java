package com.example.SpringBatchExample.models;

import com.sun.istack.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Entity
@Table(name = "readings")
public class Reading {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "ref_number")
    private int refNumber;
    @Column(name = "product")
    private String product;
    @Column(name = "r_date")
    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private ZonedDateTime date;
    @Column(name = "value")
    private BigDecimal value;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ref_number",
            updatable = false,
            insertable = false)
    private User user;

    public Reading() {

    }

    public Reading(final ZonedDateTime date,final int refNumber, final BigDecimal value, final User user) {
        this.refNumber = refNumber;
        this.date = date;
        this.value = value;
        this.user = user;
    }



    public ZonedDateTime getDate() {
        return date;
    }

    public BigDecimal getValue() {
        return value;
    }

    public int getRefNumber() {
        return refNumber;
    }
}
