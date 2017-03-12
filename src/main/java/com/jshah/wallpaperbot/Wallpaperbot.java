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
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
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
        String clientID = properties.getProperty("clientid");

        UserAgent userAgent = UserAgent.of(AppResources.platform, clientID, AppResources.version, AppResources.username);
        RedditClient reddit = new RedditClient(userAgent);
        Credentials credentials = Credentials.script(AppResources.username, password, clientID, secret);
        authenticate(reddit, credentials);
        // find top wallpapers of the week in /r/wallpapers and download image for post > 1000 score
        wallpapersPaginator(reddit);
    }

    private void downloadUrl(String url) {
        try {
            URL myUrl = new URL(url);
            String fileName = FilenameUtils.getName(myUrl.getPath());
            File file = new File("files/" + fileName);
            FileUtils.copyURLToFile(myUrl, file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sleep() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    private void wallpapersPaginator(RedditClient reddit) {
        SubredditPaginator paginator = new SubredditPaginator(reddit);
        paginator.setLimit(10);
        paginator.setSorting(Sorting.TOP);
        paginator.setTimePeriod(TimePeriod.WEEK);
        paginator.setSubreddit("wallpapers");

        Listing<Submission> listing = paginator.next(true);
        for (Submission post : listing) {
            String url = post.getUrl();
            Integer score = post.getScore();
            if (score > 1000) {
                downloadUrl(url);
            }
            sleep();
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
