package com.peter.model;

/**
 * Created by andreajacobsson on 2016-08-23.
 */
public class Account {

    private int id;
    private String name;

    public Account(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
