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
 * Creates goal items from JSON string.
 */
public class GoalItemLoaderTask extends AsyncTask<String, Void, Integer> {

    // JSON Node names
    private static final String GOALS = "savingsGoals";
    private static final String GOAL_ID = "id";
    private static final String GOAL_IMAGE_URL = "goalImageURL";
    private static final String GOAL_USER_ID = "userId";
    private static final String GOAL_TARGET = "targetAmount";
    private static final String GOAL_CURRENT_BALANCE = "currentBalance";
    private static final String GOAL_CREATED = "created";
    private static final String GOAL_STATUS = "status";
    private static final String GOAL_NAME = "name";
    private static final String GOAL_CONNECTED_USERS_LIST = "connectedUsers";

    private final List<GoalItem> mList = new ArrayList<>();

    private final LoadListener mListener;


    public interface LoadListener {
        void onLoadFinished(List<GoalItem> data);
    }

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
                            .setId(g.getInt(GOAL_ID))
                            .setCurrentBalance(g.optDouble(GOAL_CURRENT_BALANCE, 0))
                            .setSavingsTarget(g.optDouble(GOAL_TARGET, 0))
                            .setTitle(g.getString(GOAL_NAME))
                            .setImageUrl(g.getString(GOAL_IMAGE_URL))
                            .setUserId(g.getInt(GOAL_USER_ID))
                            .setStatus(g.getString(GOAL_STATUS));
                    // Integer[] users = g.getJSONArray()

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
