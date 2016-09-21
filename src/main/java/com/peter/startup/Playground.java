package com.peter.startup;

import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.color.DeviceCmyk;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.canvas.PdfCanvasConstants;
import com.itextpdf.layout.ColumnDocumentRenderer;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.layout.LayoutContext;
import com.itextpdf.layout.layout.LayoutResult;
import com.itextpdf.layout.renderer.AbstractRenderer;
import com.itextpdf.layout.renderer.BlockRenderer;
import com.itextpdf.layout.renderer.CellRenderer;
import com.itextpdf.layout.renderer.IRenderer;

import java.io.IOException;

/**
 * Created by andreajacobsson on 2016-08-24.
 */

public class Playground {


    public static final String SRC = "/Users/andreajacobsson/Dropbox/Javaprojekt/KTABGodshantering/src/main/resources/pdf/specTemplate.pdf";
    public static final String DEST = "/Users/andreajacobsson/Dropbox/Javaprojekt/KTABGodshantering/src/main/resources/pdf/spec.pdf";
    public static final String IMAGE = "/Users/andreajacobsson/Dropbox/Javaprojekt/KTABGodshantering/src/main/resources/images/karingoLogo.png";

    public static void main(String args[]) throws IOException {

        new Playground().createPDF();
    }

    public void createPDF() throws IOException {

        PdfWriter pdfWriter = new PdfWriter(DEST);
        PdfDocument pdfDocument = new PdfDocument(pdfWriter);

        PageSize ps = PageSize.A4.rotate();

        PdfPage pdfPage = pdfDocument.addNewPage(ps);
        PdfCanvas canvas = new PdfCanvas(pdfPage);

        canvas.concatMatrix(1, 0, 0, 1, ps.getWidth() / 2, ps.getHeight() / 2);

        //Draw X axis
        canvas.moveTo(-(ps.getWidth() / 2 - 15), 0)
                .lineTo(ps.getWidth() / 2 - 15, 0)
                .stroke();
        //Draw X axis arrow
        canvas.setLineJoinStyle(PdfCanvasConstants.LineJoinStyle.ROUND)
                .moveTo(ps.getWidth() / 2 - 25, -10)
                .lineTo(ps.getWidth() / 2 - 15, 0)
                .lineTo(ps.getWidth() / 2 - 25, 10).stroke()
                .setLineJoinStyle(PdfCanvasConstants.LineJoinStyle.MITER);
        //Draw Y axis
        canvas.moveTo(0, -(ps.getHeight() / 2 - 15))
                .lineTo(0, ps.getHeight() / 2 - 15)
                .stroke();
        //Draw Y axis arrow
        canvas.saveState()
                .setLineJoinStyle(PdfCanvasConstants.LineJoinStyle.ROUND)
                .moveTo(-10, ps.getHeight() / 2 - 25)
                .lineTo(0, ps.getHeight() / 2 - 15)
                .lineTo(10, ps.getHeight() / 2 - 25).stroke()
                .restoreState();
        //Draw X serif
        for (int i = -((int) ps.getWidth() / 2 - 61);
             i < ((int) ps.getWidth() / 2 - 60); i += 40) {
            canvas.moveTo(i, 5).lineTo(i, -5);
        }
        //Draw Y serif
        for (int j = -((int) ps.getHeight() / 2 - 57);
             j < ((int) ps.getHeight() / 2 - 56); j += 40) {
            canvas.moveTo(5, j).lineTo(-5, j);
        }

        Color grayColor = new DeviceCmyk(0.f, 0.f, 0.f, 0.875f);
        Color greenColor = new DeviceCmyk(1.f, 0.f, 1.f, 0.176f);
        Color blueColor = new DeviceCmyk(1.f, 0.156f, 0.f, 0.118f);

        canvas.setLineWidth(0.5f).setStrokeColor(blueColor);

        for (int i = -((int) ps.getHeight() / 2 - 57);
             i < ((int) ps.getHeight() / 2 - 56); i += 40) {
            canvas.moveTo(-(ps.getWidth() / 2 - 15), i)
                    .lineTo(ps.getWidth() / 2 - 15, i);
        }
        for (int j = -((int) ps.getWidth() / 2 - 61);
             j < ((int) ps.getWidth() / 2 - 60); j += 40) {
            canvas.moveTo(j, -(ps.getHeight() / 2 - 15))
                    .lineTo(j, ps.getHeight() / 2 - 15);
        }

        canvas.stroke();
        pdfDocument.close();



    }
}

