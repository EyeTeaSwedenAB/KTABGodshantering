package com.peter.dto;

/**
 * Created by andreajacobsson on 2016-08-24.
 */
public class OrderDTO {

    private final int id;
    private final String date;
    private final String invoiceReciever;
    private final String destination;
    private final String goodsCategory;
    private final int noOfUnits;
    private final double unitPrice;
    private final double totalPrice;
    private final String comment;


    public OrderDTO(int id, String date, String invoiceReciever, String destination, String goodsCategory, int noOfUnits, double unitPrice, double totalPrice, String comment) {
        this.id = id;
        this.date = date;
        this.invoiceReciever = invoiceReciever;
        this.destination = destination;
        this.goodsCategory = goodsCategory;
        this.noOfUnits = noOfUnits;
        this.unitPrice = unitPrice;
        this.totalPrice = totalPrice;
        this.comment = comment;
    }

    public int getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public String getInvoiceReciever() {
        return invoiceReciever;
    }

    public String getDestination() {
        return destination;
    }

    public String getGoodsCategory() {
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

    public String getComment() {
        return comment;
    }
}
