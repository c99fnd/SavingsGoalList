package com.fredde.savingsgoallist.ui.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.fredde.savingsgoallist.GoalsListAdapter;
import com.fredde.savingsgoallist.GoalsListCallback;
import com.fredde.savingsgoallist.R;
import com.fredde.savingsgoallist.data.GoalItem;

/**
 * Displays a list of savings goals. Is the start view in the application.
 */
public class GoalsListFragment extends Fragment implements AdapterView.OnItemClickListener {

    GoalsListCallback mCallback;

    GoalsListAdapter mAdapter;

    /**
     * Constructor.
     */
    public GoalsListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        mAdapter = new GoalsListAdapter(getActivity().getApplicationContext());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.goals_list_fragment, container, false);
        ListView list = (ListView) rootView.findViewById(R.id.goals_list_view);
        list.setOnItemClickListener(this);
        list.setAdapter(mAdapter);
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        /* Make Sure the hosting acitivity implements OnGoalSelectedListener.
        If not, information cannot be sent back. */

        try {
            mCallback = (GoalsListCallback) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement GoalsListCallback");
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        GoalItem item = (GoalItem) parent.getAdapter().getItem(position);
        mCallback.onGoalSelected(item);
    }

    /**
     * Gets the adapter
     *
     * @return the adapter.
     */
    public GoalsListAdapter getAdapter() {
        return mAdapter;
    }
}
