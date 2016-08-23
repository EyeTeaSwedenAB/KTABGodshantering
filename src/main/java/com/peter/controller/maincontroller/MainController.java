package com.peter.controller.maincontroller;

import com.peter.dto.AccountDTO;
import com.peter.dto.GoodsCategoryDTO;
import com.peter.dto.InvoiceRecieverDTO;
import com.peter.integration.IntegrationLogicManager;
import com.peter.model.Account;
import com.peter.model.GoodsCategory;
import com.peter.model.InvoiceReciever;

import java.sql.SQLException;
import java.util.ArrayList;
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
        List<Account> accounts = integrationLogicManager.getAccounts();
        List<AccountDTO> accountDTOs = new ArrayList<>();

        for (Account a : accounts)
            accountDTOs.add(new AccountDTO(a.getId(), a.getName()));

        return accountDTOs;
    }

    public List<GoodsCategoryDTO> getAllGoodsCategories() throws SQLException {
        List<GoodsCategory> goodsCategories = integrationLogicManager.getGoodsCategories();
        List<GoodsCategoryDTO> goodsCategoryDTOs = new ArrayList<>();
        for (GoodsCategory g : goodsCategories)
            goodsCategoryDTOs.add(new GoodsCategoryDTO(g.getId(), g.getCategory(), g.getUnitPrice()));

        return goodsCategoryDTOs;
    }


    public List<InvoiceRecieverDTO> getAllInvoiceRecievers() throws SQLException {
        List<InvoiceReciever> invoiceRecievers = integrationLogicManager.getInvoiceRecievers();
        List<InvoiceRecieverDTO> invoiceRecieverDTOs = new ArrayList<>();
        for (InvoiceReciever i : invoiceRecievers)
            invoiceRecieverDTOs.add(new InvoiceRecieverDTO(i.getId(), i.getName(), i.getAdress(), i.getContact(), i.getPhone()));

        return invoiceRecieverDTOs;

    }

    // PRIVATE DOMAIN
    ///////////////////////////////////////////////////////////////////////////////


}
