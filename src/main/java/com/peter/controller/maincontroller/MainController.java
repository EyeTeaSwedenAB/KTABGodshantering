package com.peter.controller.maincontroller;

import com.peter.dto.OrderDTO;
import com.peter.integration.DataManager;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

/**
 * Created by andreajacobsson on 2016-08-22.
 */
public class MainController {

    private DataManager dataManager;

    public MainController() {

        dataManager = new DataManager();
    }

    public MainController(String url, String userName, String password) {
        dataManager = new DataManager();
        dataManager.setLoginInformation(url, userName, password);
    }

    public void setLoginInformation(String url, String userName, String password) {
        dataManager.setLoginInformation(url, userName, password);
    }


    public void testConnection() throws SQLException {
        dataManager.testConnection();
    }

    public List<String> getAllAccounts() throws SQLException {
        List<String> accountsAsStrings = dataManager.getAllAccounts();

        return accountsAsStrings;
    }

    public List<String> getAllGoodsCategories() throws SQLException {

        List<String> goodsCategoryAsStrings = dataManager.getAllGoodsCategories();

        return goodsCategoryAsStrings;
    }


    public List<String> getAllInvoiceRecievers() throws SQLException {
        List<String> invoiceRecieversAsStrings = dataManager.getAllInvoiceRecievers();

        return invoiceRecieversAsStrings;

    }


    public int sendNewEntry(OrderDTO orderEntryDTO) throws SQLException {
        return dataManager.sendNewEntry(orderEntryDTO);
    }

    public List<OrderDTO> getOrders(int limit) throws SQLException {
        return dataManager.getOrders(limit);
    }

    public List<OrderDTO> getOrders(LocalDate localDate) throws SQLException {
        return dataManager.getOrders(localDate);
    }

    public double getUnitPrice(String goodsCategory) {

        return dataManager.getUnitPrice(goodsCategory);
    }

    public int deleteLastEntry() throws SQLException {
        return dataManager.deleteLastEntry();
    }

    public List<String> addNewInvoiceReciever(String company, String address, String contact, String phone) throws SQLException {
        return dataManager.addNewInvoiceReciever(company, address, contact, phone);
    }

    public List<String> addGoodsCategory(String goodsCategory, double unitPrice) throws SQLException {

        return dataManager.addGoodsGategory(goodsCategory, unitPrice);
    }

    public List<String> addAccount(String account) throws SQLException {

        return dataManager.addAccount(account);
    }


    // PRIVATE DOMAIN
    ///////////////////////////////////////////////////////////////////////////////


}
