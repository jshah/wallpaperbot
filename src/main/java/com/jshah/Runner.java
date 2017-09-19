package com.jshah;

import com.jshah.wallpaperbot.Wallpaperbot;
import com.jshah.web.JettyWebServer;

/**
 * Created by jay.shah on 3/8/17.
 */

public class Runner {
    public static void main(String[] args) {
        Wallpaperbot wallpaperbot = new Wallpaperbot();
        JettyWebServer webServer = new JettyWebServer();
        try {
            webServer.setupWebServer();
        } catch (Exception e) {
            System.out.println("Server failed to start");
            e.printStackTrace();
        }
        wallpaperbot.run();
    }
}
