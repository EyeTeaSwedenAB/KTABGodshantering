package com.peter.integration;

import com.peter.model.dto.AccountDTO;
import com.peter.model.dto.GoodsCategotyDTO;
import com.peter.model.dto.InvoiceRecieverDTO;
import com.peter.model.integrationreqirements.Credentials;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by andreajacobsson on 2016-08-22.
 */
public interface DatabaseHandler {

    void setUrl(String url);
    void setCredentials(Credentials credentials);
    boolean testConnection() throws SQLException;
    List<AccountDTO> getAccounts() throws SQLException;
    List<InvoiceRecieverDTO> getInvoiceRecievers() throws SQLException;
    List<GoodsCategotyDTO> getGoodsCategories() throws SQLException;

}
