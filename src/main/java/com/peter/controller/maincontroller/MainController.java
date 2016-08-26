package com.peter.controller.maincontroller;

import com.peter.dto.OrderDTO;
import com.peter.integration.IntegrationLogicManager;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

/**
 * Created by andreajacobsson on 2016-08-22.
 */
public class MainController {

    private IntegrationLogicManager integrationLogicManager;

    public MainController() {

        integrationLogicManager = new IntegrationLogicManager();
    }

    public MainController(String url, String userName, String password) {
        integrationLogicManager = new IntegrationLogicManager();
        integrationLogicManager.setLoginInformation(url, userName, password);
    }

    public void setLoginInformation(String url, String userName, String password) {
        integrationLogicManager.setLoginInformation(url, userName, password);
    }


    public void testConnection() throws SQLException {
        integrationLogicManager.testConnection();
    }

    public List<String> getAllAccounts() throws SQLException {
        List<String> accountsAsStrings = integrationLogicManager.getAllAccounts();

        return accountsAsStrings;
    }

    public List<String> getAllGoodsCategories() throws SQLException {

        List<String> goodsCategoryAsStrings = integrationLogicManager.getAllGoodsCategories();

        return goodsCategoryAsStrings;
    }


    public List<String> getAllInvoiceRecievers() throws SQLException {
        List<String> invoiceRecieversAsStrings = integrationLogicManager.getAllInvoiceRecievers();

        return invoiceRecieversAsStrings;

    }


    public int sendNewEntry(OrderDTO orderEntryDTO) throws SQLException {
        return integrationLogicManager.sendNewEntry(orderEntryDTO);
    }

    public List<OrderDTO> getOrders(int limit) throws SQLException {
        return integrationLogicManager.getOrders(limit);
    }

    public List<OrderDTO> getOrders(LocalDate localDate) throws SQLException {
        return integrationLogicManager.getOrders(localDate);
    }

    public double getUnitPrice(String goodsCategory) {

        return integrationLogicManager.getUnitPrice(goodsCategory);
    }

    public int deleteLastEntry() throws SQLException {
        return integrationLogicManager.deleteLastEntry();
    }


    // PRIVATE DOMAIN
    ///////////////////////////////////////////////////////////////////////////////



}
