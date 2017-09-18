package com.jshah.wallpaperbot;

import com.jshah.wallpaperbot.external.Email;
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
import org.zeroturnaround.zip.ZipUtil;

import java.io.File;
import java.util.Properties;

/**
 * Created by jay.shah on 3/9/17.
 */

public class Wallpaperbot {
    private String files = "files";
    private String images = "images.zip";

    public void run() {
        RedditClient reddit = authenticateReddit();
        Listing<Submission> page = wallpapersPaginator(reddit);
        downloadTopImages(page);
        zipImages();
        Email email = new Email();
        email.sendMail(images);
    }

    private void zipImages() {
        try {
            ZipUtil.pack(new File(files), new File(images));
            System.out.println("zipped images into 'images.zip'");
        }
        catch (Exception e){
            System.out.println("zip failed");
        }
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
     * Find top wallpapers of the week in /r/wallpapers and download image for post > 1000 score in the first page
     */
    private void downloadTopImages(Listing<Submission> page) {
        for (Submission post : page) {
            String url = post.getUrl();
            Integer score = post.getScore();
            if (score > 1000) {
                downloadUrl(url);
            }
        }
    }

    /*
     * Get first page of /r/wallpapers with 25 max posts over last week
     */
    private Listing<Submission> wallpapersPaginator(RedditClient reddit) {
        SubredditPaginator paginator = new SubredditPaginator(reddit);
        paginator.setLimit(25);
        paginator.setSorting(Sorting.TOP);
        paginator.setTimePeriod(TimePeriod.WEEK);
        paginator.setSubreddit("wallpapers");

        return paginator.next(true);
    }

    private RedditClient authenticateReddit() {
        // read from config.properties
        String username = ConfigHandler.getProperty("redditUsername");
        String password = ConfigHandler.getProperty("redditPassword");
        String secret = ConfigHandler.getProperty("redditSecret");
        String clientID = ConfigHandler.getProperty("redditClientID");

        UserAgent userAgent = UserAgent.of(AppResources.platform, clientID, AppResources.version, username);
        RedditClient reddit = new RedditClient(userAgent);
        Credentials credentials = Credentials.script(username, password, clientID, secret);

        try {
            OAuthData oAuthData = reddit.getOAuthHelper().easyAuth(credentials);
            reddit.authenticate(oAuthData);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return reddit;
    }
}
