package com.github.methodia.minibilling;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Table(name = "price_list")
public class PriceLists {

    @Id
    @Column(name = "price_list_id")
    private int num;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "price_list_id",insertable = false,updatable = false)
    private Set<Price> priceList;


    public PriceLists() {}

    public PriceLists(final int num, final Set<Price> prices) {
        this.num = num;
        this.priceList = prices;
    }

    public int getNum() {
        return num;
    }

    public void setNum(final int num) {
        this.num = num;
    }

    public Set<Price> getPrices() {
        return priceList;
    }

    public void setPrices(final Set<Price> prices) {
        this.priceList = prices;
    }

}
