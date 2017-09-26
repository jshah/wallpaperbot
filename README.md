# Overview

This bot will run every week and download images of any post which has over 1000 karma on reddit.com/r/wallpapers. It will then zip all the downloaded images and then email it out to all subscribed users.

# General Info

This is a project I'm doing for fun since I like collecting wallpapers. It's still under construction and I will be working it as I have time.

The goal is for this bot to be deployed on Heroku with a web ui which will allow users to subscribe to the mailing list and specify desired resolutions.

# Dependencies

MongoDB: https://www.mongodb.com/
Jersey/Grizzly: https://jersey.github.io/
Jetty: http://www.eclipse.org/jetty/


# Usage

1) Clone this repo
2) Make a Script App on Reddit by following this: https://github.com/reddit/reddit/wiki/OAuth2
3) Replace redditID, redditSecret & redditPassword in config.properties.example with your app id, app secret, and your reddit user's password
4) Rename config.properties.example to config.properties
5) Run the bot by typing "./run"
6) Wallpapers should be downloads into a folder called files in the root folder
7) You can clean the directory by running "./clean" or stop the processes by running "./stop"

# License

MIT License

Copyright (c) [2017] [Jay Shah]

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
