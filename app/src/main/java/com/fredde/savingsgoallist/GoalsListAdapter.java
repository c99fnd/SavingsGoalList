package com.fredde.savingsgoallist;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

/**
 * Adapter feeding data to the Goals List.
 */
public class GoalsListAdapter extends BaseAdapter implements ListAdapter {

    /**
     * Application context.
     */
    private final Context mContext;

    /**
     * Used to inflate the list items.
     */
    private final LayoutInflater mInflater;

    /**
     * Data
     */
    private GoalItem[] mItems;


    public GoalsListAdapter(Context context, LayoutInflater inflater) {
        mContext = context;
        mInflater = inflater;
        mItems = new GoalItem[5];

        /* Mock Data */
        mItems[0] = new GoalItem().setTitle("Roskilde Festival");
        mItems[1] = new GoalItem().setTitle("Roadburn Festival");;
        mItems[2] = new GoalItem().setTitle("Hellacopters Vinyl");;
        mItems[3] = new GoalItem().setTitle("Xbox One");;
        mItems[4] = new GoalItem().setTitle("Vacation");;
    }

    @Override
    public int getCount() {
        return mItems.length;
    }

    @Override
    public Object getItem(int position) {
        return mItems[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final GoalItem item = mItems[position];
        View view = convertView;
        TextView title = null;
        if (view == null) {
            view = mInflater.inflate(R.layout.goal_list_item, parent, false);

        }
        title = (TextView) view.findViewById(R.id.list_item_title);
        if (title != null) {
            title.setText(item.getTitle());
        }
        Log.d("Fredde","getView");
        return view;
    }
}
