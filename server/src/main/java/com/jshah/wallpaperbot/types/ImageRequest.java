package main.java.com.jshah.wallpaperbot.types;

/**
 * Created by jay.shah on 3/13/17.
 */

public class ImageRequest extends Request implements ImageHandler {
    public void setupDownload(String url) {
        this.url = url;
    }
    public void executeDownload() {
        downloadFile(this.url);
    }
}
