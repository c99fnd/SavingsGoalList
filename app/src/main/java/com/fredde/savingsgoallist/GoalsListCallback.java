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

    /**
     * Called when a fraglemt (e.g. detailsfragment) needs a Goal Item.
     *
     * @return Goal Item.
     */
    public GoalItem onRequiresSelectedGoalItem();
}
