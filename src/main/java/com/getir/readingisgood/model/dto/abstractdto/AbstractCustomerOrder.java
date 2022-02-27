package com.getir.readingisgood.model.dto.abstractdto;

public abstract class AbstractCustomerOrder {
    protected Double totalPrice;

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
