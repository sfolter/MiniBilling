package com.github.methodia.minibilling;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "users")
public class User {
    @Column(name = "user_name")
    private String name;
    @Id
    @Column(name = "ref_num")
    private String ref;
    @ManyToOne
    @JoinColumn(name = "price_list_num",
            insertable = false,
            updatable = false)
    private PriceLists priceList;

    @OneToMany(fetch = FetchType.EAGER,
            cascade = CascadeType.ALL)
    @JoinColumn(name = "ref_num",
            insertable = false,
            updatable = false)
    private List<Reading> readingsList;


    public User(String name, String ref, PriceLists priceListNumber, List<Reading> readingsList) {
        this.name = name;
        this.ref = ref;
        this.readingsList = readingsList;
        this.priceList = priceListNumber;
    }

    public User() {}

    public String getName() {
        return name;
    }
    public String getRef() {
        return ref;
    }

    public List<Reading> getPrice() {
        return readingsList;
    }
    public PriceLists getPriceListNumber() {
        return priceList;
    }

    public PriceLists getPriceList() {
        return priceList;
    }
}
