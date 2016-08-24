package com.peter.integration;

import com.peter.dtos.*;
import com.peter.integration.database.DatabaseHandler;
import com.peter.integration.integrationrequirements.Credentials;

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

    private Map<Integer, AccountDTO> accountsMap = new HashMap<>();
    private Map<Integer, GoodsCategoryDTO> goodsCategoriesMap = new HashMap<>();
    private Map<Integer, InvoiceRecieverDTO> invoiceRecieversMap = new HashMap<>();

    private List<TransformedOrderDataDTO> transformedOrderDataDTOs = new ArrayList<>();

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


    public List<AccountDTO> getAllAccounts() throws SQLException {

        accountsMap.clear();
        List<AccountDTO> accountDTOs = databaseHandler.getAllAccounts();

        for (AccountDTO accountDTO : accountDTOs)
            accountsMap.put(accountDTO.ID, accountDTO);

        return accountDTOs;
    }


    public List<GoodsCategoryDTO> getAllGoodsCategories() throws SQLException {

        goodsCategoriesMap.clear();
        List<GoodsCategoryDTO> goodsCategoryDTOs = databaseHandler.getAllGoodsCategories();

        for (GoodsCategoryDTO goodsCategoryDTO : goodsCategoryDTOs)
            goodsCategoriesMap.put(goodsCategoryDTO.ID, goodsCategoryDTO);

        return goodsCategoryDTOs;
    }


    public List<InvoiceRecieverDTO> getAllInvoiceRecievers() throws SQLException {

        invoiceRecieversMap.clear();
        List<InvoiceRecieverDTO> invoiceRecieverDTOs = databaseHandler.getAllInvoiceRecievers();

        for (InvoiceRecieverDTO invoiceRecieverDTO : invoiceRecieverDTOs)
            invoiceRecieversMap.put(invoiceRecieverDTO.ID, invoiceRecieverDTO);

        return invoiceRecieverDTOs;
    }


    public List<TransformedOrderDataDTO> getTransformedOrders(int limit) throws SQLException {

        transformedOrderDataDTOs.clear();
        List<RawOrderDataDTO> rawOrderDataDTOs = databaseHandler.fetchRawOrderData(limit);

        // Construct list of orders

        for (RawOrderDataDTO rawOrderDataDTO : rawOrderDataDTOs) {

            TransformedOrderDataDTO transformedOrderDataDTO = convertRawOrderDataDTO(rawOrderDataDTO);
            transformedOrderDataDTOs.add(transformedOrderDataDTO);
        }

        return transformedOrderDataDTOs;
    }

    public void testConnection() throws SQLException {
        databaseHandler.testConnection();
    }

    public int sendNewEntry(TransformedOrderDataDTO transformedOrderDataDTO) throws SQLException {

        RawOrderDataDTO rawOrderDataDTO = convertTransformedOrderDataDTO(transformedOrderDataDTO);
        return databaseHandler.sendNewEntry(rawOrderDataDTO);
    }


    // Private Domain
    ////////////////////////////////////////////////////////////////////////////////////


    private TransformedOrderDataDTO convertRawOrderDataDTO(RawOrderDataDTO rawOrderDataDTO) {

        int id = rawOrderDataDTO.ID;
        LocalDate date = LocalDate.parse(rawOrderDataDTO.DATE);
        InvoiceRecieverDTO invoiceReciever = invoiceRecieversMap.get(rawOrderDataDTO.INVOICE_RECIEVER_ID);
        AccountDTO account = accountsMap.get(rawOrderDataDTO.ACCOUNT_ID);
        GoodsCategoryDTO goodsCategory = goodsCategoriesMap.get(rawOrderDataDTO.GOODS_CATEGORY_ID);
        int noOfUnits = rawOrderDataDTO.NO_OF_UNITS;
        double unitPrice = goodsCategory.UNIT_PRICE;
        double totalPrice = noOfUnits * unitPrice;

        TransformedOrderDataDTO transformedDTO = new TransformedOrderDataDTO(date, invoiceReciever, account, goodsCategory,
                noOfUnits, unitPrice, totalPrice);

        return transformedDTO;
    }

    private RawOrderDataDTO convertTransformedOrderDataDTO(TransformedOrderDataDTO transformedOrderDataDTO) {
        int ignoreID = 0;
        String date = transformedOrderDataDTO.getDate().toString();
        int invoiceRecieverId = transformedOrderDataDTO.getInvoiceReciever().ID;
        int accpountID = transformedOrderDataDTO.getDestination().ID;
        int goodsCategoryId = transformedOrderDataDTO.getGoodsCategory().ID;
        int noOfUnits = transformedOrderDataDTO.getNoOfUnits();
        double totalPrice = transformedOrderDataDTO.getTotalPrice();

        RawOrderDataDTO raworderData = new RawOrderDataDTO(ignoreID, date, invoiceRecieverId, accpountID, goodsCategoryId, noOfUnits, totalPrice);
        return raworderData;
    }
}
