package com.peter.dtos;

/**
 * Created by andreajacobsson on 2016-08-23.
 */

public class RawOrderDataDTO {

    public final int ID;
    public final String DATE;
    public final int INVOICE_RECIEVER_ID;
    public final int ACCOUNT_ID;
    public final int GOODS_CATEGORY_ID;
    public final int NO_OF_UNITS;
    public final double TOTAL_PRICE;


    public RawOrderDataDTO(int ID, String DATE, int INVOICE_RECIEVER_ID, int ACCOUNT_ID, int GOODS_CATEGORY_ID, int NO_OF_UNITS, double TOTAL_PRICE) {
        this.ID = ID;
        this.DATE = DATE;
        this.INVOICE_RECIEVER_ID = INVOICE_RECIEVER_ID;
        this.ACCOUNT_ID = ACCOUNT_ID;
        this.GOODS_CATEGORY_ID = GOODS_CATEGORY_ID;
        this.NO_OF_UNITS = NO_OF_UNITS;
        this.TOTAL_PRICE = TOTAL_PRICE;
    }

}
