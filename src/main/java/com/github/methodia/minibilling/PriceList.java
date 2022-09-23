package com.github.methodia.minibilling;

/**
 * @author Todor Todorov
 * @Date 23.09.2022
 * Methodia Inc.
 */

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "price_list")
public class PriceList {

    @Id
    @Column(name = "num")
    private int num;

    @OneToMany(mappedBy = "priceList")
    List<Price> prices;

    public PriceList() {
    }

    public PriceList(final int num, final List<Price> prices) {
        this.num = num;
        this.prices = prices;
    }

    public int getNum() {
        return num;
    }

    public void setNum(final int num) {
        this.num = num;
    }

    public List<Price> getPrices() {
        return prices;
    }

    public void setPrices(final List<Price> prices) {
        this.prices = prices;
    }

    @Override
    public String toString() {
        return "PriceList{" +
                "num=" + num +
                ", prices=" + prices +
                '}';
    }
}

