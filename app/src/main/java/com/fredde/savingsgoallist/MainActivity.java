package com.fredde.savingsgoallist;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;

import com.fredde.savingsgoallist.data.GoalItem;
import com.fredde.savingsgoallist.fragments.DetailsFragment;
import com.fredde.savingsgoallist.fragments.GoalsListFragment;


public class MainActivity extends ActionBarActivity implements GoalsListCallback {

    private static final String LIST_TAG = "listFragment";
    private static final String DETAILS_TAG = "detailsFragment";

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
                        } else {
                            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                        }
                    }
                });

        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, new GoalsListFragment(), LIST_TAG)
                .commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getSupportFragmentManager().popBackStack();
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
