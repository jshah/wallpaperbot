# Wallpaperbot

This bot will run every day and download the url of any post which has over 1000 karma on /r/wallpapers

# General Info

This is a project I'm doing for fun since I like collecting wallpapers. It's still under construction and I will be working it as I have time.

# Usage

1) Clone this repo
2) Make a Script App on Reddit by following this: https://github.com/reddit/reddit/wiki/OAuth2
3) Replace redditID, redditSecret & redditPassword in config.properties.example with your app id, app secret, and your reddit user's password
4) Rename config.properties.example to config.properties
5) In the root folder of the project, run `gradle fatJar`
6) Then run, `java -jar build/libs/wallpaperbot-all-1.0.jar`
7) Wallpapers should be downloaded into a folder called files in the root folder
