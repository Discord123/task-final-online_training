package by.epam.onlinetraining.command.util;

import java.util.ResourceBundle;

public class PagePathManager {
    private static final String CONFIG_FILE_PATH = "config";
    private static ResourceBundle resourceBundle = ResourceBundle.getBundle(CONFIG_FILE_PATH);

    private PagePathManager() {}

    public static String getProperty(String key) {
        return resourceBundle.getString(key);
    }
}
