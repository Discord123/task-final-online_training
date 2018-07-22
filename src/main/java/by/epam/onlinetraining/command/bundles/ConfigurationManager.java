package by.epam.onlinetraining.command.bundles;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class ConfigurationManager {
    private static final Logger Logger = LogManager.getLogger(ConfigurationManager.class);
    private static final String CONFIG_FILE_PATH = "config";

    private static ResourceBundle resourceBundle;

    private ConfigurationManager() { }

    public static String getProperty(String key) {
        String resource = null;
        try {
            resourceBundle = ResourceBundle.getBundle(CONFIG_FILE_PATH);
            resource = resourceBundle.getString(key);
        } catch (MissingResourceException | ExceptionInInitializerError e) {
            Logger.log(Level.FATAL, "Exception during reading resourceBundle", e);
            throw new RuntimeException("Exception during reading resourceBundle", e);
        }
        return resource;
    }
}
