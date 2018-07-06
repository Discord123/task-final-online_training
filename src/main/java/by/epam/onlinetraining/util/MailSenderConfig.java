package by.epam.onlinetraining.util;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class MailSenderConfig {
    private static final  Logger LOGGER = LogManager.getLogger(MailSenderConfig.class);

    static final String RESOURCE_BUNDLE_NAME = "mail";
    static final String EMAIL = "mail.username";
    static final String PASSWORD = "mail.password";
    static final String AUTH = "mail.smtp.auth";
    static final String START_TLS = "mail.smtp.starttls.enable";
    static final String HOST = "mail.smtp.host";
    static final String PORT = "mail.smtp.port";

    static ResourceBundle resourceBundle;
    static String email;
    static String password;
    static String auth;
    static String startTls;
    static String host;
    static String port;

    static {
        try {
            resourceBundle = ResourceBundle.getBundle(RESOURCE_BUNDLE_NAME);
            email = resourceBundle.getString(EMAIL);
            password = resourceBundle.getString(PASSWORD);
            auth = resourceBundle.getString(AUTH);
            startTls = resourceBundle.getString(START_TLS);
            host = resourceBundle.getString(HOST);
            port = resourceBundle.getString(PORT);
        } catch (MissingResourceException e) {
            LOGGER.log(Level.FATAL, "Exception during reading resourceBundle.", e);
            throw new RuntimeException("Exception during reading resourceBundle.", e);
        }
    }
}
