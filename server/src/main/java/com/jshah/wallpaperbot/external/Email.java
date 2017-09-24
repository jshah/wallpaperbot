package main.java.com.jshah.wallpaperbot.external;

import main.java.com.jshah.wallpaperbot.resources.ConfigHandler;

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
    private final String username;
    private final String password;

    public Email() {
        this.username = ConfigHandler.getProperty("gmailUsername");
        this.password = ConfigHandler.getProperty("gmailPassword");
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
            message.setSubject("Top Wallpapers From /r/wallpapers");

            Multipart multipart = new MimeMultipart();

            MimeBodyPart messageBody = new MimeBodyPart();
            messageBody.setText("This email contains the top wallpapers from /r/wallpapers over the last week that have a post score of 1000 or above.");

            MimeBodyPart messageFilePart = new MimeBodyPart();
            DataSource source = new FileDataSource(filename);
            messageFilePart.setDataHandler(new DataHandler(source));
            messageFilePart.setFileName(filename);

            multipart.addBodyPart(messageBody);
            multipart.addBodyPart(messageFilePart);

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

