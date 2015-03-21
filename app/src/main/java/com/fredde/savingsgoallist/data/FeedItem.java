package com.fredde.savingsgoallist.data;

/**
 * Data representation of a feed list item.
 */
public class FeedItem {
    /**
     * The feed item Id.
     */
    private String mId;

    /**
     * The type of feed item. Currently only "Savings" is supported.
     */
    private String mType;

    /**
     * Timestamp with the format: 2015-03-10T14:55:16.025Z
     */
    private String mTimeStamp;

    /**
     * Message string. Can contain HTTP elements.
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


    /**
     * Gets the ID.
     *
     * @return The Id for this item.
     */
    public String getId() {
        return mId;
    }

    /**
     * Sets the Id.
     *
     * @param id The ID to set.
     * @return this.
     */
    public FeedItem setId(String id) {
        mId = id;
        return this;
    }

    /**
     * Gets the type of the feed item
     *
     * @return The type
     */
    public String getType() {
        return mType;
    }

    /**
     * Sets the type of the feed item
     *
     * @param type The type to set.
     * @return this
     */
    public FeedItem setType(String type) {
        mType = type;
        return this;
    }

    /**
     * Gets the timestamp for when this feed item was created.
     *
     * @return The timestamp.
     */
    public String getTimeStamp() {
        return mTimeStamp;
    }

    /**
     * Sets the timestamp for when this feed item was created
     *
     * @param timestamp The timestamp to set.
     * @return this.
     */
    public FeedItem setTimeStamp(String timestamp) {
        mTimeStamp = timestamp;
        return this;
    }

    /**
     * Gets the message.
     *
     * @return The message.
     */
    public String getMessage() {
        return mMsg;
    }

    /**
     * Sets the message for this item.
     *
     * @param msg The message to set.
     * @return this.
     */
    public FeedItem setMessage(String msg) {
        mMsg = msg;
        return this;
    }

    /**
     * Get the amount saved in this feed item.
     *
     * @return The amount saved.
     */
    public double getAmount() {
        return mAmount;
    }

    /**
     * Sets the amount saved for this item.
     *
     * @param amount The amount to set
     * @return this.
     */
    public FeedItem setAmount(double amount) {
        mAmount = amount;
        return this;
    }

    /**
     * Gets the ID for the user that created this item.
     *
     * @return The user ID.
     */
    public int getUserId() {
        return mUserId;
    }

    /**
     * Sets the ID for the user that created this item.
     *
     * @param id The ID to set.
     * @return this.
     */
    public FeedItem setUserId(int id) {
        mUserId = id;
        return this;
    }
}
