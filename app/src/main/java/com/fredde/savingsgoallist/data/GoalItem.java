package com.fredde.savingsgoallist.data;

import java.io.Serializable;
import java.util.List;

/**
 * Data representation of a Savings Goal.
 */
public class GoalItem implements Serializable {

    /**
     * Goal id.
     */
    private int mId;

    /**
     * Id for the user of the goal.
     */
    private int mUserId;

    /**
     * The savings target for this goal.
     */
    private double mSavingsTarget;

    /**
     * The current balance of this goal
     */
    private double mCurrentBalance;

    /**
     * The Uri of the image for this goal.
     */
    private String mImageUri;

    /**
     * The status of this goal.
     */
    private String mStatus;

    /**
     * List of users this goal is shared with.
     */
    private List<Integer> mConnectedUserIds;

    /**
     * Title of this goal.
     */
    private String mTitle;

    /**
     * Gets goal title.
     *
     * @return The tile of the goal.
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

    /**
     * Gets the saving target
     *
     * @return The savings target.
     */
    public double getSavingsTarget() {
        return mSavingsTarget;
    }

    /**
     * Sets the saving target
     *
     * @param target The target to set.
     * @return this.
     */
    public GoalItem setSavingsTarget(double target) {
        mSavingsTarget = target;
        return this;
    }

    /**
     * Gets the current balance.
     *
     * @return The current balance.
     */
    public double getCurrentBalance() {
        return mCurrentBalance;
    }

    /**
     * Sets the current balance.
     *
     * @param balance The current balance.
     * @return this.
     */
    public GoalItem setCurrentBalance(double balance) {
        mCurrentBalance = balance;
        return this;
    }

    /**
     * Gets image URL.
     *
     * @return The image URL
     */
    public String getImageUrl() {
        return mImageUri;
    }

    /**
     * Sets image URL
     *
     * @param uri The uri to set.
     * @return this.
     */
    public GoalItem setImageUrl(String uri) {
        mImageUri = uri;
        return this;
    }

    /**
     * Gets the goal Id.
     *
     * @return The id.
     */
    public int getId() {
        return mId;
    }

    /**
     * Set goal id.
     *
     * @param id The id to set.
     * @return this
     */
    public GoalItem setId(int id) {
        mId = id;
        return this;
    }

    /**
     * Gets the id of the creator of the goal.
     *
     * @return this.
     */
    public int getUserId() {
        return mUserId;
    }

    /**
     * Sets user id.
     *
     * @param userId id of the user that created the goal.
     * @return this.
     */
    public GoalItem setUserId(int userId) {
        mUserId = userId;
        return this;
    }

    /**
     * Return the status of the goal. Can be Deleted or Active.
     *
     * @return String status
     */
    public String getStatus() {
        return mStatus;
    }

    /**
     * Set the status of the goal.
     *
     * @param status The status of the goal.
     * @return this.
     */
    public GoalItem setStatus(String status) {
        this.mStatus = status;
        return this;
    }

    /**
     * Get a list of ids for users sharing this goal.
     *
     * @return list of users
     */
    public List<Integer> getConnectedUserIds() {
        return mConnectedUserIds;
    }

    /**
     * Set connected users.
     *
     * @param users list of users
     */
    public GoalItem setConnectedUserIds(List<Integer> users) {
        mConnectedUserIds = users;
        return this;
    }
}
