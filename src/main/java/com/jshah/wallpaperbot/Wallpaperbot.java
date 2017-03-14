package com.jshah.wallpaperbot;

import com.jshah.wallpaperbot.types.ImageHandler;
import com.jshah.wallpaperbot.types.ImageRequest;
import com.jshah.wallpaperbot.types.ImgurRequest;
import com.jshah.wallpaperbot.types.Request;
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
    private InputStream inputStream;

    public void run() {
        RedditClient reddit = authenticateReddit();
        wallpapersPaginator(reddit);
    }

    private ImageHandler findRequestType(String url) {
        if (url.contains("imgur.com") && !url.contains("i.imgur.com")) {
            return new ImgurRequest();
        }
        else {
            return new ImageRequest();
        }
    }

    private void downloadUrl(String url) {
        ImageHandler imageHandler = findRequestType(url);
        imageHandler.setupDownload(url);
        imageHandler.executeDownload();
    }

    private void sleep() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /*
     * Find top wallpapers of the week in /r/wallpapers and download image for post > 1000 score
     */
    private void wallpapersPaginator(RedditClient reddit) {
        SubredditPaginator paginator = new SubredditPaginator(reddit);
        paginator.setLimit(25);
        paginator.setSorting(Sorting.TOP);
        paginator.setTimePeriod(TimePeriod.WEEK);
        paginator.setSubreddit("wallpapers");

        // paginator.next() flips through each page, need to handle logic for multiple pages and see what happens
        // what is max limit per page?
//        int i = 1;
        Listing<Submission> listing = paginator.next(true);
        for (Submission post : listing) {
            String url = post.getUrl();
            Integer score = post.getScore();
            if (score > 1000) {
                downloadUrl(url);
            }
            // reddit only allows a request every 1 minute
            sleep();
//            i++;
//            System.out.println(i + ": " + post.getTitle() + " " + score);
        }
    }

    private RedditClient authenticateReddit() {
        // read from config.properties
        loadProperties();
        String password = properties.getProperty("redditPassword");
        String secret = properties.getProperty("redditSecret");
        String clientID = properties.getProperty("redditClientID");

        UserAgent userAgent = UserAgent.of(AppResources.platform, clientID, AppResources.version, AppResources.username);
        RedditClient reddit = new RedditClient(userAgent);
        Credentials credentials = Credentials.script(AppResources.username, password, clientID, secret);

        try {
            OAuthData oAuthData = reddit.getOAuthHelper().easyAuth(credentials);
            reddit.authenticate(oAuthData);
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            closeProperties();
        }
        return reddit;
    }

    private void closeProperties() {
        try {
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadProperties() {
        try {
            inputStream = new FileInputStream(AppResources.config);
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
