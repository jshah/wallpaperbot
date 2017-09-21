package com.jshah.web;

import org.eclipse.jetty.server.Server;

/**
 * Created by jay.shah on 9/18/17.
 */

public class JettyWebServer {
    public void setupWebServer() throws Exception {
        Server server = new Server(8080);
        server.setHandler(new HelloHandler());

        server.start();
        server.join();
    }
}
