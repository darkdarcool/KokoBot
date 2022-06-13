package com.darkdarcool.kokobot.utils;

public class Web {
    public static String get(String url) {
        StringBuilder sb = new StringBuilder();
        try {
            java.net.URL urlObj = new java.net.URL(url);
            java.net.URLConnection conn = urlObj.openConnection();
            conn.connect();
            java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (Exception err) {
            err.printStackTrace();
        }
        return sb.toString();
    }
}
