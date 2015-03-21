package com.fredde.savingsgoallist;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
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
import com.fredde.savingsgoallist.utils.DebugUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity implements GoalsListCallback, DownloadListener, GoalItemLoaderTask.LoadListener {

    private static final String LIST_TAG = "listFragment";
    private static final String DETAILS_TAG = "detailsFragment";

    private final String BASE_URL = "http://qapital-ios-testtask.herokuapp.com/";
    private final String SAVINGS = BASE_URL + "savingsgoals";
    private final String RULES = BASE_URL + "savingsrules";

    List<GoalItem> mData = new ArrayList<>();
    GoalItemLoaderTask mLoadGoalsTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState != null) {
            return;
        }
        getSupportFragmentManager().addOnBackStackChangedListener(
                new FragmentManager.OnBackStackChangedListener() {
                    public void onBackStackChanged() {
                        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
                            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                        }
                    }
                });

        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, new GoalsListFragment(), LIST_TAG)
                .commit();


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
        DebugUtils.log("onOptionsItemSelected");
        switch (item.getItemId()) {
            case android.R.id.home:
                getSupportFragmentManager().popBackStack();
                return true;
            case R.id.action_add_goal:
                /* Not implemented. */
                return true;
            default:
                return false;
        }
    }

    @Override
    public void onGoalSelected(GoalItem item) {
        DetailsFragment detailsFrag = (DetailsFragment)
                getSupportFragmentManager().findFragmentById(R.id.details_fragment);

        Bundle args = new Bundle();
        args.putSerializable(DetailsFragment.ARG_ITEM, item);
        if (detailsFrag != null) {
            detailsFrag.setArguments(args);
        } else {
            createAndAddDetailsFragment(args);
        }
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
        GoalsListFragment frag = (GoalsListFragment) getSupportFragmentManager().findFragmentByTag(LIST_TAG);
        GoalsListAdapter adapter = frag.getAdapter();
        GoalItem[] items = mData.toArray(new GoalItem[mData.size()]);
        adapter.setData(items);
        adapter.notifyDataSetChanged();
    }

    /**
     * Creates the DetailsFragment and adds it using a custom animation.
     *
     * @param args Bundle containing the Serializable {@link GoalItem} to display details from.
     */
    private void createAndAddDetailsFragment(Bundle args) {
        DetailsFragment frag = new DetailsFragment();

        frag.setArguments(args);
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.slide_in_right, R.anim.stay, 0, R.anim.slide_out_right)
                .replace(R.id.container, frag, DETAILS_TAG)
                .addToBackStack(null).commit();
    }
}
