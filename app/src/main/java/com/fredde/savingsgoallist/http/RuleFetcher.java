package com.fredde.savingsgoallist.http;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Retrieves a url string from a user id.
 */
public class RuleFetcher {

    private static final String AVATAR_URL = "avatarUrl";

    /**
     * Constructor
     */
    public RuleFetcher() {
    }

    /**
     * Gets url for avatar image from the user with given id.
     *
     * @param id The id of the user.
     * @return The URL for the avatar image.
     * @throws org.json.JSONException
     */
    public String fetch(int id) throws JSONException {
        String jSon = null;
        try {
            jSon = new JSonFetcher().fetch(QapitalApi.getUserUrl(id));
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (jSon != null) {
            JSONObject jsonObj = new JSONObject(jSon);
            return jsonObj.getString(AVATAR_URL);
        }
        return null;
    }
}
