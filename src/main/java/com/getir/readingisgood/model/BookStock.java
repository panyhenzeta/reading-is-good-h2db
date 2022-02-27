package com.getir.readingisgood.model;

import com.sun.istack.NotNull;

import javax.persistence.*;

@Entity
public class BookStock {

    public BookStock() {}

    public BookStock(Long stock) {
        this.stock = stock;
    }

    public BookStock(Long id, Long stock, int version) {
        this.id = id;
        this.stock = stock;
        this.version = version;
    }

    @Id
    @GeneratedValue
    private Long id;
    private Long stock;

    @NotNull
    @OneToOne()
    private Book book;

    @Version
    @Column(columnDefinition = "integer DEFAULT 0")
    private int version;

    public Long getStock() {
        return stock;
    }

    public void setStock(Long stock) {
        this.stock = stock;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
