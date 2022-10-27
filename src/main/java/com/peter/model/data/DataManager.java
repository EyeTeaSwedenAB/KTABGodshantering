package com.peter.model.data;

import com.peter.dto.ChangebleInvoiceRecieverAttrs;
import com.peter.dto.OrderDTO;
import com.peter.dto.OrderSummaryDTO;
import com.peter.integration.database.DatafetcherDAO;
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
public class DataManager {

    private DatafetcherDAO datafetcherDAO;

    private Map<String, Account> nameToAccountMap = new HashMap<>();
    private Map<String, GoodsCategory> nameToGoodsCategoryMap = new HashMap<>();
    private Map<String, InvoiceReciever> nameToInvoiceRecieverMap = new HashMap<>();

    private Map<Account, Integer> accountToIdMap = new HashMap<>();
    private Map<GoodsCategory, Integer> goodsCategoryToIdMap = new HashMap<>();
    private Map<InvoiceReciever, Integer> invoiceRecieverToIdMap = new HashMap<>();


    public DataManager() {
        datafetcherDAO = DatafetcherDAO.getNewInstance();
    }

    public DataManager(String databaseUrl, String userName, String password) {
        datafetcherDAO = DatafetcherDAO.getNewInstance(databaseUrl, new Credentials(userName, password));
    }

    public void setLoginInformation(String url, String userName, String password) {
        datafetcherDAO.setUrl(url);
        datafetcherDAO.setCredentials(new Credentials(userName, password));
    }


    public void testConnection() throws SQLException {
        datafetcherDAO.testConnection();
    }


    public List<String> getAllAccounts() throws SQLException {

        nameToAccountMap.clear();
        accountToIdMap.clear();

        List<Account> accounts = datafetcherDAO.getAllAccounts();
        List<String> accountsAsString = new ArrayList<>();

        for (Account account : accounts) {
            nameToAccountMap.put(account.getAccountName(), account);
            accountToIdMap.put(account, account.getId());
            accountsAsString.add(account.getAccountName());
        }

        return accountsAsString;
    }


    public List<String> getAllGoodsCategories() throws SQLException {

        List<GoodsCategory> goodsCategories = datafetcherDAO.getAllGoodsCategories();
        return updateGoodsCategorysMaps(goodsCategories);
    }


    public List<String> getAllInvoiceRecievers() throws SQLException {

        List<InvoiceReciever> invoiceRecievers = datafetcherDAO.getAllInvoiceRecievers();
        return updateInvoiceRecieveMaps(invoiceRecievers);
    }

    public ChangebleInvoiceRecieverAttrs getChangebleInvoiceRecieverAttrs(String invoiceReciver) {
        InvoiceReciever reciever = nameToInvoiceRecieverMap.get(invoiceReciver);
        return new ChangebleInvoiceRecieverAttrs(reciever.getAddress(), reciever.getContact(), reciever.getPhone(), reciever.getEmail());

    }


    public List<OrderDTO> getOrders(int limit) throws SQLException {

        return datafetcherDAO.fetchOrders(limit);
    }

    public List<OrderDTO> getOrders(LocalDate localDate) throws SQLException {

        return datafetcherDAO.fetchOrders(localDate.toString());
    }

    public List<OrderDTO> getOrders(LocalDate start, LocalDate end) throws SQLException {

        return datafetcherDAO.fetchOrders(start.toString(), end.toString());
    }


    public Map<String, InvoiceReciever> getNameToInvoiceRecieverMap() {
        return this.nameToInvoiceRecieverMap;
    }

    public int sendNewEntry(OrderDTO orderDTO) throws SQLException {

        RawOrderData rawOrderData = convertToRawOrderData(orderDTO);
        return datafetcherDAO.sendNewEntry(rawOrderData);
    }


    public double getUnitPrice(String goodsCategory) {

        GoodsCategory g = nameToGoodsCategoryMap.get(goodsCategory);
        if (g == null)
            return -1;
        return g.getUnitPrice();
    }

    public int deleteLastEntry() throws SQLException {
        return datafetcherDAO.deleteLastEntry();
    }


    public List<String> addNewInvoiceReciever(String company, String address, String contact, String phone, String email) throws SQLException {
        InvoiceReciever invoiceReciever = new InvoiceReciever(0, company, address, contact, phone, email);
        datafetcherDAO.addInvoiceReciever(invoiceReciever);
        List<InvoiceReciever> newInvoiceRecivers = datafetcherDAO.getAllInvoiceRecievers();
        return updateInvoiceRecieveMaps(newInvoiceRecivers);
    }

    public List<String> updateInvoiceReciever(String selectedReciever, ChangebleInvoiceRecieverAttrs attrs) throws SQLException {
        InvoiceReciever reciever = nameToInvoiceRecieverMap.get(selectedReciever);
        reciever.setAddress(attrs.getAddress());
        reciever.setContact(attrs.getContact());
        reciever.setPhone(attrs.getPhone());
        reciever.setEmail(attrs.getEmail());

        datafetcherDAO.updateInvoiceReciever(reciever);
        List<InvoiceReciever> newinvoiceRecievers = datafetcherDAO.getAllInvoiceRecievers();
        return updateInvoiceRecieveMaps(newinvoiceRecievers);

    }

    public List<String> updateGoodsCategory(String selectedCategory, double newUnitPrice) throws SQLException {
        GoodsCategory goodsCategory = nameToGoodsCategoryMap.get(selectedCategory);
        goodsCategory.setUnitPrice(newUnitPrice);
        datafetcherDAO.updateGoodscategory(goodsCategory);
        List<GoodsCategory> newGoodsCategories = datafetcherDAO.getAllGoodsCategories();
        return updateGoodsCategorysMaps(newGoodsCategories);


    }


    public List<String> addAccount(String accountName) throws SQLException {
        Account account = new Account(0, accountName);
        datafetcherDAO.addAccount(account);
        List<Account> newAccounts = datafetcherDAO.getAllAccounts();
        return updateAccountMaps(newAccounts);

    }

    public List<String> addGoodsGategory(String goodsCategory, double unitPrice) throws SQLException {

        GoodsCategory category = new GoodsCategory(0, goodsCategory, unitPrice);
        datafetcherDAO.addGoodsCategory(category);
        List<GoodsCategory> newGoodsCategories = datafetcherDAO.getAllGoodsCategories();
        return updateGoodsCategorysMaps(newGoodsCategories);
    }


    public List<String> deleteInvoiceReciever(String selectedInvoiceReciever) throws SQLException {

        InvoiceReciever invoiceReciever = nameToInvoiceRecieverMap.get(selectedInvoiceReciever);
        datafetcherDAO.deteleInvoiceReciever(invoiceReciever);
        List<InvoiceReciever> newInvoiceRecievers = datafetcherDAO.getAllInvoiceRecievers();
        return updateInvoiceRecieveMaps(newInvoiceRecievers);
    }

    public List<String> deleteGoodsCategory(String selectedGoodsCategory) throws SQLException {

        GoodsCategory goodsCategory = nameToGoodsCategoryMap.get(selectedGoodsCategory);

        datafetcherDAO.deleteGoodsCategory(goodsCategory);
        List<GoodsCategory> newGoodsCategories = datafetcherDAO.getAllGoodsCategories();
        return updateGoodsCategorysMaps(newGoodsCategories);

    }

    public List<String> deleteAccount(String selectedAccount) throws SQLException {

        Account account = nameToAccountMap.get(selectedAccount);
        datafetcherDAO.deleteAccount(account);
        List<Account> newAccounts = datafetcherDAO.getAllAccounts();
        return updateAccountMaps(newAccounts);
    }


    public int deleteEntry(OrderDTO selectedRow) throws SQLException {
        return datafetcherDAO.deleteEntry(selectedRow);
    }

    public String resolveEmail(String invoicereciver) {
        return nameToInvoiceRecieverMap.get(invoicereciver).getEmail();
    }

    /// / Private Domain
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
            accountToIdMap.put(account, account.getId());
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
        String invoiceSent = orderDTO.getMailedDate();

        return new RawOrderData(0, date, invoiceID, accountID, goodsCatID, noOfUnits, totalPrice, comment, invoiceSent);

    }


    public void pdfsCreated(Map<String, OrderSummaryDTO> currentSummaryMap, LocalDate date) {

        // TODO: 2016-09-28 Implement "pdfsCreated"
    }

    public void pdfCreated(OrderSummaryDTO summaryDTO, LocalDate date) {

        // TODO: 2016-09-28 Implement "pdfCreated"
    }



}
