package com.peter.dtos;

import java.time.LocalDate;

/**
 * Created by andreajacobsson on 2016-08-24.
 */
public class TransformedOrderDataDTO {

    private final LocalDate date;
    private final InvoiceRecieverDTO invoiceReciever;
    private final AccountDTO destination;
    private final GoodsCategoryDTO goodsCategory;
    private final int noOfUnits;
    private final double unitPrice;
    private final double totalPrice;


    public TransformedOrderDataDTO(LocalDate date, InvoiceRecieverDTO invoiceReciever, AccountDTO destination,
                                   GoodsCategoryDTO goodsCategory, int noOfUnits, double unitPrice, double totalPrice) {

        this.date = date;
        this.invoiceReciever = invoiceReciever;
        this.destination = destination;
        this.goodsCategory = goodsCategory;
        this.noOfUnits = noOfUnits;
        this.unitPrice = unitPrice;
        this.totalPrice = totalPrice;
    }

    public LocalDate getDate() {
        return date;
    }

    public InvoiceRecieverDTO getInvoiceReciever() {
        return invoiceReciever;
    }

    public AccountDTO getDestination() {
        return destination;
    }

    public GoodsCategoryDTO getGoodsCategory() {
        return goodsCategory;
    }

    public int getNoOfUnits() {
        return noOfUnits;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

}
