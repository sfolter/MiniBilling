package com.github.methodia.minibilling;

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
    @Column(name = "refNum")
    private int id;
    @Column(name = "date")
    private ZonedDateTime time;
    @Column(name = "measurment")
    private BigDecimal value;
    @ManyToOne
    @JoinColumn(name = "user_ref",
            insertable = false,
            updatable = false)
    private User user;
    @Column(name = "product")
    private String product;

    public Reading() {
    }

    public Reading(final User user, final String product, final ZonedDateTime time, final BigDecimal value  ) {
        this.time = time;
        this.value = value;
        this.user = user;
        this.product = product;
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

    @Override
    public String toString() {
        return "Reading{" +
                "time=" + time +
                ", value=" + value +
                ", user=" + user +
                ", product='" + product + '\'' +
                '}';
    }
}
