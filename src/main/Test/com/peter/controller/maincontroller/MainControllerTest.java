package com.peter.controller.maincontroller;

import com.peter.dto.AccountDTO;
import com.peter.dto.GoodsCategoryDTO;
import com.peter.dto.InvoiceRecieverDTO;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by andreajacobsson on 2016-08-23.
 */
public class MainControllerTest {

    MainController mainController;

    @Before
    public void setUp() throws Exception {

        mainController = new MainController();
        mainController.setLoginInformation("jdbc:mysql://ktabtest.cyzgfcxn1ubh.eu-central-1.rds.amazonaws.com:3306/KTABGoodsTest", "pebo0602", "PetBob82");
    }

    @After
    public void tearDown() throws Exception {
        mainController = null;
    }

    @Test
    public void setLoginInformation() throws Exception {
        mainController.setLoginInformation("jdbc:mysql://ktabtest.cyzgfcxn1ubh.eu-central-1.rds.amazonaws.com:3306/KTABGoodsTest", "pebo0602", "PetBob82");

    }

    @Test
    public void testConnection() {
        try {
            mainController.testConnection();
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

    @Test
    public void getAllAccounts() {
        try {
            List<AccountDTO> accountDTOs = mainController.getAllAccounts();
            System.out.println("-----------AccountDTOS--------------------");
            for (AccountDTO a : accountDTOs)
                System.out.println(a);
            System.out.println("------------------------------------------");
            System.out.println();

            assertNotEquals(0, accountDTOs.size());

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void getAllGoodsCategories() {

        try {
            List<GoodsCategoryDTO> goodsCatDTOs = mainController.getAllGoodsCategories();
            System.out.println("-----------GoodsCategoryDTOS--------------------");
            for (GoodsCategoryDTO g : goodsCatDTOs)
                System.out.println(g);
            System.out.println("------------------------------------------");
            System.out.println();

            assertNotEquals(0, goodsCatDTOs.size());

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void getAllInvoiceRecievers() {

        try {
            List<InvoiceRecieverDTO> invoiceDTOS = mainController.getAllInvoiceRecievers();
            System.out.println("-----------InviceReciverDTOS--------------------");
            for (InvoiceRecieverDTO i : invoiceDTOS)
                System.out.println(i);
            System.out.println("------------------------------------------");
            System.out.println();

            assertNotEquals(0, invoiceDTOS.size());

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}