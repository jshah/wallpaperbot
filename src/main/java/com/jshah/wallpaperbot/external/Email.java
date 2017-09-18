package com.jshah.wallpaperbot.external;

import com.jshah.wallpaperbot.resources.ConfigHandler;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;

/**
 * Created by jay.shah on 9/17/17.
 */

public class Email {
    final private String username;
    final private String password;

    public Email() {
        ConfigHandler configHandler = new ConfigHandler();
        Properties properties = configHandler.loadProperties();

        this.username = properties.getProperty("gmailUsername");
        this.password = properties.getProperty("gmailPassword");
        System.out.println(username);
        System.out.println(password);

        configHandler.closeProperties();
    }

    public void sendMail(String filename) {
        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {
            Transport transport = session.getTransport();
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(username));
            message.setSubject("Top Wallpapers From /r/wallpapers Zipped");
            message.setText("PFA");

            MimeBodyPart messageBodyPart = new MimeBodyPart();

            Multipart multipart = new MimeMultipart();

            messageBodyPart = new MimeBodyPart();
            DataSource source = new FileDataSource(filename);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(filename);
            multipart.addBodyPart(messageBodyPart);

            message.setContent(multipart);

            System.out.println("Sending");

            transport.connect();
            Transport.send(message);
            transport.close();

            System.out.println("Done");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

