package com.peter.model.business.excel;

import com.peter.dto.OrderDTO;
import com.peter.dto.OrderSummaryDTO;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by andreajacobsson on 2016-09-05.
 */
public class ExcelPrinter {


    private XSSFWorkbook workbook;
    private Sheet sheet;
    private Row globalRow;
    private Cell globalCell;
    int gobalRowCounter = 1;
    int globalColCounter = 0;

    public void print(Map<String, OrderSummaryDTO> orderSummaryDTOMap, File file) throws IOException {

        workbook = new XSSFWorkbook();
        sheet = workbook.createSheet();
        createSingleColumnFilledHeader("FAKURAUDERLAG", IndexedColors.YELLOW, (short) 24);
        fillData(orderSummaryDTOMap);
        autoSizeColumns();

        FileOutputStream fout = new FileOutputStream(file, false);
        workbook.write(fout);
        workbook.close();


    }

    private void autoSizeColumns() {
        for (Row row : sheet)
            for (Cell cell : row)
                sheet.autoSizeColumn(cell.getColumnIndex());
    }

    private void fillData(Map<String, OrderSummaryDTO> orderSummaryDTOMap) {

        gobalRowCounter += 5;

        for (String key : orderSummaryDTOMap.keySet()) {
            createSingleColumnFilledHeader(key, IndexedColors.BRIGHT_GREEN, (short) 16);

            gobalRowCounter++;
            createSmallHeaders();
            List<OrderDTO> monthlyOrders = orderSummaryDTOMap.get(key).getMonthlyOrders();

            for (OrderDTO orderDTO : monthlyOrders) {
                globalRow = sheet.createRow(gobalRowCounter);
                globalRow.createCell(globalColCounter++).setCellValue(orderDTO.getDate());
                globalRow.createCell(globalColCounter++).setCellValue(orderDTO.getGoodsCategory());
                globalRow.createCell(globalColCounter++).setCellValue(orderDTO.getComment());
                globalRow.createCell(globalColCounter++).setCellValue(orderDTO.getDestination());
                globalRow.createCell(globalColCounter++).setCellValue(orderDTO.getNoOfUnits());
                globalRow.createCell(globalColCounter++).setCellValue(orderDTO.getUnitPrice());
                globalRow.createCell(globalColCounter++).setCellValue(orderDTO.getTotalPrice());
                globalRow.createCell(globalColCounter++).setCellValue(orderDTO.getInvoiceSent());
                gobalRowCounter++;
                globalColCounter = 0;
            }
            sumUpMonthlyDue(orderSummaryDTOMap.get(key).getMonthlyDueAmount());

            gobalRowCounter += 3;
            globalColCounter = 0;
        }
    }

    private void sumUpMonthlyDue(double due) {
        CellStyle style = new CellStyleBuilder(workbook).fontHeght((short) 14).fontBold(true).build();
        globalRow = sheet.createRow(gobalRowCounter);
        globalRow.setRowStyle(style);

        globalCell = globalRow.createCell(globalColCounter + 5);
        globalCell.setCellStyle(style);
        globalCell.setCellValue("TOTAL:");

        globalCell = globalRow.createCell(globalColCounter + 6);
        globalCell.setCellStyle(style);
        globalCell.setCellValue(due);
    }

    private void createSingleColumnFilledHeader(String cellValue, IndexedColors color, short fontSize) {
        globalRow = sheet.createRow(gobalRowCounter);
        globalCell = globalRow.createCell(globalColCounter);
        globalCell.setCellValue(cellValue);
        CellStyle style = new CellStyleBuilder(workbook).backGround(color, CellStyle.SOLID_FOREGROUND).fontHeght(fontSize).build();
        globalRow.setRowStyle(style);
        globalCell.setCellStyle(style);
        globalColCounter = 0;
    }

    private void createSmallHeaders() {
        globalRow = sheet.createRow(gobalRowCounter++);
        globalRow.createCell(globalColCounter++).setCellValue("Datum");
        globalRow.createCell(globalColCounter++).setCellValue("Godskategori");
        globalRow.createCell(globalColCounter++).setCellValue("Kommentar");
        globalRow.createCell(globalColCounter++).setCellValue("Destination");
        globalRow.createCell(globalColCounter++).setCellValue("Antal enheter");
        globalRow.createCell(globalColCounter++).setCellValue("Á pris");
        globalRow.createCell(globalColCounter++).setCellValue("Totalt");
        globalRow.createCell(globalColCounter++).setCellValue("Betald");
        globalColCounter = 0;

        CellStyle cellStyle = new CellStyleBuilder(workbook).fontBold(true).build();
        for (Cell cell : globalRow)
            cell.setCellStyle(cellStyle);

    }


}
