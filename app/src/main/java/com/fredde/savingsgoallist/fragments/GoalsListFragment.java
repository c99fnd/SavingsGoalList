package com.fredde.savingsgoallist.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.fredde.savingsgoallist.GoalsListAdapter;
import com.fredde.savingsgoallist.R;

/**
 * Displays a list of savings goals. Is the start view in the application.
 */
public class GoalsListFragment extends Fragment {
    /**
     * Constructor.
     */
    public GoalsListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.goals_list_fragment, container, false);
        ListView list = (ListView) rootView.findViewById(R.id.goals_list_view);
        GoalsListAdapter adapter = new GoalsListAdapter(getActivity().getApplicationContext(), inflater);
        list.setAdapter(adapter);
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ActionBar bar =  ((ActionBarActivity)activity).getSupportActionBar();
        if(bar != null) {
            bar.setTitle("Savings");
        }
    }
}
