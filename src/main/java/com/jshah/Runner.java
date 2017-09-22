package com.jshah;

import com.jshah.db.MongoDB;
import com.jshah.wallpaperbot.Wallpaperbot;
import com.jshah.web.JettyWebServer;

import java.util.Arrays;

/**
 * Created by jay.shah on 3/8/17.
 */

public class Runner {
    public static void main(String[] args) {
        try {
            JettyWebServer webServer = new JettyWebServer();
            webServer.setupWebServer();
        } catch (Exception e) {
            e.printStackTrace();
        }

//        MongoDB db = new MongoDB();
        Wallpaperbot wallpaperbot = new Wallpaperbot();
        wallpaperbot.run();
    }
}
