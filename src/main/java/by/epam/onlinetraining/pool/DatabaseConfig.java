package by.epam.onlinetraining.pool;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class DatabaseConfig {
    private final static Logger LOGGER = LogManager.getLogger(DatabaseConfig.class);

    static final String RESOURCE_BUNDLE_PATH = "db_config";
    static final String USER = "db_user";
    static final String PASSWORD = "db_password";
    static final String POOL_SIZE = "db_pool_size";
    static final String CONNECTION = "db_connection";

    static ResourceBundle resourceBundle;
    static String user;
    static String password;
    static String url;
    static int poolSize;

    static {
        try {
            resourceBundle = ResourceBundle.getBundle(RESOURCE_BUNDLE_PATH);
            user = resourceBundle.getString(USER);
            password = resourceBundle.getString(PASSWORD);
            url = resourceBundle.getString(CONNECTION);
            String poolSizeValue = resourceBundle.getString(POOL_SIZE);
            poolSize = Integer.parseInt(poolSizeValue);
        } catch (MissingResourceException e) {
            LOGGER.log(Level.FATAL, "Exception during reading resourceBundle file", e);
            throw new RuntimeException("Exception during reading resourceBundle file", e);
        }
    }
}
