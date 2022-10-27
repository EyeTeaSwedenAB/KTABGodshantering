package com.peter.model.business.mail;

import com.peter.exceptions.WrongFilenameFormatException;
import com.peter.model.Constants;
import com.peter.model.data.InvoiceReciever;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by andreajacobsson on 2016-09-23.
 */
class MailAddressResolver {


    Map<String, File> resolve(File[] pdfFiles, Map<String, InvoiceReciever> invoiceRecieversMap) throws WrongFilenameFormatException {

        Map<String, File> emailToFilesMap = new HashMap<>();


        for (File file : pdfFiles) {

            if (file.getAbsolutePath().endsWith(".pdf")) {

                String invoiceReciverName = resolveInvoiceReciverName(file);

                if (invoiceRecieversMap.containsKey(invoiceReciverName)) {

                    InvoiceReciever reciever = invoiceRecieversMap.get(invoiceReciverName);


                    if (reciever.hasRegisteredMail()) {
                        String email = reciever.getEmail();
                        emailToFilesMap.put(email, file);
                    }
                }
            }
        }

        return emailToFilesMap;
    }


    String resolveInvoiceReciverName(File file) throws WrongFilenameFormatException {

        Path filePath = Paths.get(file.getAbsolutePath());

        String fileName = filePath.getFileName().toString();

        try {
            return extractInvoiceReciver(fileName);

        } catch (WrongFilenameFormatException e) {
            e.printStackTrace();
            e.setWrongFile(file);
            throw e;
        }
    }


    String extractInvoiceReciver(String filename) throws WrongFilenameFormatException {

        String regex = "Januari | Februari | Mars | April | Maj | Juni | Juli | Augusti | September | Oktober | November | December";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(filename);

        if (!matcher.find() || !filename.contains(Constants.PDF_FILENAME_START))
            throw new WrongFilenameFormatException("Fileformat must be of type 'Underlag [Invoicereciver] [Month] [Year]', recived " + filename);

        else {
            int matchIdx = matcher.start();
            filename = filename.substring(0, matchIdx);
            filename = filename.replace(Constants.PDF_FILENAME_START + " ", "");
            filename = filename.trim();
            return filename;

        }
    }


}
