package com.peter.model.business.pdf;

import com.itextpdf.io.font.FontConstants;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.peter.Util.SweMonthConverter;
import com.peter.dto.OrderDTO;
import com.peter.dto.OrderSummaryDTO;
import com.peter.exceptions.NonValidDirectoryException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Map;

/**
 * Created by andreajacobsson on 2016-09-20.
 */
public class PDFManager {

    public PDFManager createPDFs(File directory, Map<String, OrderSummaryDTO> summaryDTOMap) throws NonValidDirectoryException, FileNotFoundException {

        for (String invoiceReciver : summaryDTOMap.keySet()) {
            createSinglePDF(directory, summaryDTOMap.get(invoiceReciver));
        }

        return this;
    }

    public PDFManager createSinglePDF(File directory, OrderSummaryDTO summaryDTO) throws NonValidDirectoryException, FileNotFoundException {

        if (!directory.isDirectory())
            throw new NonValidDirectoryException("Not a valid directory");

        String filePath = buildAbsoluteFilePath(directory, summaryDTO);

        PdfWriter pdfWriter = new PdfWriter(new FileOutputStream(filePath));
        PdfDocument pdfDocument = new PdfDocument(pdfWriter);
        PageSize pageSize = PageSize.A4;
        Document document = new Document(pdfDocument, pageSize);

        designDocument(document, summaryDTO.getInvoiceReciver(), summaryDTO.getEndingDate());

        float lineStartX = 0.1f * pageSize.getWidth();
        float lineEndX = 0.9f * pageSize.getWidth();
        float yCoord = 0.9f * pageSize.getHeight();

        drawLine(pdfDocument, lineStartX, yCoord, lineEndX, yCoord);


        Table table = generateDefaultTable();

        for (OrderDTO orderDTO : summaryDTO.getMonthlyOrders()) {
            addRowToTable(table, orderDTO);
        }

        addFooters(summaryDTO, table);

        document.add(table);
        drawLine(pdfDocument, lineStartX, yCoord, lineEndX, yCoord);
        document.close();

        return this;
    }

    // PRIVATE DOMAIN
    ///////////////////////////////////////////////////////////////////////////////////////


    private void designDocument(Document document, String invoiceReciver, LocalDate endingDate) {

        Image logoImage = new Image(ImageDataFactory.create(getClass().getResource("/images/karingoLogo.png")));

        Paragraph logoParagraph = new Paragraph()
                .add(logoImage)
                .setMarginBottom(20);

        String month = SweMonthConverter.convert(endingDate.getMonth()).getName();
        String year = Integer.toString(endingDate.getYear());

        Paragraph headeParagraph = new Paragraph("Fakturaunderlag för: " + invoiceReciver + " " + month + ", " + year)
                .setFontSize(20)
                .setFontColor(Color.GRAY)
                .setMarginBottom(20);

        document.add(logoParagraph)
                .add(headeParagraph);
    }


    private Table generateDefaultTable() {
        Table table = new Table(new float[]{10, 15, 10, 5, 10, 10});
        PdfFont headerFont = null;

        try {
            headerFont = PdfFontFactory.createFont(FontConstants.HELVETICA_BOLD);
        } catch (IOException e) {
            e.printStackTrace();
        }
        table.setWidthPercent(100);
        table.addHeaderCell(new Cell().add(new Paragraph("Datum").setFont(headerFont)));
        table.addHeaderCell(new Cell().add(new Paragraph("Transporterat").setFont(headerFont)));
        table.addHeaderCell(new Cell().add(new Paragraph("Destination").setFont(headerFont)));
        table.addHeaderCell(new Cell().add(new Paragraph("Antal").setFont(headerFont)));
        table.addHeaderCell(new Cell().add(new Paragraph("Styckpris").setFont(headerFont)));
        table.addHeaderCell(new Cell().add(new Paragraph("Totalt").setFont(headerFont)));
        return table;
    }


    private void drawLine(PdfDocument pdfDocument, float startX, float startY, float endX, float endY) {
        PdfCanvas canvas = new PdfCanvas(pdfDocument.getPage(1));

        canvas.moveTo(startX, startY).lineTo(endX, endY).closePathFillStroke();
    }

    private Table addRowToTable(Table table, OrderDTO orderDTO) {

        table.addCell(new Cell().add(new Paragraph(orderDTO.getDate())));
        table.addCell(new Cell().add(new Paragraph(orderDTO.getGoodsCategory())));
        table.addCell(new Cell().add(new Paragraph(orderDTO.getDestination())));
        table.addCell(new Cell().add(new Paragraph(Integer.toString(orderDTO.getNoOfUnits()))));
        table.addCell(new Cell().add(new Paragraph(Double.toString(orderDTO.getUnitPrice()))));
        table.addCell(new Cell().add(new Paragraph(Double.toString(orderDTO.getTotalPrice()))));

        return table;

    }

    private void addFooters(OrderSummaryDTO summaryDTO, Table table) {

        PdfFont boldFont = null;
        try {
            boldFont = PdfFontFactory.createFont(FontConstants.HELVETICA_BOLD);
        } catch (IOException e) {
            e.printStackTrace();
        }

        table.addFooterCell(new Cell(1, 5).add(new Paragraph("TOTALT exkl. moms:")).setFont(boldFont));
        table.addFooterCell(new Cell(1, 1).add(new Paragraph(Double.toString(summaryDTO.getMonthlyDueAmount()))).setFont(boldFont));
        table.addFooterCell(new Cell(1, 5).add(new Paragraph("TOTALT inkl. 25% moms:")).setFont(boldFont));
        table.addFooterCell(new Cell(1, 1).add(new Paragraph(Double.toString(summaryDTO.getMonthlyDueAmount() * 1.25))).setFont(boldFont));

    }

    private String buildAbsoluteFilePath(File directory, OrderSummaryDTO summaryDTO) {

        LocalDate endingDate = summaryDTO.getEndingDate();

        String sweMonth = SweMonthConverter.convert(endingDate.getMonth()).getName();
        String year = Integer.toString(endingDate.getYear());

        String fileName = "Underlag " + summaryDTO.getInvoiceReciver() + " " + sweMonth + " " + year;

        return directory.getAbsolutePath() + "/" + fileName + ".pdf";
    }
}
