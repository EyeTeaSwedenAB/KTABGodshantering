package com.peter.controller.maincontroller;

import com.peter.dtos.AccountDTO;
import com.peter.dtos.GoodsCategoryDTO;
import com.peter.dtos.InvoiceRecieverDTO;
import com.peter.dtos.TransformedOrderDataDTO;
import com.peter.integration.IntegrationLogicManager;

import java.sql.SQLException;
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

    public List<AccountDTO> getAllAccounts() throws SQLException {
        List<AccountDTO> accountDTOs = integrationLogicManager.getAllAccounts();

        return accountDTOs;
    }

    public List<GoodsCategoryDTO> getAllGoodsCategories() throws SQLException {

        List<GoodsCategoryDTO> goodsCategoriDTOs = integrationLogicManager.getAllGoodsCategories();


        return goodsCategoriDTOs;
    }


    public List<InvoiceRecieverDTO> getAllInvoiceRecievers() throws SQLException {
        List<InvoiceRecieverDTO> invoiceRecieverDTOs = integrationLogicManager.getAllInvoiceRecievers();

        return invoiceRecieverDTOs;

    }


    public int sendNewEntry(TransformedOrderDataDTO orderEntryDTO) throws SQLException {
        return integrationLogicManager.sendNewEntry(orderEntryDTO);
    }

    public List<TransformedOrderDataDTO> getEntries(int limit) throws SQLException {
        return integrationLogicManager.getTransformedOrders(limit);
    }


    // PRIVATE DOMAIN
    ///////////////////////////////////////////////////////////////////////////////



}
