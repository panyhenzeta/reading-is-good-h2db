package com.getir.readingisgood.model.dto;

import com.getir.readingisgood.model.dto.abstractdto.AbstractCustomerOrder;
import com.getir.readingisgood.validator.annotation.BookStockInfo;

import javax.validation.constraints.NotNull;
import java.util.List;

public class CustomerOrderRequestDTO extends AbstractCustomerOrder {

    @NotNull
    private Long customerId;

    @BookStockInfo
    private List<OrderBookRequestDTO> bookOrders;

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public List<OrderBookRequestDTO> getBookOrders() {
        return bookOrders;
    }

    public void setBookOrders(List<OrderBookRequestDTO> bookOrders) {
        this.bookOrders = bookOrders;
    }
}
