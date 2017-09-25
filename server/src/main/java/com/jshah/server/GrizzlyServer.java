package com.jshah.server;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.net.URI;

/**
 * Created by jay.shah on 9/24/17.
 */

public class GrizzlyServer {
    private static String baseUri = "http://localhost:4444/rest/";

    public static HttpServer createGrizzlyServer() {
        final ResourceConfig rc = new ResourceConfig().packages("com.jshah.server.resources");
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(baseUri), rc);
    }
}
