package com.github.methodia.minibilling;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "readings")
public class Reading {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "date")
    private ZonedDateTime time;
    @Column(name = "pokazanie")
    private BigDecimal value;
    @ManyToOne
    @JoinColumn(name = "ref_num",
            insertable = false,
            updatable = false)
    private User user;
    @Column(name = "product")
    private String product;

    public Reading(ZonedDateTime time, BigDecimal value, User user, String product) {
        this.time = time;
        this.value = value;
        this.user = user;
        this.product = product;
    }

    public Reading() {
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

    public String getProduct() {
        return product;
    }
}
