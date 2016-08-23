package com.peter.controller.maincontroller;

import com.peter.exceptions.DatabaseHandlerNotInitializedException;
import com.peter.integration.DatabaseHandler;
import com.peter.integration.DatabaseHandlerImpl;
import com.peter.model.dto.AccountDTO;
import com.peter.model.dto.GoodsCategotyDTO;
import com.peter.model.dto.InvoiceRecieverDTO;
import com.peter.model.integrationreqirements.Credentials;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by andreajacobsson on 2016-08-22.
 */
public class MainController {

    private DatabaseHandler databaseHandler = new DatabaseHandlerImpl();

    public MainController() {
    }

    public MainController(String userName, String password) {
        databaseHandler.setCredentials(new Credentials(userName, password));
    }

    public void setCredentials(String usename, String password) {
        databaseHandler.setCredentials(new Credentials(usename, password));
    }


    public boolean testConnection() throws SQLException {
        if (databaseInitialized())
            return databaseHandler.testConnection();
        else throw new DatabaseHandlerNotInitializedException("Current database is null");

    }

    public List<AccountDTO> getAccounts() throws SQLException {

        if (databaseInitialized())
            return databaseHandler.getAccounts();
        else
            throw new DatabaseHandlerNotInitializedException("Current database is null");
    }

    public List<GoodsCategotyDTO> getAllGoodsCategories() throws SQLException {
        if (databaseInitialized())
            return databaseHandler.getGoodsCategories();
        else
            throw new DatabaseHandlerNotInitializedException("Current database is null");
    }


    public List<InvoiceRecieverDTO> getAllInvoiceRecievers() throws SQLException {
        if (databaseInitialized())
            return databaseHandler.getInvoiceRecievers();

        else
            throw new DatabaseHandlerNotInitializedException("Current database is null");
    }

    // PRIVATE DOMAIN
    ///////////////////////////////////////////////////////////////////////////////

    private boolean databaseInitialized() {
        return databaseHandler != null;
    }

    public String tjtj(){
        return null;
    }

}
