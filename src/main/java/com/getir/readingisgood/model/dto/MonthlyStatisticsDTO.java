package com.getir.readingisgood.model.dto;

public class MonthlyStatisticsDTO {
    private String month;
    private int totalOrder;
    private Long totalBookCount;
    private Double totalPurchasedAmount;

    public MonthlyStatisticsDTO(String month, int totalOrder, Long totalBookCount, Double totalPurchasedAmount) {
        this.month = month;
        this.totalOrder = totalOrder;
        this.totalBookCount = totalBookCount;
        this.totalPurchasedAmount = totalPurchasedAmount;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public int getTotalOrder() {
        return totalOrder;
    }

    public void setTotalOrder(int totalOrder) {
        this.totalOrder = totalOrder;
    }

    public Long getTotalBookCount() {
        return totalBookCount;
    }

    public void setTotalBookCount(Long totalBookCount) {
        this.totalBookCount = totalBookCount;
    }

    public Double getTotalPurchasedAmount() {
        return totalPurchasedAmount;
    }

    public void setTotalPurchasedAmount(Double totalPurchasedAmount) {
        this.totalPurchasedAmount = totalPurchasedAmount;
    }
}
