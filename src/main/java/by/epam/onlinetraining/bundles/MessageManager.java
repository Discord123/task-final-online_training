package by.epam.onlinetraining.bundles;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public enum MessageManager {
    EN(ResourceBundle.getBundle("localedata", new Locale("en", "US"))),
    RU( ResourceBundle.getBundle("localedata", new Locale("ru", "RU")));

    private static final Logger Logger = LogManager.getLogger(MessageManager.class);
    private ResourceBundle resourceBundle;

    MessageManager(ResourceBundle bundle) {
        this.resourceBundle = bundle;
    }

    public static MessageManager getManager(String locale) {
        String result = "";
        if ("ru_RU".equals(locale)) {
            result = "RU";
        } else {
            result = "EN";
        }
        return valueOf(result);
    }

    public String getMessage(String key) {
        String localizedContent = null;
        try {
            localizedContent = resourceBundle.getString(key);
        } catch (MissingResourceException e) {
            Logger.log(Level.FATAL, "Exception during reading resourceBundle", e);
            throw new RuntimeException("Exception during reading resourceBundle", e);
        }
        return localizedContent;
    }
}