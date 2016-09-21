package com.peter.model.data;

/**
 * Created by andreajacobsson on 2016-08-23.
 */

public class RawOrderData {

    private int id;
    private String date;
    private int invoiceRecieverId;
    private int accountId;
    private int goodsCategoryId;
    private int noOfUnits;
    private double totalPrice;
    private String comment;
    private int invoiceSent;


    public RawOrderData(int id, String date, int invoiceRecieverId, int accountId,
                        int goodsCategoryId, int noOfUnits, double totalPrice, String comment, int invoiceSent) {
        this.id = id;
        this.date = date;
        this.invoiceRecieverId = invoiceRecieverId;
        this.accountId = accountId;
        this.goodsCategoryId = goodsCategoryId;
        this.noOfUnits = noOfUnits;
        this.totalPrice = totalPrice;
        this.comment = comment;
        this.invoiceSent = invoiceSent;
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


    public String getComment() {
        return comment;
    }


    public int getInvoiceSent() {
        return invoiceSent;
    }
}
