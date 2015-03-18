package com.fredde.savingsgoallist.data;

import java.util.List;

/**
 * Data representation of a Savings goal.
 */
public class GoalItem {

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
    private float mSavingsTarget;

    /**
     * The current balance of this goal
     */
    private float mCurrentBalance;

    /**
     * The Uri of the image for this goal.
     */
    private String mImageUri;

    /**
     * The status of this goal
     */
    private String mStatus;

    /**
     * List of users this goal is shared with.
     */
    private List<Integer> mConnectedUserIds;

    /**
     * Title of this goal
     */
    private String mTitle;

    /**
     * Gets goal title.
     *
     * @return Sting The tile of the goal.
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
     * @return float The Saving Target.
     */
    public float getSavingsTarget() {
        return mSavingsTarget;
    }

    /**
     * Sets the saving target
     *
     * @param target The target to set.
     * @return this.
     */
    public GoalItem setSavingsTarget(float target) {
        mSavingsTarget = target;
        return this;
    }

    /**
     * Gets the current balance.
     *
     * @return float The current balance.
     */
    public float getCurrentBalance() {
        return mCurrentBalance;
    }

    /**
     * Sets the current balance.
     *
     * @param balance current balance.
     * @return this.
     */
    public GoalItem setCurrentBalance(float balance) {
        mCurrentBalance = balance;
        return this;
    }

    /**
     * Gets the image uri.
     *
     * @return String image uri
     */
    public String getImageUri() {
        return mImageUri;
    }

    /**
     * Set Image Uri
     *
     * @param uri The uri to set.
     * @return this.
     */
    public GoalItem setImageUri(String uri) {
        mImageUri = uri;
        return this;
    }

    /**
     * Get Goal Id.
     *
     * @return int Id.
     */
    public int getGoalId() {
        return mId;
    }

    /**
     * Sets goal id.
     *
     * @param id The id to set.
     * @return this
     */
    public GoalItem setGoalId(int id) {
        mId = id;
        return this;
    }

    /**
     * Get the id of the creator of the goal.
     *
     * @return this.
     */
    public int getmUserId() {
        return mUserId;
    }

    /**
     * Set user id.
     *
     * @param userId id of the user that created the goal
     * @return this.
     */
    public GoalItem setmUserId(int userId) {
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
    public GoalItem setmConnectedUserIds(List<Integer> users) {
        mConnectedUserIds = users;
        return this;
    }
}
