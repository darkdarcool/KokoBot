package com.darkdarcool.kokobot.utils;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class KeepAlive {

    public static void start()  {
        try {
            String env = System.getenv("PORT");
            if (env == null) env = "5000";
            HttpServer server = HttpServer.create(new InetSocketAddress(Integer.parseInt(env)), 0);
            server.createContext("/", new Handler());
            server.setExecutor(null); // creates a default executor
            server.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static class Handler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            String response = "Don't ever go here";
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

}