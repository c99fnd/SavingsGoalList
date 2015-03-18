package com.fredde.savingsgoallist.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fredde.savingsgoallist.R;

/**
 * Displays the details view of a savings goal.
 */
public class GoalDetailsFragment extends Fragment {

    public static final String ARG_ID = "GoalId";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.details_view_fragment, container, false);

        String goalId = getArguments().getString(ARG_ID);
        Log.d("Album", "onCreateView GoalId " + goalId);

        return rootView;
    }

    public void updateDetailsView(String goalId) {
        Log.d("Album", "updateDetailsView GoalId " + goalId);
    }
}
