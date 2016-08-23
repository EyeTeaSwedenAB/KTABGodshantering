package com.peter.model.dto;

import java.time.LocalDate;

/**
 * Created by andreajacobsson on 2016-08-23.
 */
public class OrderTransferDTO {

    private final int id;
    private final LocalDate date;
    private final int invoiceRecieverId;
    private final int accountId;
    private final int goodsCategoryId;
    private final int noOfUnits;
    private final double totalPrice;

    public OrderTransferDTO(int id, LocalDate date, int invoiceRecieverId, int accountId, int goodsCategoryId, int noOfUnits, double totalPrice) {
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

    public LocalDate getDate() {
        return date;
    }

    public int getInvoiceRecieverId() {
        return invoiceRecieverId;
    }

    public int getAccountId() {
        return accountId;
    }

    public int getGoodsCategoryId() {
        return goodsCategoryId;
    }

    public int getNoOfUnits() {
        return noOfUnits;
    }

    public double getTotalPrice() {
        return totalPrice;
    }
}
