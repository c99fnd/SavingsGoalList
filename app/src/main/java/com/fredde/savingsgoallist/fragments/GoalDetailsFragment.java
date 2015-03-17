package com.fredde.savingsgoallist.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fredde.savingsgoallist.R;

/**
 * Displays the details view of a savings goal.
 */
public class GoalDetailsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.details_view_fragment, container,false);
       return rootView;
    }
}
