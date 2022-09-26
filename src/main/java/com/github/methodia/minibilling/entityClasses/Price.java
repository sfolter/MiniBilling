package com.github.methodia.minibilling.entityClasses;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "prices")
public class Price {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    int id;

    @Column(name = "price_list_id")
    int priceListId;
    @Column(name = "product")
    private String product;
    @Column(name = "start_date")
    private LocalDate startDate;
    @Column(name = "end_date")
    private LocalDate endDate;
    @Column(name = "price")
    private BigDecimal price;

    //    @ManyToOne
    //    @JoinColumn(name = "price_list_id")
    //    private Users users;

    public Price() {
    }

    public Price(final String product, final LocalDate startDate, final LocalDate endDate, final BigDecimal price) {
        this.product = product;
        this.startDate = startDate;
        this.endDate = endDate;
        this.price = price;
    }

    public String getProduct() {
        return product;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public BigDecimal getPrice() {
        return price;
    }

    //    public Users getUsers() {
    //        return users;
    //    }
    //
    //    public void setUsers(Users users) {
    //        this.users = users;
    //    }
}