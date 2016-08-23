package com.peter.dto;

/**
 * Created by andreajacobsson on 2016-08-23.
 */
public class AccountDTO {

    public final int ID;
    public final String NAME;

    public AccountDTO(int ID, String NAME) {
        this.ID = ID;
        this.NAME = NAME;
    }


    @Override
    public String toString() {
        return NAME;
    }

}
