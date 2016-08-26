package com.peter.model;

/**
 * Created by andreajacobsson on 2016-08-23.
 */

public class RawOrderData {

    private int id;
    private String date;
    private int invoiceRecieverId;
    private int anIntccountId;
    private int goodsCategoryId;
    private int noOfUnits;
    private double totalPrice;
    private String comment;


    public RawOrderData(int id, String date, int invoiceRecieverId, int anIntccountId, int goodsCategoryId, int noOfUnits, double totalPrice, String comment) {
        this.id = id;
        this.date = date;
        this.invoiceRecieverId = invoiceRecieverId;
        this.anIntccountId = anIntccountId;
        this.goodsCategoryId = goodsCategoryId;
        this.noOfUnits = noOfUnits;
        this.totalPrice = totalPrice;
        this.comment = comment;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getInvoiceRecieverId() {
        return invoiceRecieverId;
    }

    public void setInvoiceRecieverId(int invoiceRecieverId) {
        this.invoiceRecieverId = invoiceRecieverId;
    }

    public int getAccountId() {
        return anIntccountId;
    }

    public void setAnIntccountId(int anIntccountId) {
        this.anIntccountId = anIntccountId;
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

    public String getComment() {
        return comment;
    }
}
