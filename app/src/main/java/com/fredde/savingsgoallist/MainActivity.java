package com.fredde.savingsgoallist;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.fredde.savingsgoallist.data.GoalItem;
import com.fredde.savingsgoallist.data.GoalItemLoaderTask;
import com.fredde.savingsgoallist.fragments.DetailsFragment;
import com.fredde.savingsgoallist.fragments.GoalsListFragment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity implements GoalsListCallback {

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

        /* Set a listener to the back stack to be able to do simple UI operations depending
           on the stack state. */
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

        //mLoadGoalsTask = new GoalItemLoaderTask(mData, this);
        //DownloadJSonTask task = new DownloadJSonTask(this);
        //task.execute(SAVINGS);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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
