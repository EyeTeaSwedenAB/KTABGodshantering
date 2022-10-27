package com.peter.model.business.mail;

import com.peter.exceptions.WrongFilenameFormatException;
import com.peter.model.data.InvoiceReciever;
import javafx.concurrent.Task;

import java.io.File;
import java.util.Map;
import java.util.Observable;

/**
 * Created by andreajacobsson on 2016-09-22.
 */

public class MailManager extends Observable {

    private MailAddressResolver mailAddressResolver;
    private Mailer mailer;

    public MailManager() {
        mailAddressResolver = new MailAddressResolver();
        mailer = new Mailer();
    }

    public boolean sendMultipleMails(final File[] files, final Map<String, InvoiceReciever> invoiceRecieverMap) throws WrongFilenameFormatException {

        Task<Void> task = new Task<Void>() {

            @Override
            protected Void call() throws Exception {

                Map<String, File> emailToFileMap = mailAddressResolver.resolve(files, invoiceRecieverMap);

                for (String emailAdress : emailToFileMap.keySet()) {
                    sendSingleMail(emailAdress, emailToFileMap.get(emailAdress));

                }

                return null;
            }

        };


        task.setOnSucceeded(event -> {
            this.setChanged();
            this.notifyObservers(new MailStateEvent(false));
        });

        Thread thread = new Thread(task);
        thread.start();
        this.setChanged();
        this.notifyObservers(new MailStateEvent(true));

        return true;
    }

    public boolean sendSingleEmail(InvoiceReciever invoiceReciever, File file) {
        if (invoiceReciever.hasRegisteredMail()) {
            mailer.mail(invoiceReciever.getEmail(), file);
            return true;
        }
        return false;
    }

    public boolean sendSingleMail(String email, File file) {
        return mailer.mail(email, file);

    }


}
