package com.getir.readingisgood.model.dto;

import com.getir.readingisgood.model.OrderBook;
import com.getir.readingisgood.model.dto.abstractdto.AbstractOrderBook;

public class OrderBookResponseDTO extends AbstractOrderBook {
    private String bookName;

    public OrderBookResponseDTO(OrderBook orderBook){
        this.bookName = orderBook.getBook().getName();
        this.quantity = orderBook.getQuantity();
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }
}
