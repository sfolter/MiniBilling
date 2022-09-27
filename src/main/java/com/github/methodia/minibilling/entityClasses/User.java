package com.github.methodia.minibilling.entityClasses;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "users")
public class User implements Serializable {

    @Id
    @Column(name = "ref_number")
    private String refNumber;
    @Column(name = "name")
    private String name;
    @ManyToOne
    @JoinColumn(name = "price_list_id")
    private PriceList pricesList;

    @OneToMany
            @JoinColumn(name = "ref_number")
    List<Reading> readingsList;

    @Column(name = "currency")
    String currency;

    public PriceList getPricesList() {
        return pricesList;
    }

    public void setPricesList(final PriceList pricesList) {
        this.pricesList = pricesList;
    }

    //    @OneToMany(targetEntity = Prices.class,
    //            cascade = CascadeType.ALL,
    //            mappedBy = "users")
    //    private List<Prices> pricesList;

    public User() {
    }

    public User(final String referentNumber, final String name, final PriceList pricesList, final List<Reading> readingsList, final String currency) {
        refNumber = referentNumber;
        this.name = name;
        this.pricesList = pricesList;
        this.readingsList=readingsList;
        this.currency=currency;
    }


    public String getRefNumber() {
        return refNumber;
    }

    public String getName() {
        return name;
    }

    public PriceList getPriceList() {
        return pricesList;
    }

    public String getCurrency() {
        return currency;
    }

    public List<Reading> getReadingsList() {
        return readingsList;
    }
}
