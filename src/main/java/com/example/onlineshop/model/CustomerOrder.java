package com.example.onlineshop.model;

import jakarta.persistence.*;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;


@Setter
@Entity
@Table(name = "customer_order")
public class CustomerOrder extends BaseEntity {
    private BigDecimal amount;
    private Date dateCreated;
    private Integer confirmationNumber;
    private Customer customer;

    Set<OrderedProduct> orderedProducts;

    @Column(name = "amount", nullable = false, precision = 9, scale = 2)
    public BigDecimal getAmount() {
        return amount;
    }

    @Basic(optional = false)
    @Column(name = "date_created", insertable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getDateCreated() {
        return dateCreated;
    }

    @Column(name = "confirmation_number", nullable = false)
    public Integer getConfirmationNumber() {
        return confirmationNumber;
    }

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)

    public Customer getCustomer() {
        return customer;
    }

    @OneToMany(mappedBy = "customerOrder")
    public Set<OrderedProduct> getOrderedProducts(){
        return orderedProducts;
    }
}
