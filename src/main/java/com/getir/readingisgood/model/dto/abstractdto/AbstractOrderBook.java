package com.getir.readingisgood.model.dto.abstractdto;

public abstract class AbstractOrderBook {
    protected int quantity;

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
