package com.fredde.savingsgoallist;

import com.fredde.savingsgoallist.data.GoalItem;

/**
 * Callback
 */
public interface GoalsListCallback {
    /**
     * Called when goal is selected.
     *
     * @param item The goal item.
     */
    public void onGoalSelected(GoalItem item);
}
