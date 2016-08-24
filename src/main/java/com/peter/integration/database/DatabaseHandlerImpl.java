package com.peter.integration.database;

import com.peter.dtos.AccountDTO;
import com.peter.dtos.GoodsCategoryDTO;
import com.peter.dtos.InvoiceRecieverDTO;
import com.peter.dtos.RawOrderDataDTO;
import com.peter.integration.integrationrequirements.Credentials;

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

        openGlobalConnection();
        closeGlobalRescources();
    }

    @Override
    public List<AccountDTO> getAllAccounts() throws SQLException {

        List<AccountDTO> accounts = new ArrayList<>();

        openGlobalConnection();
        simpleQueryWithGlobalRecources("SELECT * FROM " + Table.ACCOUNTS);

        while (globalResultSet.next()) {
            accounts.add(new AccountDTO(globalResultSet.getInt("id"), globalResultSet.getString("name")));
        }

        closeGlobalRescources();
        return accounts;
    }


    @Override
    public List<InvoiceRecieverDTO> getAllInvoiceRecievers() throws SQLException {
        List<InvoiceRecieverDTO> invoiceRecievers = new ArrayList<>();
        openGlobalConnection();
        simpleQueryWithGlobalRecources("SELECT * FROM " + Table.INVOICE_RECIEVERS);

        while (globalResultSet.next())
            invoiceRecievers.add(new InvoiceRecieverDTO(globalResultSet.getInt("id"),
                    globalResultSet.getString("name"),
                    globalResultSet.getString("adress"),
                    globalResultSet.getString("contact"),
                    globalResultSet.getString("phone")));

        closeGlobalRescources();
        return invoiceRecievers;
    }

    @Override
    public List<GoodsCategoryDTO> getAllGoodsCategories() throws SQLException {

        List<GoodsCategoryDTO> goodsCategories = new ArrayList<>();
        openGlobalConnection();
        simpleQueryWithGlobalRecources("SELECT * FROM " + Table.GOODS_CATEGORIES);

        while (globalResultSet.next()) {
            goodsCategories.add(new GoodsCategoryDTO(globalResultSet.getInt("id"), globalResultSet.getString("category"), Double.parseDouble(globalResultSet.getString("unitprice"))));
        }
        closeGlobalRescources();
        return goodsCategories;
    }

    @Override
    public List<RawOrderDataDTO> fetchRawOrderData(int limit) throws SQLException {

        String sql = "SELECT * FROM " + Table.ORDERS;

        if (limit == -1)
            sql = sql + " LIMIT" + limit;

        List<RawOrderDataDTO> rawOrderDataDTOs = new ArrayList<>();


        openGlobalConnection();
        simpleQueryWithGlobalRecources(sql);

        while (globalResultSet.next()) {

            rawOrderDataDTOs.add(new RawOrderDataDTO(
                    globalResultSet.getInt("id"),
                    globalResultSet.getString("date"),
                    globalResultSet.getInt("invoicereciever_id"),
                    globalResultSet.getInt("accounts_id"),
                    globalResultSet.getInt("goodscategories_id"),
                    globalResultSet.getInt("nounits"),
                    globalResultSet.getDouble("totalprice")));
        }

        closeGlobalRescources();

        return rawOrderDataDTOs;
    }


    @Override
    public int sendNewEntry(RawOrderDataDTO rawOrderDataDTO) throws SQLException {

        String sql = "INSERT INTO " + Table.ORDERS +
                " (date, invoicereciever_id, accounts_id, goodscategories_id, nounits, totalprice) " +
                "VALUES ("
                + "'" + rawOrderDataDTO.DATE + "'" + ", " + rawOrderDataDTO.INVOICE_RECIEVER_ID + ", " + rawOrderDataDTO.ACCOUNT_ID + ", " +
                rawOrderDataDTO.GOODS_CATEGORY_ID + ", " + rawOrderDataDTO.NO_OF_UNITS + ", " + rawOrderDataDTO.TOTAL_PRICE +
                ");";

        System.out.println(sql);

        openGlobalConnection();
        int rowsAffected = simpleUpdateWithGlobalRecources(sql);
        closeGlobalRescources();
        return rowsAffected;
    }


    // PRIVATE DOMAIN
    //////////////////////////////////////////////////////////////////////////////////////////////////////////


    private void simpleQueryWithGlobalRecources(String updateSql) throws SQLException {

        globalStmt = globalConnection.createStatement();
        globalResultSet = globalStmt.executeQuery(updateSql);

    }

    private int simpleUpdateWithGlobalRecources(String sql) throws SQLException {

        globalStmt = globalConnection.createStatement();
        return globalStmt.executeUpdate(sql);


    }

    private void openGlobalConnection() throws SQLException {

        globalConnection = DriverManager.getConnection(url, credentials.getUSERNAME(), credentials.getPASSWORD());
    }


    private void closeGlobalRescources() throws SQLException {

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
