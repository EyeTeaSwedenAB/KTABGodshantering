package com.peter.model.business.excel;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Created by andreajacobsson on 2016-09-07.
 */
class CellStyleBuilder {

    private XSSFCellStyle style;
    private Font font;

    CellStyleBuilder(XSSFWorkbook wb) {
        this.style = wb.createCellStyle();
        this.font = wb.createFont();
        style.setFont(font);
    }


    CellStyleBuilder backGround(IndexedColors colors, short cellStyle) {

        style.setFillForegroundColor(colors.getIndex());
        style.setFillPattern(cellStyle);
        return this;
    }

    CellStyleBuilder bottomBorder(short cellStyle) {
        style.setBorderBottom(cellStyle);
        return this;
    }

    CellStyleBuilder bottomBorder(BorderStyle borderStyle) {
        style.setBorderBottom(borderStyle);
        return this;
    }

    CellStyleBuilder topBorder(short cellStyle) {
        style.setBorderTop(cellStyle);
        return this;

    }

    CellStyleBuilder topBorder(BorderStyle borderStyle) {
        style.setBorderTop(borderStyle);
        return this;

    }

    CellStyleBuilder rightBorder(short cellStyle) {
        style.setBorderRight(cellStyle);
        return this;
    }

    CellStyleBuilder rightBorder(BorderStyle borderStyle) {
        style.setBorderRight(borderStyle);
        return this;
    }

    CellStyleBuilder leftBorder(short cellStyle) {
        style.setBorderLeft(cellStyle);
        return this;

    }

    CellStyleBuilder leftBorder(BorderStyle borderStyle) {
        style.setBorderLeft(borderStyle);
        return this;
    }

    CellStyleBuilder fontHeght(short height) {

        font.setFontHeightInPoints(height);
        return this;
    }

    CellStyleBuilder fontName(String name) {
        font.setFontName(name);
        return this;
    }

    CellStyleBuilder fontItalic(boolean italic) {
        font.setItalic(italic);
        return this;
    }


    CellStyleBuilder fontColor(IndexedColors color) {
        font.setColor(color.getIndex());
        return this;
    }

    CellStyleBuilder fontUnderLine(byte underLine) {
        font.setUnderline(underLine);
        return this;
    }

    CellStyleBuilder fontBold(boolean bold){
        font.setBold(bold);
        return this;
    }


    XSSFCellStyle build() {
        return style;
    }


}
