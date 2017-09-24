package com.jshah;

import com.jshah.db.MongoDB;
import com.jshah.wallpaperbot.Wallpaperbot;

/**
 * Created by jay.shah on 9/23/17.
 */
public class ServerRunner {
    public static void main(String[] args) {
        MongoDB db = new MongoDB();
        Wallpaperbot wallpaperbot = new Wallpaperbot();
        wallpaperbot.run();
    }
}
