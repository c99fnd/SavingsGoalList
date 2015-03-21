package com.fredde.savingsgoallist.http;

import com.fredde.savingsgoallist.utils.Utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Responsible for fetching a string from a given URL.
 */
public class JSonFetcher {

    /**
     * Time out in milliseconds.
     */
    private final static int TIMEOUT = 10000;

    /**
     * Constructor
     */
    public JSonFetcher() {
    }

    /**
     * Sets up an HTTPURLConnection and gets an input stream from the provided URL.
     *
     * @param urlString The url to fetch the JSON from.
     * @return String containing the content of the URL.
     * @throws java.io.IOException
     */
    public String fetch(String urlString) throws IOException {
        InputStream is = null;
        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(TIMEOUT);
            conn.setConnectTimeout(TIMEOUT);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);

            conn.connect();
            is = conn.getInputStream();

            return Utils.InputStreamToString(is);
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }
}