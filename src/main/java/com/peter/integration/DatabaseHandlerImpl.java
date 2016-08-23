package com.peter.integration;

import com.peter.model.dto.AccountDTO;
import com.peter.model.dto.GoodsCategotyDTO;
import com.peter.model.dto.InvoiceRecieverDTO;
import com.peter.model.integrationreqirements.Credentials;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by andreajacobsson on 2016-08-23.
 */
public class DatabaseHandlerImpl implements DatabaseHandler {

    private Connection globalConnection;
    private Statement globalStmt;
    private PreparedStatement globalPrepStmt;
    private ResultSet globalResultSet;

    private Credentials credentials;
    private String url = "jdbc:mysql://ktabtest.cyzgfcxn1ubh.eu-central-1.rds.amazonaws.com:3306/KTABGoodsTest";


    public DatabaseHandlerImpl() {
    }

    public DatabaseHandlerImpl(Credentials credentials) {
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
    public boolean testConnection() {

        try {
            startGlobalConnection();
            closeGlobalConnerction();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public List<AccountDTO> getAccounts() throws SQLException {

        List<AccountDTO> accountDTOs = new ArrayList<>();

        startGlobalConnection();
        loadGlobalResultSetForTable(Table.ACCOUNTS);

        while (globalResultSet.next()) {
            accountDTOs.add(new AccountDTO(globalResultSet.getInt("id"), globalResultSet.getString("name")));
        }

        closeGlobalConnerction();
        return accountDTOs;
    }

    @Override
    public List<InvoiceRecieverDTO> getInvoiceRecievers() throws SQLException {
        List<InvoiceRecieverDTO> invoiceRecieverDTOs = new ArrayList<>();
        startGlobalConnection();
        loadGlobalResultSetForTable(Table.INVOICE_RECIEVERS);

        while (globalResultSet.next())
            invoiceRecieverDTOs.add(new InvoiceRecieverDTO(globalResultSet.getInt("id"), globalResultSet.getString("name"), globalResultSet.getString("adress"),
                    globalResultSet.getString("contact"), globalResultSet.getString("phone")));

        closeGlobalConnerction();
        return invoiceRecieverDTOs;
    }

    @Override
    public List<GoodsCategotyDTO> getGoodsCategories() throws SQLException {

        List<GoodsCategotyDTO> goodsCategories = new ArrayList<>();
        startGlobalConnection();
        loadGlobalResultSetForTable(Table.GOODS_CATEGORIES);

        while (globalResultSet.next()) {
            goodsCategories.add(new GoodsCategotyDTO(globalResultSet.getInt("id"), globalResultSet.getString("category"), Double.parseDouble(globalResultSet.getString("unitprice"))));
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
