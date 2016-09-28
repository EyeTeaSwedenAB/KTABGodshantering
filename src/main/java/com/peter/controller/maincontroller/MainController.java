package com.peter.controller.maincontroller;

import com.peter.dto.OrderDTO;
import com.peter.dto.OrderSummaryDTO;
import com.peter.exceptions.NonValidDirectoryException;
import com.peter.exceptions.WrongFilenameFormatException;
import com.peter.model.business.excel.ExcelPrinter;
import com.peter.model.business.mail.MailManager;
import com.peter.model.business.pdf.PDFManager;
import com.peter.model.business.summary.Summarizer;
import com.peter.model.data.DataManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Observer;

/**
 * Created by andreajacobsson on 2016-08-22.
 */
public class MainController {

    private DataManager dataManager;
    private Summarizer summarizer;
    private ExcelPrinter excelPrinter;
    private PDFManager pdfManager;
    private MailManager mailManager;

    public MainController() {

        dataManager = new DataManager();
        summarizer = new Summarizer();
        excelPrinter = new ExcelPrinter();
        pdfManager = new PDFManager();
        mailManager = new MailManager();
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

    public List<String> addNewInvoiceReciever(String company, String address, String contact, String phone, String email) throws SQLException {
        return dataManager.addNewInvoiceReciever(company, address, contact, phone, email);
    }

    public List<String> addGoodsCategory(String goodsCategory, double unitPrice) throws SQLException {

        return dataManager.addGoodsGategory(goodsCategory, unitPrice);
    }

    public List<String> addAccount(String account) throws SQLException {

        return dataManager.addAccount(account);
    }

    public List<String> deleteInvoiceReciever(String selectedInvoiceReciever) throws SQLException {
        return dataManager.deleteInvoiceReciever(selectedInvoiceReciever);
    }

    public List<String> deleteGoodsCategory(String selectedGoodsCategory) throws SQLException {

        return dataManager.deleteGoodsCategory(selectedGoodsCategory);
    }

    public List<String> deleteAccount(String selectedAccount) throws SQLException {
        return dataManager.deleteAccount(selectedAccount);
    }

    public void deleOrder(OrderDTO selectedRow) throws SQLException {
        dataManager.deleteEntry(selectedRow);
    }

    public Map<String, OrderSummaryDTO> getSummary(LocalDate start, LocalDate end) throws SQLException {

        return summarizer.summarize(dataManager.getOrders(start, end));
    }

    public void printToExcel(Map<String, OrderSummaryDTO> summaryMap, File file) throws IOException {
        excelPrinter.print(summaryMap, file);
    }

    public void generatePDFs(File directory, Map<String, OrderSummaryDTO> currentSummaryMap) throws FileNotFoundException, NonValidDirectoryException {
        pdfManager.createPDFs(directory, currentSummaryMap);
    }

    public void generatePDF(File directory, OrderSummaryDTO summaryDTO) throws FileNotFoundException, NonValidDirectoryException {
        pdfManager.createSinglePDF(directory, summaryDTO);
    }

    public void mailPDFs(File[] pdfFiles) throws WrongFilenameFormatException {

        mailManager.sendMultipleMails(pdfFiles, dataManager.getNameToInvoiceRecieverMap());
    }

    public void addMailManagerObserver(Observer observer) {
        mailManager.addObserver(observer);
    }


    // PRIVATE DOMAIN
    ///////////////////////////////////////////////////////////////////////////////


}
