package com.peter.model.business.mail;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.nio.file.Paths;
import java.util.Observable;
import java.util.Properties;

/**
 * Created by andreajacobsson on 2016-09-27.
 */
class Mailer extends Observable {

    private final String SENDER = "godsterminalen.ktab@gmail.com";

    boolean mail(String email, File file) {


        final String username = "godsterminalen.ktab@gmail.com";
        final String password = "Karingotrafiken";
        final String smtpHost = "smtp.gmail.com";

        Properties props = new Properties();

        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", smtpHost);
        props.put("mail.smtp.port", "587");


        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(SENDER));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(email));
            message.setSubject("Sammanställing av fakturaunderlag");

            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText("Fakturaunderlag");

            Multipart multipart = new MimeMultipart();

            multipart.addBodyPart(messageBodyPart);

            messageBodyPart = new MimeBodyPart();
            DataSource dataSource = new FileDataSource(file);
            messageBodyPart.setDataHandler(new DataHandler(dataSource));
            messageBodyPart.setFileName(Paths.get(file.getAbsolutePath()).getFileName().toString());
            multipart.addBodyPart(messageBodyPart);

            message.setContent(multipart);

            Transport.send(message);


        } catch (MessagingException e) {
            e.printStackTrace();
        }

        return true;
    }
}
