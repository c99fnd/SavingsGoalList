package com.fredde.savingsgoallist.data;

public class FeedItem {
    /**
     * The feed Id.
     */
    private String mId;

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
    private String mMsg;

    /**
     * The amount saved in the feed.
     */
    private double mAmount;

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


    public String getId() {
        return mId;
    }

    public FeedItem setId(String id) {
        mId = id;
        return this;
    }

    public String getType() {
        return mType;
    }

    public FeedItem setType(String type) {
        mType = type;
        return this;
    }

    public String getTimeStamp() {
        return mTimeStamp;
    }

    public FeedItem setTimeStamp(String timestamp) {
        mTimeStamp = timestamp;
        return this;
    }

    public String getMessage() {
        return mMsg;
    }

    public FeedItem setMessage(String msg) {
        mMsg = msg;
        return this;
    }

    public double getAmount() {
        return mAmount;
    }

    public FeedItem setAmount(double amount) {
        mAmount = amount;
        return this;
    }

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
