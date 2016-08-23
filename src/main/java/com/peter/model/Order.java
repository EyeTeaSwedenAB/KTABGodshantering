package com.peter.model;

import com.peter.model.dto.OrderTransferDTO;
import javafx.util.converter.LocalDateStringConverter;

import java.time.LocalDate;
import java.util.Date;

/**
 * Created by andreajacobsson on 2016-08-23.
 */
public class Order {

    private int id;
    private LocalDate date;
    private int invoiceRecieverId;
    private int accountId;
    private int goodsCategoryId;
    private int noOfUnits;
    private double totalPrice;

    public Order(OrderTransferDTO transferDTO) {
        this.id = transferDTO.getId();
        this.date = transferDTO.getDate();
        this.invoiceRecieverId = transferDTO.getInvoiceRecieverId();
        this.accountId = transferDTO.getAccountId();
        this.goodsCategoryId = transferDTO.getGoodsCategoryId();
        this.noOfUnits = transferDTO.getNoOfUnits();
        this.totalPrice = transferDTO.getTotalPrice();
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
}
