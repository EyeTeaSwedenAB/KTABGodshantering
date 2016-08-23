package com.peter.integration;

import com.peter.model.dto.AccountDTO;
import com.peter.model.dto.GoodsCategotyDTO;
import com.peter.model.dto.InvoiceRecieverDTO;
import com.peter.model.integrationreqirements.Credentials;

import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by andreajacobsson on 2016-08-23.
 */
public class DatabaseHandlerImplTest {

    private DatabaseHandlerImpl databaseHandler;

    @org.junit.Before
    public void setUp() throws Exception {
        databaseHandler = new DatabaseHandlerImpl(new Credentials("pebo0602", "PetBob82"));
    }

    @org.junit.After
    public void tearDown() throws Exception {
        databaseHandler = null;

    }

    @org.junit.Test
    public void setCredentials() throws Exception {

    }

    @org.junit.Test
    public void testConnection() throws Exception {
        assertTrue("Successfully connected to the database§", databaseHandler.testConnection());
    }

    @org.junit.Test
    public void getAccounts() throws Exception {
        List<AccountDTO> accountDTOs = databaseHandler.getAccounts();
        System.out.println("------------------ACCOUNTS---------------");
        if (accountDTOs != null)
            for (AccountDTO a : accountDTOs)
                System.out.println(a);

        System.out.println("-----------------------------------------x");
        System.out.println();
        assertNotNull("getaccounts should not be null", accountDTOs);

    }

    @org.junit.Test
    public void getInvoiceRecievers() throws Exception {

        List<InvoiceRecieverDTO> invoiceRecieverDTOs = databaseHandler.getInvoiceRecievers();
        System.out.println("----------INVOICE RECIEVERS-----------");
        if (invoiceRecieverDTOs != null)
            for (InvoiceRecieverDTO i : invoiceRecieverDTOs)
                System.out.println(i);

        System.out.println("--------------------------------------");
        System.out.println();

        assertNotNull("getInvoiceRecievers should not be null", invoiceRecieverDTOs);
    }

    @org.junit.Test
    public void getGoodsCategories() throws Exception {

        List<GoodsCategotyDTO> goodsCategoties = databaseHandler.getGoodsCategories();
        System.out.println("----------------GOODS CATEGORIES---------");
        if (goodsCategoties != null)
            for (GoodsCategotyDTO g : goodsCategoties)
                System.out.println(g);

        System.out.println("------------------------------------------");
        System.out.println();

        assertNotNull("goodscat should not be null", goodsCategoties);

    }

}