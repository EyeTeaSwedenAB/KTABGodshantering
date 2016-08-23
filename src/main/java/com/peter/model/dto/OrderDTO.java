package com.peter.model.dto;

import java.time.LocalDate;

/**
 * Created by andreajacobsson on 2016-08-23.
 */
public class OrderDTO {

    private int id;
    private LocalDate date;
    private int invoiceRecieverId;
    private int accountId;
    private int goodsCategoryId;
    private int noOfUnits;
    private double totalPrice;


    public OrderDTO(int id, LocalDate date, int invoiceRecieverId, int accountId, int goodsCategoryId, int noOfUnits, double totalPrice) {
        this.id = id;
        this.date = date;
        this.invoiceRecieverId = invoiceRecieverId;
        this.accountId = accountId;
        this.goodsCategoryId = goodsCategoryId;
        this.noOfUnits = noOfUnits;
        this.totalPrice = totalPrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getInvoiceRecieverId() {
        return invoiceRecieverId;
    }

    public void setInvoiceRecieverId(int invoiceRecieverId) {
        this.invoiceRecieverId = invoiceRecieverId;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public int getGoodsCategoryId() {
        return goodsCategoryId;
    }

    public void setGoodsCategoryId(int goodsCategoryId) {
        this.goodsCategoryId = goodsCategoryId;
    }

    public int getNoOfUnits() {
        return noOfUnits;
    }

    public void setNoOfUnits(int noOfUnits) {
        this.noOfUnits = noOfUnits;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderDTO)) return false;

        OrderDTO orderDTO = (OrderDTO) o;

        if (id != orderDTO.id) return false;
        if (invoiceRecieverId != orderDTO.invoiceRecieverId) return false;
        if (accountId != orderDTO.accountId) return false;
        if (goodsCategoryId != orderDTO.goodsCategoryId) return false;
        if (noOfUnits != orderDTO.noOfUnits) return false;
        if (Double.compare(orderDTO.totalPrice, totalPrice) != 0) return false;
        return date != null ? date.equals(orderDTO.date) : orderDTO.date == null;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + invoiceRecieverId;
        result = 31 * result + accountId;
        result = 31 * result + goodsCategoryId;
        result = 31 * result + noOfUnits;
        temp = Double.doubleToLongBits(totalPrice);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
