package com.jshah.wallpaperbot.types;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.net.URL;

/**
 * Created by jay.shah on 3/13/17.
 */

public abstract class Request {
    protected String url;

    public void downloadFile(String url) {
        try {
            URL myUrl = new URL(url);
            String fileName = FilenameUtils.getName(myUrl.getPath());
            File file = new File("files/" + fileName);
            System.out.println("Downloading image from link: " + url);
            FileUtils.copyURLToFile(myUrl, file);
        } catch (Exception e) {
            System.out.println("Unable to download file from: " + url);
//            e.printStackTrace();
        }
    }
}
