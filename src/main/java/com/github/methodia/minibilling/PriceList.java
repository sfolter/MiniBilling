package com.github.methodia.minibilling;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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

    @OneToMany(mappedBy = "pricesList")
    List<Price> prices;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public PriceList(int id, List<Price> prices) {
        this.id = id;
        this.prices = prices;
    }
    public PriceList(){}
}
