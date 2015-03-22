package com.fredde.savingsgoallist.http;

/**
 * Simple Rest like api.
 */
public class QaptialApi {

    static final String BASE_URL = "http://qapital-ios-testtask.herokuapp.com/";
    static final String SAVINGS = "/savingsgoals";
    static final String USERS = "users/";
    static final String FEED = "/feed";


    /**
     * Returns the savings URL.
     *
     * @return The URL.
     */
    public static String getSavingsURL() {
        return BASE_URL + SAVINGS;
    }

    /**
     * Returns the user URL for the given user id.
     *
     * @param id The id to get user URL for.
     * @return The URL.
     */
    public static String getUserUrl(int id) {
        return BASE_URL + USERS + id;
    }

    /**
     * Returns the feed URL for the given id.
     *
     * @param id The id for the feed url.
     * @return The url.
     */
    public static String getFeedUrl(int id) {
        return BASE_URL + SAVINGS + "/" + id + FEED;
    }
}
