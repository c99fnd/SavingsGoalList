package com.fredde.savingsgoallist.ui.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.fredde.savingsgoallist.FeedListAdapter;
import com.fredde.savingsgoallist.GoalsListCallback;
import com.fredde.savingsgoallist.R;
import com.fredde.savingsgoallist.data.GoalItem;
import com.fredde.savingsgoallist.utils.StringUtils;
import com.squareup.picasso.Picasso;

/**
 * Displays the details view of a savings goal.
 */
public class DetailsFragment extends Fragment {

    public static final String ARG_ID = "GoalId";

    public GoalItem mItem;

    GoalsListCallback mCallback;

    FeedListAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        mAdapter = new FeedListAdapter(getActivity().getApplicationContext());
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallback = (GoalsListCallback) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement GoalsListCallback");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        int goalId = getArguments().getInt(ARG_ID);
        mItem = mCallback.onRequiresSelectedGoalItem();

        /* Inflate the main view incl. feed list*/
        View rootView = inflater.inflate(R.layout.details_fragment, container, false);
        ListView list = (ListView) rootView.findViewById(R.id.details_list_view);

        /* Inflate and set up header view */
        View header = inflater.inflate(R.layout.details_header, list, false);
        ImageView headerImage = (ImageView) header.findViewById(R.id.details_header_img);
        TextView nameText = (TextView) header.findViewById(R.id.details_header_title);
        TextView moneyText = (TextView) header.findViewById(R.id.details_header_subtitle);
        nameText.setText(mItem.getTitle());
        moneyText.setText(StringUtils.buildProgressString(getActivity().getBaseContext(), mItem));
        list.addHeaderView(header);
        list.setAdapter(mAdapter);

        Picasso.with(getActivity().getApplicationContext()).load(mItem.getImageUrl()).placeholder(R.drawable.list_placeholder).into(headerImage);
        return rootView;
    }

    public void updateDetailsView(int goalId) {
        Log.d("Album", "updateDetailsView GoalId " + goalId);
    }
}