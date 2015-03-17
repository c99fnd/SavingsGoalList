package com.fredde.savingsgoallist;

/**
 * Data representation of a Savings goal.
 */
public class GoalItem {

    /**
     * Title
     */
    private String mTitle;

    /**
     * Gets goal title.
     *
     * @return title The tile of the goal.
     */
    public String getTitle() {
        return mTitle;
    }

    /**
     * Sets goal tile.
     *
     * @param title The title to set.
     * @return this.
     */
    public GoalItem setTitle(String title) {
        mTitle = title;
        return this;
    }
}
