package by.epam.onlinetraining.service.util;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class MailSenderConfig {
    private static final  Logger LOGGER = LogManager.getLogger(MailSenderConfig.class);

    protected static final String RESOURCE_BUNDLE_NAME = "mail";
    protected static final String EMAIL = "mail.username";
    protected static final String PASSWORD = "mail.password";
    protected static final String AUTH = "mail.smtp.auth";
    protected static final String START_TLS = "mail.smtp.starttls.enable";
    protected static final String HOST = "mail.smtp.host";
    protected static final String PORT = "mail.smtp.port";

    protected static ResourceBundle resourceBundle;
    protected static String email;
    protected static String password;
    protected static String auth;
    protected static String startTls;
    protected static String host;
    protected static String port;

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
