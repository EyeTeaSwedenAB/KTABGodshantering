package com.peter.dto;

import java.time.LocalDate;

/**
 * Created by andreajacobsson on 2016-08-23.
 */
public class OrderDTO {

    private final int ID;
    private final LocalDate DATE;
    private final int INVOICE_RECIEVER_ID;
    private final int ACCOUNT_ID;
    private final int GOODS_CATEGORY_ID;
    private final int NO_OF_UNITS;
    private final double TOTAL_PRICE;


    public OrderDTO(int ID, LocalDate DATE, int INVOICE_RECIEVER_ID, int ACCOUNT_ID, int GOODS_CATEGORY_ID, int NO_OF_UNITS, double TOTAL_PRICE) {
        this.ID = ID;
        this.DATE = DATE;
        this.INVOICE_RECIEVER_ID = INVOICE_RECIEVER_ID;
        this.ACCOUNT_ID = ACCOUNT_ID;
        this.GOODS_CATEGORY_ID = GOODS_CATEGORY_ID;
        this.NO_OF_UNITS = NO_OF_UNITS;
        this.TOTAL_PRICE = TOTAL_PRICE;
    }

}
