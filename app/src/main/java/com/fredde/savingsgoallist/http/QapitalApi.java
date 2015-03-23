package com.fredde.savingsgoallist.http;

/**
 * Simple Rest like api.
 */
public class QapitalApi {

    static final String END_POINT = "http://qapital-ios-testtask.herokuapp.com/";
    static final String SAVINGS = "/savingsgoals";
    static final String USERS = "users/";
    static final String FEED = "/feed";


    /**
     * Returns the savings URL.
     *
     * @return The URL.
     */
    public static String getSavingsURL() {
        return END_POINT + SAVINGS;
    }

    /**
     * Returns the user URL for the given user id.
     *
     * @param id The id to get user URL for.
     * @return The URL.
     */
    public static String getUserUrl(int id) {
        return END_POINT + USERS + id;
    }

    /**
     * Returns the feed URL for the given id.
     *
     * @param id The id for the feed url.
     * @return The url.
     */
    public static String getFeedUrl(int id) {
        return END_POINT + SAVINGS + "/" + id + FEED;
    }
}
