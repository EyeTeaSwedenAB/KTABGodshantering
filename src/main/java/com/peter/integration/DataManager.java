package com.peter.integration;

import com.peter.dto.OrderDTO;
import com.peter.integration.database.Datafetcher;
import com.peter.integration.integrationrequirements.Credentials;
import com.peter.model.Account;
import com.peter.model.GoodsCategory;
import com.peter.model.InvoiceReciever;
import com.peter.model.RawOrderData;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by andreajacobsson on 2016-08-23.
 */
public class DataManager {

    private Datafetcher datafetcher;

    private Map<String, Account> nameToAccountMap = new HashMap<>();
    private Map<String, GoodsCategory> nameToGoodsCategoryMap = new HashMap<>();
    private Map<String, InvoiceReciever> nameToInvoiceRecieverMap = new HashMap<>();

    private Map<Account, Integer> accountToIdMap = new HashMap<>();
    private Map<GoodsCategory, Integer> goodsCategoryToIdMap = new HashMap<>();
    private Map<InvoiceReciever, Integer> invoiceRecieverToIdMap = new HashMap<>();


    private List<OrderDTO> orderDTOs = new ArrayList<>();

    public DataManager() {
        datafetcher = Datafetcher.getNewInstance();
    }

    public DataManager(String databaseUrl, String userName, String password) {
        datafetcher = Datafetcher.getNewInstance(databaseUrl, new Credentials(userName, password));
    }

    public void setLoginInformation(String url, String userName, String password) {
        datafetcher.setUrl(url);
        datafetcher.setCredentials(new Credentials(userName, password));
    }


    public void testConnection() throws SQLException {
        datafetcher.testConnection();
    }


    public List<String> getAllAccounts() throws SQLException {

        nameToAccountMap.clear();
        accountToIdMap.clear();

        List<Account> accounts = datafetcher.getAllAccounts();
        List<String> accountsAsString = new ArrayList<>();

        for (Account account : accounts) {
            nameToAccountMap.put(account.getAccountName(), account);
            accountToIdMap.put(account, account.getId());
            accountsAsString.add(account.getAccountName());
        }

        return accountsAsString;
    }


    public List<String> getAllGoodsCategories() throws SQLException {

        List<GoodsCategory> goodsCategories = datafetcher.getAllGoodsCategories();
        return updateGoodsCategorysMaps(goodsCategories);
    }


    public List<String> getAllInvoiceRecievers() throws SQLException {

        List<InvoiceReciever> invoiceRecievers = datafetcher.getAllInvoiceRecievers();
        return updateInvoiceRecieveMaps(invoiceRecievers);
    }

    public List<OrderDTO> getOrders(int limit) throws SQLException {

        List<OrderDTO> orderDTOs = datafetcher.fetchOrders(limit);
        return orderDTOs;
    }

    public List<OrderDTO> getOrders(LocalDate localDate) throws SQLException {

        List<OrderDTO> orderDTOs = datafetcher.fetchOrders(localDate.toString());
        return orderDTOs;
    }

    public int sendNewEntry(OrderDTO orderDTO) throws SQLException {

        RawOrderData rawOrderData = convertToRawOrderData(orderDTO);
        return datafetcher.sendNewEntry(rawOrderData);
    }


    public double getUnitPrice(String goodsCategory) {

        GoodsCategory g = nameToGoodsCategoryMap.get(goodsCategory);
        if (g == null)
            return -1;
        return g.getUnitPrice();
    }


    public int deleteLastEntry() throws SQLException {
        return datafetcher.deleteLastEntry();
    }

    public List<String> addNewInvoiceReciever(String company, String address, String contact, String phone) throws SQLException {
        InvoiceReciever invoiceReciever = new InvoiceReciever(0, company, address, contact, phone);
        datafetcher.addInvoiceReciever(invoiceReciever);
        List<InvoiceReciever> newInvoiceRecivers = datafetcher.getAllInvoiceRecievers();
        return updateInvoiceRecieveMaps(newInvoiceRecivers);
    }


    public List<String> addAccount(String accountName) throws SQLException {
        Account account = new Account(0, accountName);
        datafetcher.addAccount(account);
        List<Account> newAccounts = datafetcher.getAllAccounts();
        return updateAccountMaps(newAccounts);

    }

    public List<String> addGoodsGategory(String goodsCategory, double unitPrice) throws SQLException {

        GoodsCategory category = new GoodsCategory(0, goodsCategory, unitPrice);
        datafetcher.addGoodsCategory(category);
        List<GoodsCategory> newGoodsCategories = datafetcher.getAllGoodsCategories();
        return updateGoodsCategorysMaps(newGoodsCategories);
    }


    // Private Domain
    ////////////////////////////////////////////////////////////////////////////////////


    private List<String> updateInvoiceRecieveMaps(List<InvoiceReciever> invoiceRecievers) {
        List<String> invoiceRecieversAsStrings = new ArrayList<>();

        nameToInvoiceRecieverMap.clear();
        invoiceRecieverToIdMap.clear();

        for (InvoiceReciever invoiceReciever : invoiceRecievers) {
            nameToInvoiceRecieverMap.put(invoiceReciever.getCompany(), invoiceReciever);
            invoiceRecieverToIdMap.put(invoiceReciever, invoiceReciever.getId());
            invoiceRecieversAsStrings.add(invoiceReciever.getCompany());

        }

        return invoiceRecieversAsStrings;
    }

    private List<String> updateGoodsCategorysMaps(List<GoodsCategory> goodsCategories) {

        List<String> goodsCateGoriesAsString = new ArrayList<>();
        nameToGoodsCategoryMap.clear();
        goodsCategoryToIdMap.clear();

        for (GoodsCategory goodsCategory : goodsCategories) {
            nameToGoodsCategoryMap.put(goodsCategory.getCategory(), goodsCategory);
            goodsCategoryToIdMap.put(goodsCategory, goodsCategory.getId());
            goodsCateGoriesAsString.add(goodsCategory.getCategory());


        }
        return goodsCateGoriesAsString;
    }

    private List<String> updateAccountMaps(List<Account> accounts) {
        List<String> accountsAsStrings = new ArrayList<>();
        nameToAccountMap.clear();
        accountToIdMap.clear();

        for (Account account : accounts) {
            nameToAccountMap.put(account.getAccountName(), account);
            accountToIdMap.put(account,account.getId());
            accountsAsStrings.add(account.getAccountName());
        }

        return accountsAsStrings;
    }

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
