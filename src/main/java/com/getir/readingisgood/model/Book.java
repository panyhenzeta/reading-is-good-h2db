package com.getir.readingisgood.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Book {
    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private Double price;

    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "book")
    public BookStock bookStock;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public BookStock getBookStock() {
        return bookStock;
    }

    public void setBookStock(BookStock bookStock) {
        this.bookStock = bookStock;
    }

}
