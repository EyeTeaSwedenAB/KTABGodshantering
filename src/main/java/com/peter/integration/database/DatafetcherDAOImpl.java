package com.peter.integration.database;

import com.peter.dto.OrderDTO;
import com.peter.integration.integrationrequirements.Credentials;
import com.peter.model.data.Account;
import com.peter.model.data.GoodsCategory;
import com.peter.model.data.InvoiceReciever;
import com.peter.model.data.RawOrderData;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by andreajacobsson on 2016-08-23.
 */
class DatafetcherDAOImpl extends DatafetcherDAO {

    private Connection globalConnection;
    private Statement globalStmt;
    private PreparedStatement globalPrepStmt;
    private ResultSet globalResutset;

    private Credentials credentials;
    private String url;

    DatafetcherDAOImpl() {
    }

    DatafetcherDAOImpl(String url, Credentials credentials) {
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

        openGlobalRecources();
        closeGlobalResources();
    }


    @Override
    public List<Account> getAllAccounts() throws SQLException {

        List<Account> accounts = new ArrayList<>();

        openGlobalRecources();
        loadGlobalResultSetForSimpleQurey("SELECT * FROM " + Table.ACCOUNTS + " ORDER BY account ASC");

        while (globalResutset.next()) {
            accounts.add(new Account(globalResutset.getInt("id"), globalResutset.getString("account")));
        }

        closeGlobalResources();
        return accounts;
    }


    @Override
    public List<InvoiceReciever> getAllInvoiceRecievers() throws SQLException {
        List<InvoiceReciever> invoiceRecievers = new ArrayList<>();
        openGlobalRecources();
        loadGlobalResultSetForSimpleQurey("SELECT * FROM " + Table.INVOICE_RECIEVERS + " ORDER BY company ASC");

        while (globalResutset.next())
            invoiceRecievers.add(new InvoiceReciever(globalResutset.getInt("id"),
                    globalResutset.getString("company"),
                    globalResutset.getString("adress"),
                    globalResutset.getString("contact"),
                    globalResutset.getString("phone"),
                    globalResutset.getString("email")));

        closeGlobalResources();
        return invoiceRecievers;
    }


    @Override
    public List<GoodsCategory> getAllGoodsCategories() throws SQLException {

        List<GoodsCategory> goodsCategories = new ArrayList<>();
        openGlobalRecources();
        loadGlobalResultSetForSimpleQurey("SELECT * FROM " + Table.GOODS_CATEGORIES + " ORDER BY category ASC");

        while (globalResutset.next()) {
            goodsCategories.add(new GoodsCategory(
                    globalResutset.getInt("id"),
                    globalResutset.getString("category"),
                    Double.parseDouble(globalResutset.getString("unitprice"))));
        }
        closeGlobalResources();
        return goodsCategories;
    }

    @Override
    public List<OrderDTO> fetchOrders(int limit) throws SQLException {


        String sql = "SELECT orders.id, orders.date, invoicerecievers.company, accounts.account, goodscategories.category, orders.nounits, goodscategories.unitprice ,orders.totalprice, orders.comment, orders.mailed_date " +
                "FROM orders " +
                "JOIN invoicerecievers ON orders.invoicereciever_id = invoicerecievers.id " +
                "JOIN accounts ON orders.accounts_id = accounts.id " +
                "JOIN goodscategories ON orders.goodscategories_id = goodscategories.id " +
                "ORDER BY orders.id ASC";

        if (limit != -1)
            sql = sql + " LIMIT " + limit;

        return executeFetchOrders(sql);
    }

    @Override
    public List<OrderDTO> fetchOrders(String date) throws SQLException {

        String sql = "SELECT orders.id, orders.date, invoicerecievers.company, accounts.account, goodscategories.category, orders.nounits, goodscategories.unitprice ,orders.totalprice, orders.comment, orders.mailed_date " +
                "FROM orders " +
                "JOIN invoicerecievers ON orders.invoicereciever_id = invoicerecievers.id " +
                "JOIN accounts ON orders.accounts_id = accounts.id " +
                "JOIN goodscategories ON orders.goodscategories_id = goodscategories.id " +
                "WHERE orders.date = '" + date + "' " +
                "ORDER BY orders.id ASC";

        return executeFetchOrders(sql);


    }

    @Override
    public List<OrderDTO> fetchOrders(String startDate, String endDate) throws SQLException {
        String sql = "SELECT orders.id, orders.date, invoicerecievers.company, accounts.account, goodscategories.category, orders.nounits, goodscategories.unitprice ,orders.totalprice, orders.comment, orders.mailed_date " +
                "FROM orders " +
                "JOIN invoicerecievers ON orders.invoicereciever_id = invoicerecievers.id " +
                "JOIN accounts ON orders.accounts_id = accounts.id " +
                "JOIN goodscategories ON orders.goodscategories_id = goodscategories.id " +
                "WHERE orders.date >= '" + startDate + "' " + "AND orders.date <= '" + endDate + "'" +
                "ORDER BY orders.id ASC";

        return executeFetchOrders(sql);
    }

    @Override
    public int sendNewEntry(RawOrderData rawOrderData) throws SQLException {


        String sql = "INSERT INTO " + Table.ORDERS + "(date, invoicereciever_id, accounts_id, goodscategories_id, nounits, totalprice, comment)" +
                " VALUES (?, ?, ?, ?, ?, ?, ?)";

        openGlobalRecources();
        globalPrepStmt = globalConnection.prepareStatement(sql);
        globalPrepStmt.setString(1, rawOrderData.getDate());
        globalPrepStmt.setInt(2, rawOrderData.getInvoiceRecieverId());
        globalPrepStmt.setInt(3, rawOrderData.getAccountId());
        globalPrepStmt.setInt(4, rawOrderData.getGoodsCategoryId());
        globalPrepStmt.setInt(5, rawOrderData.getNoOfUnits());
        globalPrepStmt.setDouble(6, rawOrderData.getTotalPrice());
        globalPrepStmt.setString(7, rawOrderData.getComment());

        int rowsAffected = globalPrepStmt.executeUpdate();
        closeGlobalResources();

        return rowsAffected;
    }

    @Override
    public int deleteLastEntry() throws SQLException {

        String sql = "DELETE FROM " + Table.ORDERS + " ORDER BY id DESC LIMIT 1";

        openGlobalRecources();
        int rowsAffected = doSimpleUpdate(sql);
        closeGlobalResources();
        return rowsAffected;

    }

    @Override
    public int delete(DeleteCritera deleteCritera) throws SQLException {
        return 0;
    }

    @Override
    public int addInvoiceReciever(InvoiceReciever invoiceReciever) throws SQLException {
        String sql = "INSERT INTO " + Table.INVOICE_RECIEVERS + " (company, adress, contact, phone) VALUES (?, ?, ?, ?)";
        openGlobalRecources();
        globalPrepStmt = globalConnection.prepareStatement(sql);
        globalPrepStmt.setString(1, invoiceReciever.getCompany());
        globalPrepStmt.setString(2, invoiceReciever.getAddress());
        globalPrepStmt.setString(3, invoiceReciever.getContact());
        globalPrepStmt.setString(4, invoiceReciever.getPhone());
        int rowsAffected = globalPrepStmt.executeUpdate();
        closeGlobalResources();
        return rowsAffected;


    }

    @Override
    public int addGoodsCategory(GoodsCategory goodsCategory) throws SQLException {
        String sql = "INSERT INTO " + Table.GOODS_CATEGORIES + " (category, unitprice) VALUES (?, ?)";
        openGlobalRecources();
        globalPrepStmt = globalConnection.prepareStatement(sql);
        globalPrepStmt.setString(1, goodsCategory.getCategory());
        globalPrepStmt.setDouble(2, goodsCategory.getUnitPrice());
        int rowsAffected = globalPrepStmt.executeUpdate();
        closeGlobalResources();
        return rowsAffected;
    }

    @Override
    public int addAccount(Account account) throws SQLException {
        String sql = "INSERT INTO " + Table.ACCOUNTS + " (account) VALUES (?)";
        openGlobalRecources();
        globalPrepStmt = globalConnection.prepareStatement(sql);
        globalPrepStmt.setString(1, account.getAccountName());
        int rowsAffected = globalPrepStmt.executeUpdate();
        closeGlobalResources();
        return rowsAffected;
    }

    @Override
    public int deteleInvoiceReciever(InvoiceReciever invoiceReciever) throws SQLException {

        String sql = "DELETE FROM " + Table.INVOICE_RECIEVERS + " WHERE id = ?";
        openGlobalRecources();
        globalPrepStmt = globalConnection.prepareStatement(sql);
        globalPrepStmt.setInt(1, invoiceReciever.getId());
        int rowsAffected = globalPrepStmt.executeUpdate();
        closeGlobalResources();
        return rowsAffected;


    }

    @Override
    public int deleteGoodsCategory(GoodsCategory goodsCategory) throws SQLException {

        String sql = "DELETE FROM " + Table.GOODS_CATEGORIES + " WHERE id = ?";
        openGlobalRecources();
        globalPrepStmt = globalConnection.prepareStatement(sql);
        globalPrepStmt.setInt(1, goodsCategory.getId());
        int rowsAffected = globalPrepStmt.executeUpdate();
        closeGlobalResources();
        return rowsAffected;

    }

    @Override
    public int deleteAccount(Account account) throws SQLException {
        String sql = "DELETE FROM " + Table.ACCOUNTS + " WHERE id = ?";
        openGlobalRecources();
        globalPrepStmt = globalConnection.prepareStatement(sql);
        globalPrepStmt.setInt(1, account.getId());
        int rowsAffected = globalPrepStmt.executeUpdate();
        closeGlobalResources();
        return rowsAffected;

    }


    @Override
    public int deleteEntry(OrderDTO selectedRow) throws SQLException {
        String sql = "DELETE FROM " + Table.ORDERS + " WHERE id = ?";
        openGlobalRecources();
        globalPrepStmt = globalConnection.prepareStatement(sql);
        globalPrepStmt.setInt(1, selectedRow.getId());
        int rowsAffected = globalPrepStmt.executeUpdate();
        closeGlobalResources();
        return rowsAffected;
    }


    // PRIVATE DOMAIN
    //////////////////////////////////////////////////////////////////////////////////////////////////////////


    private List<OrderDTO> executeFetchOrders(String sql) throws SQLException {

        List<OrderDTO> orderDTOs = new ArrayList<>();

        openGlobalRecources();
        loadGlobalResultSetForSimpleQurey(sql);

        while (globalResutset.next()) {
            OrderDTO orderDTO = constructOrderDTO();
            orderDTOs.add(orderDTO);
        }

        closeGlobalResources();
        return orderDTOs;
    }


    private void loadGlobalResultSetForSimpleQurey(String sql) throws SQLException {
        globalStmt = globalConnection.createStatement();
        globalResutset = globalStmt.executeQuery(sql);

    }


    private int doSimpleUpdate(String sql) throws SQLException {

        globalStmt = globalConnection.createStatement();
        int rowsAffected = globalStmt.executeUpdate(sql);
        return rowsAffected;

    }

    private Connection openGlobalRecources() throws SQLException {

        globalConnection = DriverManager.getConnection(url, credentials.getUSERNAME(), credentials.getPASSWORD());
        return globalConnection;
    }

    private void closeGlobalResources() throws SQLException {

        if (globalResutset != null)
            globalResutset.close();
        if (globalPrepStmt != null)
            globalPrepStmt.close();
        if (globalStmt != null)
            globalStmt.close();
        if (globalConnection != null)
            globalConnection.close();
    }


    private OrderDTO constructOrderDTO() throws SQLException {


        return new OrderDTO(
                globalResutset.getInt("id"),
                globalResutset.getString("date"),
                globalResutset.getString("company"),
                globalResutset.getString("account"),
                globalResutset.getString("category"),
                globalResutset.getInt("nounits"),
                globalResutset.getDouble("unitprice"),
                globalResutset.getDouble("totalprice"),
                globalResutset.getString("comment"),
                globalResutset.getString("mailed_date"));

    }
}
