package com.peter.integration.database;

import com.peter.integration.integrationrequirements.Credentials;
import com.peter.model.Account;
import com.peter.model.GoodsCategory;
import com.peter.model.InvoiceReciever;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by andreajacobsson on 2016-08-22.
 */
public abstract class DatabaseHandler {

    public abstract void setUrl(String url);

    public abstract void setCredentials(Credentials credentials);

    public abstract void testConnection() throws SQLException;

    public abstract List<Account> getAccounts() throws SQLException;

    public abstract List<InvoiceReciever> getInvoiceRecievers() throws SQLException;

    public abstract List<GoodsCategory> getGoodsCategories() throws SQLException;

    public static DatabaseHandler getNewInstance() {
        return new DatabaseHandlerImpl();
    }

    public static DatabaseHandler getNewInstance(String url, Credentials credentials) {
        return new DatabaseHandlerImpl(url, credentials);
    }

}
