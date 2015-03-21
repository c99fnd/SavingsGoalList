package com.fredde.savingsgoallist.data;

import android.os.AsyncTask;

import com.fredde.savingsgoallist.http.JSonFetcher;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Responsible for fetching a JSON object representing feed item and converting them into
 * {@link FeedItem}s
 */
public class FeedItemLoaderTask extends AsyncTask<String, Void, Integer> {

    // JSON Node names
    private static final String FEED = "feed";
    private static final String ID = "id";
    private static final String TYPE = "type";
    private static final String TIMESTAMP = "timestamp";
    private static final String MESSAGE = "message";
    private static final String AMOUNT = "amount";
    private static final String USER_ID = "userId";

    /**
     * List where the items will be stored.
     */
    private final List<FeedItem> mList = new ArrayList<>();

    /**
     * Listener to call when loading is done.
     */
    private final LoadListener mListener;


    /**
     * Listener interface.
     */
    public interface LoadListener {
        /**
         * Called when feed items are loaded into the data list.
         *
         * @param data The loaded data.
         */
        void onLoadFinished(List<FeedItem> data);
    }

    /**
     * Constructor.
     *
     * @param listener Listener to notify when loading is done.
     */
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
                            .setAmount(g.optDouble(AMOUNT, 0))
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