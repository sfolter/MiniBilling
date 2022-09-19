package com.github.methodia.minibilling.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.List;

@Entity
@Table(name = "price_lists")
public class PriceList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "price_list_id")
    int id;

    @OneToMany(fetch = FetchType.EAGER,
            cascade = CascadeType.ALL)
    @JoinColumn(name = "price_list_id",
            insertable = false,
            updatable = false)
    private List<Price> priceList;


    public PriceList() {
    }

    public PriceList(final int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public List<Price> getPriceList() {
        return priceList.stream().map(price -> setZone(price)).toList();
    }

    private Price setZone(Price price) {
        ZonedDateTime withZoneSameInstantStart = price.getStart();
        ZonedDateTime withZoneSameInstantEnd = price.getEnd().with(LocalTime.of(23, 59, 59));
        price.setStart(withZoneSameInstantStart);
        price.setEnd(withZoneSameInstantEnd);
        return price;
    }
}
