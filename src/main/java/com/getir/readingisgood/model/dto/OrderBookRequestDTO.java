package com.getir.readingisgood.model.dto;

import com.getir.readingisgood.model.dto.abstractdto.AbstractOrderBook;

public class OrderBookRequestDTO extends AbstractOrderBook {
    private Long bookId;

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }
}
