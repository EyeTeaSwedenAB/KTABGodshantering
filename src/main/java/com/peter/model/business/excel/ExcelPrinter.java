package com.peter.model.business.excel;

import com.peter.dto.OrderDTO;
import com.peter.dto.OrderSummaryDTO;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

/**
 * Created by andreajacobsson on 2016-09-05.
 */
public class ExcelPrinter {


    private XSSFWorkbook workbook;
    private Sheet sheet;

    public ExcelPrinter() {
        workbook = new XSSFWorkbook();
        sheet = workbook.createSheet();
    }

    public void print(Map<String, OrderSummaryDTO> orderSummaryDTOMap, File file) throws IOException {


        if (!file.getAbsolutePath().endsWith(".xlsx")){
            file = new File(file.getAbsolutePath() + ".xlsx");
        }

        int currentRow = 1;
        currentRow = createSingleColumnFilledHeader("FAKTURAUNDERLAG", IndexedColors.YELLOW, (short) 24, currentRow, 0);

        for (String key : orderSummaryDTOMap.keySet()) {
            currentRow = createSingleColumnFilledHeader(key, IndexedColors.BRIGHT_GREEN, (short) 16, currentRow + 1, 0);
            currentRow = createSmallHeaders(currentRow, 0);
            for (OrderDTO orderDTO : orderSummaryDTOMap.get(key).getMonthlyOrders()) {
                currentRow = writeDTO(orderDTO, currentRow, 0);
            }
            currentRow = sumUpMonthlyDue(orderSummaryDTOMap.get(key).getMonthlyDueAmount(), currentRow, 5);
        }
        autoSizeColumns();

        FileOutputStream fout = new FileOutputStream(file, false);
        workbook.write(fout);
        workbook.close();

    }

    // PRIVATE DOMAIN
    ///////////////////////////////////////////////////////////////////////////

    private int writeDTO(OrderDTO orderDTO, int startRow, int startCol) {
        Row row = sheet.createRow(startRow);
        row.createCell(startCol++).setCellValue(orderDTO.getDate());
        row.createCell(startCol++).setCellValue(orderDTO.getGoodsCategory());
        row.createCell(startCol++).setCellValue(orderDTO.getComment());
        row.createCell(startCol++).setCellValue(orderDTO.getDestination());
        row.createCell(startCol++).setCellValue(orderDTO.getNoOfUnits());
        row.createCell(startCol++).setCellValue(orderDTO.getUnitPrice());
        row.createCell(startCol++).setCellValue(orderDTO.getTotalPrice());
        row.createCell(startCol++).setCellValue(orderDTO.getMailedDate());
        return startRow + 1;

    }

    private int sumUpMonthlyDue(double due, int startRow, int startCol) {
        CellStyle style = new CellStyleBuilder(workbook).fontHeght((short) 14).fontBold(true).build();
        Row row = sheet.createRow(startRow);
        row.setRowStyle(style);

        Cell cell = row.createCell(startCol);
        cell.setCellStyle(style);
        cell.setCellValue("TOTAL:");

        cell = row.createCell(startCol + 1);
        cell.setCellStyle(style);
        cell.setCellValue(due);

        return startRow + 1;
    }


    private int createSingleColumnFilledHeader(String cellValue, IndexedColors color, short fontSize, int startRow, int startCol) {
        Row row = sheet.createRow(startRow);
        Cell cell = row.createCell(startCol);
        cell.setCellValue(cellValue);
        CellStyle style = new CellStyleBuilder(workbook).backGround(color, CellStyle.SOLID_FOREGROUND).fontHeght(fontSize).build();
        row.setRowStyle(style);
        cell.setCellStyle(style);
        return startRow + 1;
    }

    private int createSmallHeaders(int startRow, int startCol) {
        Row row = sheet.createRow(startRow);
        row.createCell(startCol++).setCellValue("Datum");
        row.createCell(startCol++).setCellValue("Godskategori");
        row.createCell(startCol++).setCellValue("Kommentar");
        row.createCell(startCol++).setCellValue("Destination");
        row.createCell(startCol++).setCellValue("Antal enheter");
        row.createCell(startCol++).setCellValue("Á pris");
        row.createCell(startCol++).setCellValue("Totalt");
        row.createCell(startCol++).setCellValue("Faktura Skickad");

        CellStyle cellStyle = new CellStyleBuilder(workbook).fontBold(true).build();

        for (Cell cell : row)
            cell.setCellStyle(cellStyle);
        return startRow + 1;

    }

    private void autoSizeColumns() {
        for (Row row : sheet)
            for (Cell cell : row)
                sheet.autoSizeColumn(cell.getColumnIndex());
    }


}
