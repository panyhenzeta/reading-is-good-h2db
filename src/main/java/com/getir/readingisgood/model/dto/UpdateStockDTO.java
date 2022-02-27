package com.getir.readingisgood.model.dto;

import com.getir.readingisgood.model.Book;
import com.getir.readingisgood.model.BookStock;

public class UpdateStockDTO {
    private String bookName;
    private Long stock;

    public UpdateStockDTO(){}

    public UpdateStockDTO(BookStock bookStock) {
        this.bookName = bookStock.getBook().getName();
        this.stock = bookStock.getStock();
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public Long getStock() {
        return stock;
    }

    public void setStock(Long stock) {
        this.stock = stock;
    }
}
