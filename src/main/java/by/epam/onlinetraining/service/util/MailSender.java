package by.epam.onlinetraining.service.util;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.mail.*;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class MailSender {
    private static final Logger LOGGER = LogManager.getLogger(MailSender.class);
    private String username;
    private String password;
    private Properties properties;

    public MailSender() {
        this.username = MailSenderConfig.email;
        this.password = MailSenderConfig.password;

        properties = new Properties();
        properties.put(MailSenderConfig.AUTH, MailSenderConfig.auth);
        properties.put(MailSenderConfig.START_TLS, MailSenderConfig.startTls);
        properties.put(MailSenderConfig.HOST, MailSenderConfig.host);
        properties.put(MailSenderConfig.PORT, MailSenderConfig.port);
    }

    public void send(String subject, String text, String recipientEmail) {
        Authenticator authenticator = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        };
        Session session = Session.getInstance(properties, authenticator);

        try {
            Message message = new MimeMessage(session);
            InternetAddress senderAddress = new InternetAddress(username);
            message.setFrom(senderAddress);
            InternetAddress[] internetAddress = InternetAddress.parse(recipientEmail);
            message.setRecipients(Message.RecipientType.TO, internetAddress);
            message.setSubject(subject);
            message.setText(text);

            Transport.send(message);
        } catch (MessagingException e) {
            LOGGER.log(Level.FATAL, "Exception during sending email.", e);
        }
    }
}
