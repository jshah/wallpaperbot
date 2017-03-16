package com.jshah.wallpaperbot.types;

import com.jshah.wallpaperbot.resources.ConfigHandler;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by jay.shah on 3/12/17.
 */

public class ImgurRequest extends Request implements ImageHandler {
    private String apiUrl = "https://api.imgur.com/oauth2/authorize";

    private void authenticate() {
        ConfigHandler configHandler = new ConfigHandler();
        Properties properties = configHandler.loadProperties();
        String clientID = properties.getProperty("imgurID");
        String secret = properties.getProperty("imgurSecret");
        try {
            // TODO: need to implement oauth authorization
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setupDownload(String url) {
//        authenticate();
    }

    public void executeDownload() {
//        downloadFile(this.url);
    };
}
