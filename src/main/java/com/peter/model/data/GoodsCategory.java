package com.peter.model.data;

/**
 * Created by andreajacobsson on 2016-08-23.
 */
public class GoodsCategory {

    private int id;
    private String category;
    private double unitPrice;

    public GoodsCategory() {
    }

    public GoodsCategory(int id, String category, double unitPrice) {
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

    @Override
    public String toString() {
        return category;
    }
}
