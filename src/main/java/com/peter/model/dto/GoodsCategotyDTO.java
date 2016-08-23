package com.peter.model.dto;

/**
 * Created by andreajacobsson on 2016-08-23.
 */
public class GoodsCategotyDTO {

    private int id;
    private String category;
    private double unitPrice;

    public GoodsCategotyDTO(int id, String category, double unitPrice) {
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
        return "GOODSCATEGORY[id: "+ id + ", category: " + category + ", unitprice. " + unitPrice+"]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GoodsCategotyDTO)) return false;

        GoodsCategotyDTO that = (GoodsCategotyDTO) o;

        if (id != that.id) return false;
        if (Double.compare(that.unitPrice, unitPrice) != 0) return false;
        return category != null ? category.equals(that.category) : that.category == null;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id;
        result = 31 * result + (category != null ? category.hashCode() : 0);
        temp = Double.doubleToLongBits(unitPrice);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
