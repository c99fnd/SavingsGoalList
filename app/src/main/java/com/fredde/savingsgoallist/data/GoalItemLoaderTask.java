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
 * Responsible for fetching a JSON object representing saving goals and converting them into
 * {@link GoalItem}s
 */
public class GoalItemLoaderTask extends AsyncTask<String, Void, Integer> {

    /* JSON Node names. */
    private static final String GOALS = "savingsGoals";
    private static final String ID = "id";
    private static final String IMAGE_URL = "goalImageURL";
    private static final String OWNER_ID = "userId";
    private static final String TARGET_AMOUNT = "targetAmount";
    private static final String CURRENT_AMOUNT = "currentBalance";
    private static final String STATUS = "status";
    private static final String NAME = "name";
    private static final String CONNECTED_IDS = "connectedUsers";

    /**
     * List where the items will be stored.
     */
    private final List<GoalItem> mList = new ArrayList<>();

    /**
     * Listener to call when loading is done.
     */
    private final LoadListener mListener;


    /**
     * Listener inteface.
     */
    public interface LoadListener {
        /**
         * Called when saving goal items are loaded into the data list.
         *
         * @param data The loaded data.
         */
        void onLoadFinished(List<GoalItem> data);
    }

    /**
     * Constructor.
     *
     * @param listener Listener to notify when loading is done.
     */
    public GoalItemLoaderTask(LoadListener listener) {
        mListener = listener;
    }

    @Override
    protected Integer doInBackground(String... url) {
        if (isCancelled()) {
            return 0;
        }

        String jSon = null;
        try {
            jSon = new JSonFetcher().fetch(url[0]);
        } catch (IOException e) {
            e.printStackTrace();
        }


        if (jSon != null) {
            try {
                JSONObject jsonObj = new JSONObject(jSon);

                /* Get JSON Array */
                JSONArray goals = jsonObj.getJSONArray(GOALS);

                /* Loop through goals. */
                for (int i = 0; i < goals.length(); i++) {
                    JSONObject g = goals.getJSONObject(i);

                    GoalItem item = new GoalItem()
                            .setId(g.getInt(ID))
                            .setCurrentBalance(g.optDouble(CURRENT_AMOUNT, 0))
                            .setSavingsTarget(g.optDouble(TARGET_AMOUNT, 0))
                            .setTitle(g.getString(NAME))
                            .setImageUrl(g.getString(IMAGE_URL))
                            .setUserId(g.getInt(OWNER_ID))
                            .setStatus(g.getString(STATUS));

                    /* Get JSON Array for connected users. Connected users can be null. */
                    if (!g.isNull(CONNECTED_IDS)) {
                        JSONArray jIds = g.getJSONArray(CONNECTED_IDS);
                        int ids[] = new int[jIds.length()];
                        for (int j = 0; j < jIds.length(); j++) {
                            ids[j] = jIds.getInt(j);
                        }
                        item.setConnectedUserIds(ids);
                    }
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
