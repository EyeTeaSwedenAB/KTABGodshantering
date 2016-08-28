package com.peter.observ;

import java.util.List;

/**
 * Created by andreajacobsson on 2016-08-28.
 */
public class AccountUpdateEvent implements UpdateEvent<List<String>> {

    private List<String> accounts;
    public AccountUpdateEvent(List<String> accounts){
        this.accounts = accounts;
    }

    @Override
    public List<String> getObject() {
        return accounts;
    }
}
