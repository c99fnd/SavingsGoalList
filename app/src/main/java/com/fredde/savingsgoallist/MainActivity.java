package com.fredde.savingsgoallist;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.fredde.savingsgoallist.data.GoalItem;
import com.fredde.savingsgoallist.data.GoalItemLoaderTask;
import com.fredde.savingsgoallist.http.DownloadJSonTask;
import com.fredde.savingsgoallist.http.DownloadListener;
import com.fredde.savingsgoallist.ui.fragments.DetailsFragment;
import com.fredde.savingsgoallist.ui.fragments.GoalsListFragment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity implements GoalsListCallback, DownloadListener, GoalItemLoaderTask.LoadListener {

    private static final String LIST_FRAGMENT_TAG = "listfragment";
    private static final String DETAILS_FRAGMENT_TAG = "detailsfragment";

    private final String BASE_URL = "http://qapital-ios-testtask.herokuapp.com/";
    private final String SAVINGS = BASE_URL + "savingsgoals";
    private final String RULES = BASE_URL + "savingsrules";

    List<GoalItem> mData = new ArrayList<>();
    GoalItemLoaderTask mLoadGoalsTask;
    private GoalItem mSelectedItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new GoalsListFragment(), LIST_FRAGMENT_TAG)
                    .commit();
        }
        Picasso.with(getApplicationContext()).setIndicatorsEnabled(true);
        Picasso.with(getApplicationContext()).setLoggingEnabled(true);

        mLoadGoalsTask = new GoalItemLoaderTask(mData, this);
        DownloadJSonTask task = new DownloadJSonTask(this);
        task.execute(SAVINGS);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_goal:
                /* Not implemented. */
                return true;
            default:
                return false;
        }
    }

    @Override
    public void onGoalSelected(GoalItem item) {
        mSelectedItem = item;

        DetailsFragment detailsFrag = (DetailsFragment)
                getSupportFragmentManager().findFragmentById(R.id.details_fragment);

        if (detailsFrag != null) {
            detailsFrag.updateDetailsView(item.getGoalId());
        } else {

            DetailsFragment fragment = new DetailsFragment();
            Bundle args = new Bundle();
            args.putInt(DetailsFragment.ARG_ID, item.getGoalId());
            fragment.setArguments(args);

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            transaction.replace(R.id.container, fragment, DETAILS_FRAGMENT_TAG);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }

    @Override
    public GoalItem onRequiresSelectedGoalItem() {
        return mSelectedItem;
    }

    @Override
    public void onDownloadFinished(String jsonStr) {
        mLoadGoalsTask.execute(jsonStr);
    }

    @Override
    public void onDownloadCanceled() {
        Toast.makeText(getApplicationContext(), "Connection canceled", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDownloadFailed() {
        Toast.makeText(getApplicationContext(), "Connection Failed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoadFinished(int itemsLoaded) {
        GoalsListFragment frag = (GoalsListFragment) getSupportFragmentManager().findFragmentByTag(LIST_FRAGMENT_TAG);
        GoalsListAdapter adapter = frag.getAdapter();
        GoalItem[] items = mData.toArray(new GoalItem[mData.size()]);
        adapter.setData(items);
        adapter.notifyDataSetChanged();
    }
}
