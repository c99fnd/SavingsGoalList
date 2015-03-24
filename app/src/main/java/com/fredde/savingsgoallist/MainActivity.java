package com.fredde.savingsgoallist;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.transition.Slide;
import android.view.Gravity;
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
        DetailsFragment frag = (DetailsFragment)
                getSupportFragmentManager().findFragmentByTag(DETAILS_TAG);

        Bundle args = new Bundle();
        args.putSerializable(DetailsFragment.ARG_ITEM, item);
        if (frag != null) {
            frag.setArguments(args);
        } else {
            frag = new DetailsFragment();
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                frag.setEnterTransition(new Slide(Gravity.BOTTOM));
                frag.setAllowEnterTransitionOverlap(true);

            }
            frag.setArguments(args);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, frag, DETAILS_TAG)
                    .addToBackStack(null).commit();
        }
    }
}
