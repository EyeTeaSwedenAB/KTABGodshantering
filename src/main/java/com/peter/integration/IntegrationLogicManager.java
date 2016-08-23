package com.peter.integration;

import com.peter.integration.database.DatabaseHandler;
import com.peter.integration.integrationrequirements.Credentials;
import com.peter.model.Account;
import com.peter.model.GoodsCategory;
import com.peter.model.InvoiceReciever;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by andreajacobsson on 2016-08-23.
 */
public class IntegrationLogicManager {

    private DatabaseHandler databaseHandler;
    private List<Account> accounts;
    private List<GoodsCategory> goodsCategories;
    private List<InvoiceReciever> invoiceRecievers;

    public IntegrationLogicManager() {
        databaseHandler = DatabaseHandler.getNewInstance();
    }

    public IntegrationLogicManager(String databaseUrl, String userName, String password) {
        databaseHandler = DatabaseHandler.getNewInstance(databaseUrl, new Credentials(userName, password));
    }

    public void setLoginInformation(String url, String userName, String password){
        databaseHandler.setUrl(url);
        databaseHandler.setCredentials(new Credentials(userName, password));
    }


    public DatabaseHandler getDatabaseHandler() {
        return databaseHandler;
    }

    public void setDatabaseHandler(DatabaseHandler databaseHandler) {
        this.databaseHandler = databaseHandler;
    }

    public List<Account> getAccounts() throws SQLException {
        accounts = databaseHandler.getAccounts();
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    public List<GoodsCategory> getGoodsCategories() throws SQLException {

        goodsCategories = databaseHandler.getGoodsCategories();
        return goodsCategories;
    }

    public void setGoodsCategories(List<GoodsCategory> goodsCategories) {
        this.goodsCategories = goodsCategories;
    }

    public List<InvoiceReciever> getInvoiceRecievers() throws SQLException {
        invoiceRecievers = databaseHandler.getInvoiceRecievers();
        return invoiceRecievers;
    }

    public void setInvoiceRecievers(List<InvoiceReciever> invoiceRecievers) {
        this.invoiceRecievers = invoiceRecievers;
    }

    public void testConnection() throws SQLException {
         databaseHandler.testConnection();
    }
}
