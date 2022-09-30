package com.example.SpringBatchExample.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "price_list_id")
    int id;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "price_list_id",
            insertable = false,
            updatable = false)
    List<Price> priceList;

    public PriceList(final int id, final List<Price> priceList) {
        this.id = id;
        this.priceList = priceList;
    }

    public PriceList() {

    }

    public List<Price> getPriceList() {
        return priceList;
    }
}
