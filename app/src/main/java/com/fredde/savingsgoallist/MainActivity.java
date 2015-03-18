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
import com.fredde.savingsgoallist.ui.fragments.CreateNewGoalFragment;
import com.fredde.savingsgoallist.ui.fragments.GoalDetailsFragment;
import com.fredde.savingsgoallist.ui.fragments.GoalsListFragment;
import com.fredde.savingsgoallist.utils.DebugUtils;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity implements GoalsListFragment.OnGoalSelectedListener, DownloadListener, GoalItemLoaderTask.LoadListener {

    private final String BASE_URL = "http://qapital-ios-testtask.herokuapp.com/";
    private final String SAVINGS = BASE_URL + "savingsgoals";
    private final String RULES = BASE_URL + "savingsrules";

    List<GoalItem> mData = new ArrayList<>();
    GoalItemLoaderTask mLoadGoalsTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new GoalsListFragment())
                    .commit();
        }

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
                doCreateNewGoal();
                return true;
            default:
                return false;
        }
    }

    @Override
    public void onGoalSelected(String goalId) {
        GoalDetailsFragment detailsFrag = (GoalDetailsFragment)
                getSupportFragmentManager().findFragmentById(R.id.details_fragment);

        if (detailsFrag != null) {
            detailsFrag.updateDetailsView(goalId);
        } else {

            GoalDetailsFragment fragment = new GoalDetailsFragment();
            Bundle args = new Bundle();
            args.putString(GoalDetailsFragment.ARG_ID, goalId);
            fragment.setArguments(args);

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            transaction.replace(R.id.container, fragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }

    @Override
    public void onDownloadFinished(String jsonStr) {
        DebugUtils.debugLog(jsonStr);

        Toast.makeText(getApplicationContext(), "Connection Compleded", Toast.LENGTH_SHORT).show();
        mLoadGoalsTask.execute(jsonStr);

    }

    @Override
    public void onDownloadCanceled() {
        Toast.makeText(getApplicationContext(), "Connection Failed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoadFinished(int itemsLoaded) {
        DebugUtils.debugLog("Load Finished");
        DebugUtils.debugLog(mData.get(itemsLoaded - 1).getTitle());
    }

    private void doCreateNewGoal() {
        CreateNewGoalFragment createFrag = (CreateNewGoalFragment)
                getSupportFragmentManager().findFragmentById(R.id.create_new_goal);

        if (createFrag == null) {
            //Do nothing.
            CreateNewGoalFragment fragment = new CreateNewGoalFragment();

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            transaction.replace(R.id.container, fragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }
}
