package com.fredde.savingsgoallist.http;


public interface DownloadListener {

    void onDownloadFinished(String jsonStr);

    void onDownloadCanceled();
}
