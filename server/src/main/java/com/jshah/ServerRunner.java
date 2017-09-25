package com.jshah;

import com.jshah.db.MongoDB;
import com.jshah.server.GrizzlyServer;
import com.jshah.wallpaperbot.Wallpaperbot;
import org.glassfish.grizzly.http.server.HttpServer;

/**
 * Created by jay.shah on 9/23/17.
 */

public class ServerRunner {
    public static void main(String[] args) {
        HttpServer server = GrizzlyServer.createGrizzlyServer();
        MongoDB db = new MongoDB();
        Wallpaperbot wallpaperbot = new Wallpaperbot();
        wallpaperbot.run();
    }
}
