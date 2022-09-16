package com.github.methodia.minibilling;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Column(name = "name")
    private String name;
    @Id
    @Column(name = "ref")
    private String ref;

    @Column(name = "priceListNum")
    private int priceListNumber;

    @ManyToOne
    @JoinColumn(name = "priceListNum",
            insertable = false,
            updatable = false)
    private PriceList priceList;

    @OneToMany(mappedBy = "user")
    List<Reading> readingList;

    public User() {
    }

    public User(final String name, final String ref, final int priceListNumber) {
        this.name = name;
        this.ref = ref;

        this.priceListNumber = priceListNumber;
    }

    public String getName() {
        return name;
    }

    public String getRef() {
        return ref;
    }



    public int getPriceListNumber() {
        return priceListNumber;
    }

    public PriceList getPriceList() {
        return priceList;
    }

    public List<Reading> getReadingList() {
        return readingList;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", ref='" + ref + '\'' +
                ", priceListNumber=" + priceListNumber +
                '}';
    }
}
