package com.fredde.savingsgoallist.fragments;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.fredde.savingsgoallist.FeedListAdapter;
import com.fredde.savingsgoallist.GoalsListCallback;
import com.fredde.savingsgoallist.R;
import com.fredde.savingsgoallist.data.FeedItem;
import com.fredde.savingsgoallist.data.FeedItemLoaderTask;
import com.fredde.savingsgoallist.data.GoalItem;
import com.fredde.savingsgoallist.utils.Utils;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Displays the details view of a savings goal.
 */
public class DetailsFragment extends Fragment implements FeedItemLoaderTask.LoadListener {

    /**
     * URLs
     */
    private final String BASR_URL = "http://qapital-ios-testtask.herokuapp.com/savingsgoals/";
    private final String FEED = "/feed";


    public static final String ARG_ITEM = "goal_item";

    public GoalItem mItem;

    GoalsListCallback mCallback;

    FeedListAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        setHasOptionsMenu(true);
        mItem = (GoalItem) getArguments().getSerializable(ARG_ITEM);
        mAdapter = new FeedListAdapter(getActivity().getApplicationContext());
        new FeedItemLoaderTask(this).execute(BASR_URL + mItem.getId() + FEED);
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
        mItem = (GoalItem) getArguments().getSerializable(ARG_ITEM);
        Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "fonts/BentonSans-Light.otf");

        /* Inflate the main view incl. feed list*/
        View rootView = inflater.inflate(R.layout.details_fragment, container, false);
        ListView list = (ListView) rootView.findViewById(R.id.details_list_view);

        /* Inflate and set up header view */
        View header = inflater.inflate(R.layout.details_header, list, false);

        ImageView headerImage = (ImageView) header.findViewById(R.id.details_header_img);
        Picasso.with(getActivity().getApplicationContext()).load(mItem.getImageUrl())
                .placeholder(R.drawable.list_placeholder).into(headerImage);

        TextView nameText = (TextView) header.findViewById(R.id.details_header_title);
        nameText.setText(mItem.getTitle());


        TextView moneyText = (TextView) header.findViewById(R.id.details_header_subtitle);
        moneyText.setText(Utils.buildHeaderProgressString(getActivity().getBaseContext(), mItem.getCurrentBalance(), mItem.getSavingsTarget()));

        Utils.setLayoutFont(tf, nameText, moneyText);

        list.addHeaderView(header);
        list.setAdapter(mAdapter);

        ((ActionBarActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.options_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_goal:
                Toast.makeText(getActivity().getBaseContext(), "Add cash not implemented yet.", Toast.LENGTH_SHORT).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onLoadFinished(List<FeedItem> data) {
        FeedItem[] items = data.toArray(new FeedItem[data.size()]);
        mAdapter.setData(items);
        mAdapter.notifyDataSetChanged();
    }
}
