package com.fredde.savingsgoallist.data;

import android.os.AsyncTask;

import com.fredde.savingsgoallist.http.JSonFetcher;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FeedItemLoaderTask extends AsyncTask<String, Void, Integer> {

    // JSON Node names
    private static final String FEED = "feed";
    private static final String ID = "id";
    private static final String TYPE = "type";
    private static final String TIMESTAMP = "timestamp";
    private static final String MESSAGE = "message";
    private static final String AMOUNT = "amount";
    private static final String USER_ID = "userId";


    private static final String GOAL_STATUS = "status";
    private static final String GOAL_NAME = "name";
    private static final String GOAL_CONNECTED_USERS_LIST = "connectedUsers";

    private final List<FeedItem> mList = new ArrayList<>();

    private final LoadListener mListener;


    public interface LoadListener {
        void onLoadFinished(List<FeedItem> data);
    }

    public FeedItemLoaderTask(LoadListener listener) {
        mListener = listener;
    }

    @Override
    protected Integer doInBackground(String... urls) {
        if (isCancelled()) {
            return 0;
        }

        String jSon = null;
        try {
            jSon = new JSonFetcher().fetch(urls[0]);
        } catch (IOException e) {
            e.printStackTrace();
        }


        if (jSon != null) {
            try {
                JSONObject jsonObj = new JSONObject(jSon);

                /* Get JSON Array */
                JSONArray feedItems = jsonObj.getJSONArray(FEED);

                /* Loop through goals. */
                for (int i = 0; i < feedItems.length(); i++) {
                    JSONObject g = feedItems.getJSONObject(i);

                    FeedItem item = new FeedItem()
                            .setId(g.getString(ID))
                            .setType(g.getString(TYPE))
                            .setTimeStamp(g.getString(TIMESTAMP))
                            .setMessage(g.getString(MESSAGE))
                            .setAmount(g.optDouble(USER_ID, 0))
                            .setUserId(g.getInt(USER_ID));
                    mList.add(item);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return mList.size();
    }

    @Override
    protected void onPostExecute(Integer loadedItems) {
        mListener.onLoadFinished(mList);
    }
}