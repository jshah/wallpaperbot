package com.jshah.wallpaperbot;

import com.jshah.wallpaperbot.resources.AppResources;
import com.jshah.wallpaperbot.resources.ConfigHandler;
import com.jshah.wallpaperbot.types.ImageHandler;
import com.jshah.wallpaperbot.types.ImageRequest;
import com.jshah.wallpaperbot.types.ImgurRequest;
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

    /*
     * Find top wallpapers of the week in /r/wallpapers and download image for post > 1000 score
     */
    private void wallpapersPaginator(RedditClient reddit) {
        SubredditPaginator paginator = new SubredditPaginator(reddit);
        paginator.setLimit(25);
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
        }
    }

    private RedditClient authenticateReddit() {
        // read from config.properties
        ConfigHandler configHandler = new ConfigHandler();
        Properties properties = configHandler.loadProperties();
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
            configHandler.closeProperties();
        }
        return reddit;
    }
}
