package com.peter.dtos;

/**
 * Created by andreajacobsson on 2016-08-23.
 */
public class GoodsCategoryDTO {

    public final int ID;
    public final String CATEGORY;
    public final double UNIT_PRICE;

    public GoodsCategoryDTO(int ID, String CATEGORY, double UNIT_PRICE) {
        this.ID = ID;
        this.CATEGORY = CATEGORY;
        this.UNIT_PRICE = UNIT_PRICE;
    }

    @Override
    public String toString() {
        return CATEGORY;
    }
}
