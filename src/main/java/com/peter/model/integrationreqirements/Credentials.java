package com.peter.model.integrationreqirements;

/**
 * Created by andreajacobsson on 2016-08-23.
 */
public class Credentials {

    private final String USERNAME;
    private final String PASSWORD;

    public Credentials(String USERNAME, String PASSWORD) {
        this.USERNAME = USERNAME;
        this.PASSWORD = PASSWORD;
    }

    public String getUSERNAME() {
        return USERNAME;
    }

    public String getPASSWORD() {
        return PASSWORD;
    }
}
