package com.peter.model.business;

import com.peter.controller.maincontroller.MainController;
import com.peter.dto.OrderSummaryDTO;
import com.peter.model.business.excel.ExcelPrinter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Map;

/**
 * Created by andreajacobsson on 2016-09-07.
 */
public class ExcelPrinterTest {

    MainController mainController;
    Map<String, OrderSummaryDTO> summaryMap;

    @Before
    public void setUp() throws Exception {
        mainController = new MainController();
        mainController.setLoginInformation("jdbc:mysql://ktabtest.cyzgfcxn1ubh.eu-central-1.rds.amazonaws.com:3306/KTABGoodsTest", "pebo0602", "PetBob82");
        try {
            mainController.testConnection();
            summaryMap = mainController.getSummary(LocalDate.of(2016, 8, 1), LocalDate.of(2016, 8, 31));
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("SHIT HAPPENS");
        }


    }

    @After
    public void tearDown() throws Exception {

        mainController = null;
        summaryMap = null;

    }

    @Test
    public void print() throws Exception {

        ExcelPrinter printer = new ExcelPrinter();
        printer.print(summaryMap, new File("test.xlsx"));



    }

}