package com.getir.readingisgood.model;

import com.getir.readingisgood.model.enums.OrderStatus;
import com.sun.istack.NotNull;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;
import java.util.Set;

@Entity
public class CustomerOrder {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    @NotNull
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToMany(mappedBy =  "order", cascade = CascadeType.ALL)
    private List<OrderBook> orderBooks;

    private Double totalPrice;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private Instant orderDate;

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<OrderBook> getOrderBooks() {
        return orderBooks;
    }

    public void setOrderBooks(List<OrderBook> orderBooks) {
        this.orderBooks = orderBooks;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Instant getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Instant orderDate) {
        this.orderDate = orderDate;
    }
}
