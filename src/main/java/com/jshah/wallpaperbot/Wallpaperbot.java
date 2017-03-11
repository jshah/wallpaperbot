package com.jshah.wallpaperbot;


import net.dean.jraw.RedditClient;
import net.dean.jraw.http.UserAgent;
import net.dean.jraw.http.oauth.Credentials;
import net.dean.jraw.http.oauth.OAuthData;
import net.dean.jraw.models.Listing;
import net.dean.jraw.models.Submission;
import net.dean.jraw.paginators.Sorting;
import net.dean.jraw.paginators.SubredditPaginator;
import net.dean.jraw.paginators.TimePeriod;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by jay.shah on 3/9/17.
 */

public class Wallpaperbot {
    private final Properties properties = new Properties();
    private InputStream inputStream = null;

    public void run() {
        // read from config.properties
        loadProperties();
        String password = properties.getProperty("password");
        String secret = properties.getProperty("secret");

        UserAgent userAgent = UserAgent.of(AppResources.platform, AppResources.clientID, AppResources.version, AppResources.username);
        RedditClient reddit = new RedditClient(userAgent);
        Credentials credentials = Credentials.script(AppResources.username, password, AppResources.clientID, secret);
        authenticate(reddit, credentials);

        // find top wallpapers
        wallpapersPaginator(reddit);
    }

    private void wallpapersPaginator(RedditClient reddit) {
        SubredditPaginator paginator = new SubredditPaginator(reddit);
        paginator.setLimit(10);
        paginator.setSorting(Sorting.TOP);
        paginator.setTimePeriod(TimePeriod.WEEK);
        paginator.setSubreddit("wallpapers");

        Listing<Submission> listing = paginator.next(true);
        while(!listing.isEmpty()) {
            String title = listing.get(0).getTitle();
            String url = listing.get(0).getUrl();
            System.out.println(title + " : " + url);
            listing = paginator.next(true);
        }

    }

    private void authenticate(RedditClient reddit, Credentials credentials) {
        try {
            OAuthData oAuthData = reddit.getOAuthHelper().easyAuth(credentials);
            reddit.authenticate(oAuthData);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
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
