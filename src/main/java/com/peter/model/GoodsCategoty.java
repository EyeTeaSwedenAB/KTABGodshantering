package com.peter.model;

/**
 * Created by andreajacobsson on 2016-08-23.
 */
public class GoodsCategoty {

    private int id;
    private String category;
    private double unitPrice;

    public GoodsCategoty(int id, String category, double unitPrice) {
        this.id = id;
        this.category = category;
        this.unitPrice = unitPrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }
}
