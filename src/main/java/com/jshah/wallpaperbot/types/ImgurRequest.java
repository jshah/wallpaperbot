package com.jshah.wallpaperbot.types;

import com.jshah.wallpaperbot.resources.ConfigHandler;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClientBuilder;

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
        HttpClient client = HttpClientBuilder.create().build();
        try {
            URIBuilder builder = new URIBuilder(apiUrl);
            builder.addParameter("response_type", "code");
            builder.addParameter("client_id", clientID);
            HttpGet request = new HttpGet(builder.build());
            HttpResponse response = client.execute(request);

            System.out.println(response.getEntity());
            System.out.println(response.getStatusLine());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setupDownload(String url) {
        authenticate();
    }

    public void executeDownload() {
//        downloadFile(this.url);
    };
}
