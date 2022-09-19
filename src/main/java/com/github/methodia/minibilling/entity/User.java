package com.github.methodia.minibilling.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Column(name = "name",
            nullable = false)
    private String name;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ref_number",
            nullable = false)
    private String ref;
    @ManyToOne(targetEntity = PriceList.class)
    @JoinColumn(name = "price_list_id")
    private PriceList priceList;

    @Transient
    private List<Price> price;



    public User() {
    }

    public User(final String name, final String ref, final PriceList priceListNumber, final List<Price> price) {
        this.name = name;
        this.ref = ref;
        this.priceList = priceListNumber;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getRef() {
        return ref;
    }

    public PriceList getPriceListNumber() {
        return priceList;
    }

    public List<Price> getPrice() {
        if (price == null) {
            return priceList.getPriceList();
        } else {
            return price;
        }
    }

    public List<Price> getPrices() {
        return priceList.getPriceList();
    }
}
