package com.peter.integration.database;

import com.peter.integration.integrationreqirements.Credentials;
import com.peter.model.Account;
import com.peter.model.GoodsCategory;
import com.peter.model.InvoiceReciever;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by andreajacobsson on 2016-08-23.
 */
class DatabaseHandlerImpl extends DatabaseHandler {

    private Connection globalConnection;
    private Statement globalStmt;
    private PreparedStatement globalPrepStmt;
    private ResultSet globalResultSet;

    private Credentials credentials;
    private String url;


    DatabaseHandlerImpl() {
    }

    DatabaseHandlerImpl(String url, Credentials credentials) {
        this.url = url;
        this.credentials = credentials;
    }


    @Override
    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }


    @Override
    public void testConnection() throws SQLException {

        startGlobalConnection();
        closeGlobalConnerction();
    }

    @Override
    public List<Account> getAccounts() throws SQLException {

        List<Account> accounts = new ArrayList<>();

        startGlobalConnection();
        loadGlobalResultSetForTable(Table.ACCOUNTS);

        while (globalResultSet.next()) {
            accounts.add(new Account(globalResultSet.getInt("id"), globalResultSet.getString("name")));
        }

        closeGlobalConnerction();
        return accounts;
    }

    @Override
    public List<InvoiceReciever> getInvoiceRecievers() throws SQLException {
        List<InvoiceReciever> invoiceRecievers = new ArrayList<>();
        startGlobalConnection();
        loadGlobalResultSetForTable(Table.INVOICE_RECIEVERS);

        while (globalResultSet.next())
            invoiceRecievers.add(new InvoiceReciever(globalResultSet.getInt("id"), globalResultSet.getString("name"), globalResultSet.getString("adress"),
                    globalResultSet.getString("contact"), globalResultSet.getString("phone")));

        closeGlobalConnerction();
        return invoiceRecievers;
    }

    @Override
    public List<GoodsCategory> getGoodsCategories() throws SQLException {

        List<GoodsCategory> goodsCategories = new ArrayList<>();
        startGlobalConnection();
        loadGlobalResultSetForTable(Table.GOODS_CATEGORIES);

        while (globalResultSet.next()) {
            goodsCategories.add(new GoodsCategory(globalResultSet.getInt("id"), globalResultSet.getString("category"), Double.parseDouble(globalResultSet.getString("unitprice"))));
        }
        closeGlobalConnerction();
        return goodsCategories;
    }


    // PRIVATE DOMAIN
    //////////////////////////////////////////////////////////////////////////////////////////////////////////


    private void loadGlobalResultSetForTable(Table table) throws SQLException {

        String sql = "SELECT * FROM " + table.toString();
        globalStmt = globalConnection.createStatement();
        globalResultSet = globalStmt.executeQuery(sql);

    }

    private void startGlobalConnection() throws SQLException {

        globalConnection = DriverManager.getConnection(url, credentials.getUSERNAME(), credentials.getPASSWORD());
    }


    private void closeGlobalConnerction() throws SQLException {

        if (globalResultSet != null)
            globalResultSet.close();
        if (globalPrepStmt != null)
            globalPrepStmt.close();
        if (globalStmt != null)
            globalStmt.close();
        if (globalConnection != null)
            globalConnection.close();

    }
}
