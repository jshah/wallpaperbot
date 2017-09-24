package com.jshah;

import com.jshah.web.JettyWebServer;

/**
 * Created by jay.shah on 3/8/17.
 */

public class WebappRunner {
    public static void main(String[] args) {
        try {
            JettyWebServer webServer = new JettyWebServer();
            webServer.setupWebServer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
