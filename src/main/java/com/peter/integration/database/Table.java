package com.peter.integration.database;

/**
 * Created by andreajacobsson on 2016-08-23.
 */
enum Table {

    ACCOUNTS("accounts"),
    GOODS_CATEGORIES("goodscategories"),
    INVOICE_RECIEVERS("invoicerecievers"),
    ORDERS("orders");


    private final String name;


    Table(String name){
        this.name = name;
    }

    @Override
    public String toString() {

        return this.name;
    }
}
