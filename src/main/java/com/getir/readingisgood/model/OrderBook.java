package com.getir.readingisgood.model;

import javax.persistence.*;

@Entity
public class OrderBook {

    public OrderBook(CustomerOrder order, Book book, int quantity) {
        this.order = order;
        this.book = book;
        this.quantity = quantity;
    }

    public OrderBook() {}

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private CustomerOrder order;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    private int quantity;

    public CustomerOrder getOrder() {
        return order;
    }

    public void setOrder(CustomerOrder order) {
        this.order = order;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
