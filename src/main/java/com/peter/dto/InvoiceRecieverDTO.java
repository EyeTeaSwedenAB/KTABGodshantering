package com.peter.dto;

/**
 * Created by andreajacobsson on 2016-08-23.
 */
public class InvoiceRecieverDTO {

    public final int ID;
    public final String NAME;
    public final String ADRESS;
    public final String CONTACT;
    public final String PHONE;

    public InvoiceRecieverDTO(int ID, String NAME, String ADRESS, String CONTACT, String PHONE) {
        this.ID = ID;
        this.NAME = NAME;
        this.ADRESS = ADRESS;
        this.CONTACT = CONTACT;
        this.PHONE = PHONE;
    }

    @Override
    public String toString() {
        return NAME;
    }
}
