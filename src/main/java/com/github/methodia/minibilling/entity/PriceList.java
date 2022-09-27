package com.github.methodia.minibilling.entity;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.List;

@Entity
@Table(name = "price_lists")
public class PriceList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "price_list_id")
    String id;

    @OneToMany(fetch = FetchType.EAGER,
            cascade = CascadeType.ALL)
    @JoinColumn(name = "price_list_id",
            insertable = false,
            updatable = false)
    private List<Price> priceList;


    public PriceList() {
    }

    public PriceList(final String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public List<Price> getPriceList() {
        return priceList.stream().map(PriceList::setZone).toList();
    }

    private static Price setZone(final Price price) {
        final ZonedDateTime withZoneSameInstantStart = price.getStart();
        final ZonedDateTime withZoneSameInstantEnd = price.getEnd().with(LocalTime.of(23, 59, 59));
        price.setStart(withZoneSameInstantStart);
        price.setEnd(withZoneSameInstantEnd);
        return price;
    }
}
