package com.github.methodia.minibilling.entityClasses;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "price_lists")
public class PriceList {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "price_list_id")
    private int id;

    @OneToMany
            @JoinColumn(name = "price_list_id",insertable = false,updatable = false)
    public
    List<Price> prices;

    public int getId() {
        return id;
    }
    public PriceList(final int id, final List<Price> prices) {
        this.id = id;
        this.prices = prices;
    }
    public PriceList(){}
}
