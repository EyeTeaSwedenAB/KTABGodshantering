package com.peter.startup;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellUtil;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;

/**
 * Created by andreajacobsson on 2016-08-24.
 */
public class Playground {

    public static void main(String[] args) throws Exception {
        StringBuilder sb = new StringBuilder();

        String s = sb.append("hej").append(3).append("Hejdå").toString();
        System.out.println(s);


    }

    void itareateExistingExcel() throws URISyntaxException, IOException, InvalidFormatException {

        File file = new File(getClass().getResource("/excel/Sommarschema SC 2016 v18-35.xlsx").toURI());
        if (file.exists()) {
            Workbook wb = new XSSFWorkbook(file);
            Sheet sheet = wb.getSheetAt(0);

            for (Row row : sheet) {

                for (Cell cell : row) {

                    System.out.print("|" + cell.getStringCellValue() + "|\t");


                }
                System.out.println();
            }
        }
    }

    void createFancyLookingExcel() {

        Workbook workbook = new XSSFWorkbook();
        Cell cell = workbook.createSheet("The FAT Sheet").createRow(0).createCell(0);
        CellStyle style = workbook.createCellStyle();
        style.setFillForegroundColor(HSSFColor.LIME.index);
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style.setBorderBottom(CellStyle.BORDER_THICK);
        cell.setCellValue("Hej på dig");
        cell.setCellStyle(style);

        try {
            workbook.write(new FileOutputStream(new File("The fat xl.xlsx")));
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static final class BAJS {
        public static final int bajsInt = 3;

        public void print() {
            System.out.println("Printing");
        }
    }

    void doEvenMoreStuffWithExcel() {

        Workbook wb = new XSSFWorkbook();  // or new XSSFWorkbook()
        Sheet sheet1 = wb.createSheet("new sheet");

        // Create a merged region
        Row row = sheet1.createRow(1);
        Row row2 = sheet1.createRow(2);
        Cell cell = row.createCell(1);
        cell.setCellValue("This is a test of merging");
        CellRangeAddress region = CellRangeAddress.valueOf("B2:E5");
        sheet1.addMergedRegion(region);

        // Set the border and border colors.
        final short borderMediumDashed = CellStyle.BORDER_MEDIUM_DASHED;
        RegionUtil.setBorderBottom(borderMediumDashed, region, sheet1, wb);
        RegionUtil.setBorderTop(borderMediumDashed, region, sheet1, wb);
        RegionUtil.setBorderLeft(borderMediumDashed, region, sheet1, wb);
        RegionUtil.setBorderRight(borderMediumDashed, region, sheet1, wb);
        RegionUtil.setBottomBorderColor(IndexedColors.AQUA.getIndex(), region, sheet1, wb);
        RegionUtil.setTopBorderColor(IndexedColors.AQUA.getIndex(), region, sheet1, wb);
        RegionUtil.setLeftBorderColor(IndexedColors.AQUA.getIndex(), region, sheet1, wb);
        RegionUtil.setRightBorderColor(IndexedColors.AQUA.getIndex(), region, sheet1, wb);

        // Shows some usages of HSSFCellUtil
        CellStyle style = wb.createCellStyle();
        style.setIndention((short) 4);
        CellUtil.createCell(row, 8, "This is the value of the cell", style);
        Cell cell2 = CellUtil.createCell(row2, 8, "This is the value of the cell");
        CellUtil.setAlignment(cell2, wb, CellStyle.ALIGN_CENTER);

        // Write out the workbook
        FileOutputStream fileOut = null;
        try {
            fileOut = new FileOutputStream("The FATTTTTT XLS.xlsx");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            wb.write(fileOut);
            fileOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
