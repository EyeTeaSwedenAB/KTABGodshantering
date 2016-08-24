package com.peter.integration.database;

import com.peter.dtos.AccountDTO;
import com.peter.dtos.GoodsCategoryDTO;
import com.peter.dtos.InvoiceRecieverDTO;
import com.peter.dtos.RawOrderDataDTO;
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

    public abstract List<AccountDTO> getAllAccounts() throws SQLException;

    public abstract List<InvoiceRecieverDTO> getAllInvoiceRecievers() throws SQLException;

    public abstract List<GoodsCategoryDTO> getAllGoodsCategories() throws SQLException;

    public abstract List<RawOrderDataDTO> fetchRawOrderData(int limit) throws SQLException;

    public abstract int sendNewEntry(RawOrderDataDTO rawOrderDataDTO) throws SQLException;

    public static DatabaseHandler getNewInstance() {
        return new DatabaseHandlerImpl();
    }

    public static DatabaseHandler getNewInstance(String url, Credentials credentials) {
        return new DatabaseHandlerImpl(url, credentials);
    }

}
