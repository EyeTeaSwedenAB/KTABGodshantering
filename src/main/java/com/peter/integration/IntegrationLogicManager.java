package com.peter.integration;

import com.peter.dto.OrderDTO;
import com.peter.integration.database.DatabaseHandler;
import com.peter.integration.integrationrequirements.Credentials;
import com.peter.model.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by andreajacobsson on 2016-08-23.
 */
public class IntegrationLogicManager {

    private DatabaseHandler databaseHandler;

    private Map<String, Account> nameToAccountMap = new HashMap<>();
    private Map<String, GoodsCategory> nameToGoodsCategoryMap = new HashMap<>();
    private Map<String, InvoiceReciever> nameToInvoiceRecieverMap = new HashMap<>();

    private Map<Account, Integer> accountToIdMap = new HashMap<>();
    private Map<GoodsCategory, Integer> goodsCategoryToIdMap = new HashMap<>();
    private Map<InvoiceReciever, Integer> invoiceRecieverToIdMap = new HashMap<>();


    private List<OrderDTO> orderDTOs = new ArrayList<>();

    public IntegrationLogicManager() {
        databaseHandler = DatabaseHandler.getNewInstance();
    }

    public IntegrationLogicManager(String databaseUrl, String userName, String password) {
        databaseHandler = DatabaseHandler.getNewInstance(databaseUrl, new Credentials(userName, password));
    }

    public void setLoginInformation(String url, String userName, String password) {
        databaseHandler.setUrl(url);
        databaseHandler.setCredentials(new Credentials(userName, password));
    }


    public void testConnection() throws SQLException {
        databaseHandler.testConnection();
    }


    public List<String> getAllAccounts() throws SQLException {

        nameToAccountMap.clear();
        accountToIdMap.clear();

        List<Account> accounts = databaseHandler.getAllAccounts();
        List<String> accountsAsString = new ArrayList<>();

        for (Account account : accounts){
            nameToAccountMap.put(account.getAccountName(), account);
            accountToIdMap.put(account, account.getId());
            accountsAsString.add(account.getAccountName());
        }

        return accountsAsString;
    }


    public List<String> getAllGoodsCategories() throws SQLException {

        nameToGoodsCategoryMap.clear();
        accountToIdMap.clear();

        List<GoodsCategory> goodsCategories = databaseHandler.getAllGoodsCategories();
        List<String> goodsCategoryAsString = new ArrayList<>();

        for (GoodsCategory goodsCategory : goodsCategories) {
            nameToGoodsCategoryMap.put(goodsCategory.getCategory(), goodsCategory);
            goodsCategoryToIdMap.put(goodsCategory, goodsCategory.getId());
            goodsCategoryAsString.add(goodsCategory.getCategory());
        }

        return goodsCategoryAsString;
    }


    public List<String> getAllInvoiceRecievers() throws SQLException {

        nameToInvoiceRecieverMap.clear();
        List<InvoiceReciever> invoiceRecievers = databaseHandler.getAllInvoiceRecievers();
        List<String> invoiceRecieversAsStrings = new ArrayList<>();

        for (InvoiceReciever invoiceReciever : invoiceRecievers){
            nameToInvoiceRecieverMap.put(invoiceReciever.getCompany(), invoiceReciever);
            invoiceRecieverToIdMap.put(invoiceReciever, invoiceReciever.getId());
            invoiceRecieversAsStrings.add(invoiceReciever.getCompany());

        }

        return invoiceRecieversAsStrings;
    }

    public List<OrderDTO> getOrders(int limit) throws SQLException {

        List<OrderDTO> orderDTOs = databaseHandler.fetchOrders(limit);
        // Construct list of orders
        return orderDTOs;
    }

    public List<OrderDTO> getOrders(LocalDate localDate) throws SQLException {

        List<OrderDTO> orderDTOs = databaseHandler.fetchOrders(localDate.toString());
        return orderDTOs;
    }


    public int sendNewEntry(OrderDTO orderDTO) throws SQLException {

        RawOrderData rawOrderData = convertToRawOrderData(orderDTO);
        return databaseHandler.sendNewEntry(rawOrderData);
    }


    public double getUnitPrice(String goodsCategory) {

        GoodsCategory g = nameToGoodsCategoryMap.get(goodsCategory);
        if (g == null)
            return  -1;
        return g.getUnitPrice();
    }

    public int deleteLastEntry() throws SQLException {
        return databaseHandler.deleteLastEntry();
    }


    // Private Domain
    ////////////////////////////////////////////////////////////////////////////////////

    private RawOrderData convertToRawOrderData(OrderDTO orderDTO) {

        String date = orderDTO.getDate();
        int invoiceID = nameToInvoiceRecieverMap.get(orderDTO.getInvoiceReciever()).getId();
        int accountID = nameToAccountMap.get(orderDTO.getDestination()).getId();
        int goodsCatID = nameToGoodsCategoryMap.get(orderDTO.getGoodsCategory()).getId();
        int noOfUnits = orderDTO.getNoOfUnits();
        double totalPrice = orderDTO.getTotalPrice();
        String comment = orderDTO.getComment();

        return new RawOrderData(0, date, invoiceID, accountID, goodsCatID, noOfUnits, totalPrice, comment);

    }
}
