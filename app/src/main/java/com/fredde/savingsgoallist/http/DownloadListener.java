package com.fredde.savingsgoallist.http;

/**
 * Listener for
 */
public interface DownloadListener {

    /**
     * Called when Download is finished.
     *
     * @param jsonStr The JSON string.
     */
    void onDownloadFinished(String jsonStr);

    /**
     * Called if the download task is canceled.
     */
    void onDownloadCanceled();

    /**
     * Called if no JSON string was fetched.
     */
    void onDownloadFailed();
}
