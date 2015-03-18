package com.fredde.savingsgoallist.data;

import android.os.AsyncTask;

import com.fredde.savingsgoallist.utils.DebugUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

    private final List<GoalItem> mList;

    private final LoadListener mListener;


    public interface LoadListener {
        void onLoadFinished(int itemsLoaded);
    }

    public GoalItemLoaderTask(List<GoalItem> list, LoadListener listener) {
        mListener = listener;
        mList = list;
    }

    @Override
    protected Integer doInBackground(String... jsonStrs) {
        if (isCancelled()) {
            return 0;
        }

        if (jsonStrs[0] != null) {
            try {
                JSONObject jsonObj = new JSONObject(jsonStrs[0]);

                /* Get JSON Array */
                JSONArray goals = jsonObj.getJSONArray(GOALS);
                DebugUtils.debugLog("ArraySize " + goals.length());

                /* Loop through goals. */
                for (int i = 0; i < goals.length(); i++) {
                    JSONObject g = goals.getJSONObject(i);

                    GoalItem item = new GoalItem()
                            .setGoalId(g.getInt(GOAL_ID))
                            .setCurrentBalance(g.optInt(GOAL_CURRENT_BALANCE))
                            .setSavingsTarget(g.optInt(GOAL_TARGET))
                            .setTitle(g.getString(GOAL_NAME))
                            .setImageUri(g.getString(GOAL_IMAGE_URL))
                            .setmUserId(g.getInt(GOAL_USER_ID))
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
    protected void onCancelled() {
    }

    @Override
    protected void onPostExecute(Integer loadedItems) {
        mListener.onLoadFinished(loadedItems);
    }
}
