package com.peter.model.data;

/**
 * Created by andreajacobsson on 2016-08-23.
 */
public class Account {

    private int id;
    private String accountName;

    public Account() {
    }

    public Account(int id, String accountName) {
        this.id = id;
        this.accountName = accountName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    @Override
    public String toString() {
        return accountName;
    }
}
