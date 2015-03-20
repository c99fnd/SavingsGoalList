package com.fredde.savingsgoallist.data;

public class FeedItem {
    /**
     * The feed Id.
     */
    private String mid;

    /**
     * The type (saving")
     */
    private String mType;

    /**
     * TODO//  Format: 2015-03-10T14:55:16.025Z
     */
    private String mTimeStamp;

    /**
     * Message string. (html style elements)
     */
    private String mMessage;

    /**
     * The amount saved in the feed.
     */
    private double mAmountSaved;

    /**
     * User Id of event creator.
     */
    private int mUserId;

    /**
     * The name rule the savings fell under.
     */
    private String mRuleString;

    /**
     * The resource Id of the rule.
     */
    private int mResId;

    /**
     * Gets user ID.
     *
     * @return The ID.
     */
    public int getUserId() {
        return mUserId;
    }

    /**
     * Sets user ID.
     *
     * @param id
     * @return this.
     */
    public FeedItem setUserId(int id) {
        mUserId = id;
        return this;
    }
}
