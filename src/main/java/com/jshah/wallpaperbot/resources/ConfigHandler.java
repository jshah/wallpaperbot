package com.jshah.wallpaperbot.resources;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by jay.shah on 3/15/17.
 */

public class ConfigHandler {
    private final Properties properties = new Properties();
    private InputStream inputStream;

    public Properties loadProperties() {
        try {
            inputStream = new FileInputStream(AppResources.config);
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }

    public void closeProperties() {
        try {
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
