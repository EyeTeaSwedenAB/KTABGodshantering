package com.peter.observ;

import java.util.List;

/**
 * Created by andreajacobsson on 2016-08-28.
 */
public class GoodsCategoryUpdateEvent implements UpdateEvent<List<String>> {


    private List<String> goodsCategories;

    public GoodsCategoryUpdateEvent(List<String> goodsCategories){
        this.goodsCategories = goodsCategories;
    }
    @Override
    public List<String> getObject() {
        return goodsCategories;
    }
}
