package com.peter.startup;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by andreajacobsson on 2016-08-24.
 */
public class Playground {



    public static final String SRC = "/Users/andreajacobsson/Dropbox/Javaprojekt/KTABGodshantering/src/main/resources/pdf/specTemplate.pdf";
    public static final String DEST = "/Users/andreajacobsson/Dropbox/Javaprojekt/KTABGodshantering/src/main/resources/pdf/spec.pdf";


    public static void main(String[] args) throws DocumentException, IOException, SQLException {

        File file = new File(DEST);
        new Playground().manipulatePdf(SRC, DEST);

    }


    public void manipulatePdf(String src, String dest) throws IOException, DocumentException {
        PdfReader reader = new PdfReader(src);
        PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(dest));
        PdfContentByte cb = stamper.getOverContent(1);
        ColumnText ct = new ColumnText(cb);
        ct.setSimpleColumn(120f, 48f, 200f, 600f);
        Font f = new Font();
        Paragraph pz = new Paragraph(new Phrase(20, "Hello World!", f));
        ct.addElement(pz);
        ct.go();
        BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA_BOLD, "Cp1252", BaseFont.EMBEDDED);
        f = new Font(bf, 13);
        ct = new ColumnText(cb);
        ct.setSimpleColumn(120f, 48f, 200f, 700f);
        pz = new Paragraph("Hello World!", f);
        ct.addElement(pz);
        ct.go();
        stamper.close();
        reader.close();
    }


//        // step 1
//        Document document = new Document(PageSize.POSTCARD, 30, 30, 30, 30);
//        // step 2
//        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("Test.pdf", false));
//        // step 3
//        document.open();
//        // step 4
//
//        // Create and add a Paragraph
//        Paragraph p = new Paragraph("Foobar Film Festival", new Font(Font.FontFamily.HELVETICA, 22));
//        p.setAlignment(Element.ALIGN_CENTER);
//        document.add(p);
//
//        // Create and add an Image
//        Image img = Image.getInstance(Playground.class.getResource("/images/dog.jpg"));
//
//        img.setAbsolutePosition((PageSize.POSTCARD.getWidth() - img.getScaledWidth()) / 2, (PageSize.POSTCARD.getHeight() - img.getScaledHeight()) / 2);
//        document.add(img);
//        // Now we go to the next page
//        document.newPage();
//        document.add(p);
//        document.add(img);
//        // Add text on top of the image
//        PdfContentByte over = writer.getDirectContent();
//        over.saveState();
//        float sinus = (float) Math.sin(Math.PI / 60);
//        float cosinus = (float) Math.cos(Math.PI / 60);
//        BaseFont bf = BaseFont.createFont();
//        over.beginText();
//        over.setTextRenderingMode(PdfContentByte.TEXT_RENDER_MODE_FILL_STROKE);
//        over.setLineWidth(1.5f);
//        over.setRGBColorStroke(0xFF, 0x00, 0x00);
//        over.setRGBColorFill(0xFF, 0xFF, 0xFF);
//        over.setFontAndSize(bf, 36);
//        over.setTextMatrix(cosinus, sinus, -sinus, cosinus, 50, 324);
//        over.showText("SOLD OUT");
//        over.endText();
//        over.restoreState();
//        // Add a rectangle under the image
//        PdfContentByte under = writer.getDirectContentUnder();
//        under.saveState();
//        under.setRGBColorFill(0xFF, 0xD7, 0x00);
//        under.rectangle(5, 5, PageSize.POSTCARD.getWidth() - 10, PageSize.POSTCARD.getHeight() - 10);
//        under.fill();
//        under.restoreState();
//        // step 5
//        document.close();


//        DataManager dataManager = new DataManager("jdbc:mysql://ktabtest.cyzgfcxn1ubh.eu-central-1.rds.amazonaws.com:3306/KTABGoodsTest",
//                "pebo0602", "PetBob82");
//
//
//        List<OrderDTO> summarList = dataManager.getOrders(LocalDate.of(2016, 8, 1), LocalDate.of(2016, 8, 31));
//        Map<String, OrderSummaryDTO> orderSummaryDTOMap = new Summarizer().summarize(summarList);
//
//
//        for (Map.Entry<String, OrderSummaryDTO> entry : orderSummaryDTOMap.entrySet()) {
//
//            Document document = new Document();
//            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(entry.getKey() + ".pdf", false));
//            document.open();
//
//            document.add(new Chunk(entry.getKey()));
//            document.add(Chunk.NEWLINE);
//
//            for (OrderDTO dto : orderSummaryDTOMap.get(entry.getKey()).getMonthlyOrders()) {
//                document.add(new Paragraph(dto.getDate() + " " + dto.getGoodsCategory() + " " + dto.getDestination() + " " + dto.getNoOfUnits() + " " +
//                        dto.getUnitPrice() + " "+  dto.getTotalPrice() + " " + dto.getComment()));
//            }
//
//            document.close();
//        }


}

