package main.java.com.jshah.wallpaperbot.resources;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by jay.shah on 3/15/17.
 */

public class ConfigHandler {
    private static Properties properties = new Properties();

    static {
        try {
            InputStream inputStream = new FileInputStream(AppResources.config);
            properties.load(inputStream);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
}
