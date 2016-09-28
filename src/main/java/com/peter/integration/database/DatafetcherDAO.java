package com.peter.integration.database;

import com.peter.dto.OrderDTO;
import com.peter.integration.integrationrequirements.Credentials;
import com.peter.model.data.Account;
import com.peter.model.data.GoodsCategory;
import com.peter.model.data.InvoiceReciever;
import com.peter.model.data.RawOrderData;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by andreajacobsson on 2016-08-22.
 */
public abstract class DatafetcherDAO {

    public abstract void setUrl(String url);

    public abstract void setCredentials(Credentials credentials);

    public abstract void testConnection() throws SQLException;

    public abstract List<Account> getAllAccounts() throws SQLException;

    public abstract List<InvoiceReciever> getAllInvoiceRecievers() throws SQLException;

    public abstract List<GoodsCategory> getAllGoodsCategories() throws SQLException;

    public abstract List<OrderDTO> fetchOrders(int limit) throws SQLException;

    public abstract List<OrderDTO> fetchOrders(String date) throws SQLException;

    public abstract List<OrderDTO> fetchOrders(String startDate, String endDate) throws SQLException;

    public abstract int sendNewEntry(RawOrderData rawOrderData) throws SQLException;

    public abstract int deleteLastEntry() throws SQLException;

    public abstract int delete(DeleteCritera deleteCritera) throws SQLException;

    public abstract int addInvoiceReciever(InvoiceReciever invoiceReciever) throws SQLException;

    public abstract int addGoodsCategory(GoodsCategory goodsCategory) throws SQLException;

    public abstract int addAccount(Account account) throws SQLException;

    public abstract int deteleInvoiceReciever(InvoiceReciever invoiceReciever) throws SQLException;

    public abstract int deleteGoodsCategory(GoodsCategory goodsCategory) throws SQLException;

    public static DatafetcherDAO getNewInstance() {
        return new DatafetcherDAOImpl();
    }

    public static DatafetcherDAO getNewInstance(String url, Credentials credentials) {
        return new DatafetcherDAOImpl(url, credentials);
    }

    public abstract int deleteAccount(Account account) throws SQLException;

    public abstract int deleteEntry(OrderDTO selectedRow) throws SQLException;

}
