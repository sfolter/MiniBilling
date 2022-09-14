package com.github.methodia.minibilling;

import javax.persistence.*;
import java.beans.ConstructorProperties;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Entity
@Table(name = "readings")
public class Reading {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    int id;

    @Column(name = "ref_number")
    String refNumber;
    @Column(name = "product")
    private  String product;
    @Column (name = "date")
    private  ZonedDateTime date;
    @Column(name = "value")
    private  BigDecimal value;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ref_number",insertable = false,updatable = false)
    private User user;

    public Reading() {}

    public Reading(String refNumber, String product, ZonedDateTime date, BigDecimal value) {
        this.refNumber=refNumber;
        this.product = product;
        this.date = date;
        this.value = value;
    }

    public User getUsers() {
        return user;
    }

    public String getRefNumber() {
        return refNumber;
    }

    public String getProduct() {
        return product;
    }

    public ZonedDateTime  getDate() {
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

