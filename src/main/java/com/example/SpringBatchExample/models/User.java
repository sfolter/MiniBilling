package com.example.SpringBatchExample.models;

import com.sun.istack.NotNull;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "name")
    @NotNull
    private String name;
    @Column(name = "ref_number")
    private int refNumber;
    @Column(name = "price_list_id")
    private int numberPricingList;
    @OneToMany(mappedBy = "user",
            cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    private Set<Reading> reading;

    @ManyToOne(cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinColumn(name = "price_list_id",
            updatable = false,
            insertable = false)
    PriceList prices;

    public User() {
    }

    public String getName() {
        return name;
    }

    public int getNumberPricingList() {
        return numberPricingList;
    }

    public PriceList getPrices() {
        return prices;
    }

    public int getRefNumber() {
        return refNumber;
    }

    @Override
    public String toString() {
        return "User{" +
                //  "id=" + id +
                ", name='" + name + '\'' +
                ", refNumber=" + refNumber +
                ", numberPricingList=" + numberPricingList +
                ", reading=" + reading +
                ", prices=" + prices +
                '}';
    }
}