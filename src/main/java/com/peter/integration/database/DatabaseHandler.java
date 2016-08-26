package com.peter.integration.database;

import com.peter.dto.OrderDTO;
import com.peter.model.Account;
import com.peter.model.GoodsCategory;
import com.peter.model.InvoiceReciever;
import com.peter.model.RawOrderData;
import com.peter.integration.integrationrequirements.Credentials;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by andreajacobsson on 2016-08-22.
 */
public abstract class DatabaseHandler {

    public abstract void setUrl(String url);

    public abstract void setCredentials(Credentials credentials);

    public abstract void testConnection() throws SQLException;

    public abstract List<Account> getAllAccounts() throws SQLException;

    public abstract List<InvoiceReciever> getAllInvoiceRecievers() throws SQLException;

    public abstract List<GoodsCategory> getAllGoodsCategories() throws SQLException;

    public abstract List<OrderDTO> fetchOrders(int limit) throws SQLException;

    public abstract List<OrderDTO> fetchOrders(String date) throws SQLException;

    public abstract int sendNewEntry(RawOrderData rawOrderData) throws SQLException;

    public abstract int deleteLastEntry() throws SQLException;

    public abstract int delete(DeleteCritera deleteCritera) throws SQLException;

    public static DatabaseHandler getNewInstance() {
        return new DatabaseHandlerImpl();
    }

    public static DatabaseHandler getNewInstance(String url, Credentials credentials) {
        return new DatabaseHandlerImpl(url, credentials);
    }

}
