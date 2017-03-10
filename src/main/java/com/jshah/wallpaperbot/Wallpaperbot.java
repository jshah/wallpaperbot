package com.jshah.wallpaperbot;


import net.dean.jraw.RedditClient;
import net.dean.jraw.http.UserAgent;
import net.dean.jraw.http.oauth.Credentials;
import net.dean.jraw.http.oauth.OAuthData;
import net.dean.jraw.http.oauth.OAuthHelper;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.UUID;

/**
 * Created by jay.shah on 3/9/17.
 */

public class Wallpaperbot {
    private final Properties properties = new Properties();
    private InputStream inputStream = null;

    public void run() {
        loadProperties();

        String password = properties.getProperty("password");
        String secret = properties.getProperty("secret");
        UUID uuid = UUID.randomUUID();

        UserAgent userAgent = UserAgent.of(AppResources.platform, AppResources.clientID, AppResources.version, AppResources.username);
        RedditClient reddit = new RedditClient(userAgent);
        Credentials credentials = Credentials.script(AppResources.username, password, AppResources.clientID, secret);

        OAuthData oAuthData = easyAuth(reddit.getOAuthHelper(), credentials);
        if (oAuthData != null) {
            try {
                reddit.authenticate(oAuthData);
                reddit.me();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        else {
            System.out.println("Authentication Failed");
        }
    }

    private OAuthData easyAuth(OAuthHelper oAuthHelper , Credentials credentials) {
        try {
            return oAuthHelper.easyAuth(credentials);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void loadProperties() {
        try {
            inputStream = new FileInputStream(AppResources.config);
            properties.load(inputStream);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
