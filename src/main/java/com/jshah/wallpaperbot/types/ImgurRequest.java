package com.jshah.wallpaperbot.types;

/**
 * Created by jay.shah on 3/12/17.
 */

public class ImgurRequest extends Request implements ImageHandler {
    public void setupDownload(String url) {
        this.url = url + ".jpg";
    }

    public void executeDownload() {
        downloadFile(this.url);
    };
}
