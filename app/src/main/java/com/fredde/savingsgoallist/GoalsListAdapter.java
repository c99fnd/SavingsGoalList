package com.fredde.savingsgoallist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.fredde.savingsgoallist.data.GoalItem;

/**
 * Adapter feeding data to the Goals List View.
 */
public class GoalsListAdapter extends BaseAdapter implements ListAdapter {

    /**
     * Space.
     */
    static final String SPACE = " ";

    /**
     * String in between current savings and target.
     */
    private final String mProgressText;
    /**
     * Used to inflate the list items.
     */
    private final LayoutInflater mInflater;

    /**
     * Data
     */
    private GoalItem[] mItems;

    /**
     * View holder.
     */
    static class ViewHolder {
        TextView title;
        TextView subTitle;
        ImageView imageView;
        ProgressBar progress;
        String progressText;
        int position;
    }

    public GoalsListAdapter(Context context, LayoutInflater inflater) {
        mInflater = inflater;
        mProgressText = context.getResources().getString(R.string.text_saved_of);


        mItems = new GoalItem[4];

        /* Mock Data */
        mItems[0] = new GoalItem().setTitle("Roskilde Festival").setSavingsTarget(2000).setCurrentBalance(1500);
        mItems[1] = new GoalItem().setTitle("Roadburn Festival").setSavingsTarget(1600).setCurrentBalance(1600);
        mItems[2] = new GoalItem().setTitle("Hellacopters Vinyl").setSavingsTarget(5000).setCurrentBalance(1500);
        mItems[3] = new GoalItem().setTitle("Xbox One").setSavingsTarget(2500).setCurrentBalance(0);
        mItems[3] = new GoalItem().setTitle("PS4").setSavingsTarget(0).setCurrentBalance(1500);
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
        ViewHolder holder;
        View view = convertView;

        if (view == null) {
            holder = new ViewHolder();
            view = mInflater.inflate(R.layout.goal_list_item, parent, false);
            holder.title = (TextView) view.findViewById(R.id.list_item_title);
            holder.subTitle = (TextView) view.findViewById(R.id.list_item_subtitle);
            holder.imageView = (ImageView) view.findViewById(R.id.list_item_image);
            holder.progress = (ProgressBar) view.findViewById(R.id.list_item_progress);
            view.setTag(holder);
        }
        holder = (ViewHolder) view.getTag();
        setDataToHolder(holder, item);
        return view;
    }

    /**
     * Sets data from GoalItem to HolderView.
     *
     * @param holder The holder to set data to.
     * @param item   The item to get data from.
     */
    private void setDataToHolder(ViewHolder holder, GoalItem item) {
        holder.title.setText(item.getTitle());
        holder.subTitle.setText(item.getCurrentBalance() + SPACE + mProgressText + SPACE + item.getSavingsTarget());
        holder.progress.setProgress(calculateProgress(holder, item));
    }

    /**
     * Calculates the progress for the progress bar in fractions of progress bar max value.
     *
     * @param item   GoalItem containing the progress info.
     * @param holder ViewHolder containing the progress bar.
     * @return int progress.
     */
    private int calculateProgress(ViewHolder holder, GoalItem item) {
        if (item.getSavingsTarget() == 0) {
            return holder.progress.getMax();
        }
        return (int) (item.getCurrentBalance() / item.getSavingsTarget() * holder.progress.getMax());
    }
}
