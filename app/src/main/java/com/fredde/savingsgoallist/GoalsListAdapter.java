package com.fredde.savingsgoallist;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.fredde.savingsgoallist.data.GoalItem;
import com.fredde.savingsgoallist.utils.Utils;
import com.squareup.picasso.Picasso;

/**
 * Adapter feeding data to the Goals List View.
 */
public class GoalsListAdapter extends BaseAdapter implements ListAdapter {

    /**
     * Used to inflate the list items.
     */
    private final Context mContext;

    /**
     * Custom fonts.
     */
    private final Typeface mTypefaceLight;

    /**
     * Custom fonts.
     */
    private final Typeface mTypefaceNorm;

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

    public GoalsListAdapter(Context context) {
        mContext = context;
        mItems = new GoalItem[0];
        mTypefaceLight = Typeface.createFromAsset(mContext.getAssets(), "fonts/BentonSans-Light.otf");
        mTypefaceNorm = Typeface.createFromAsset(mContext.getAssets(), "fonts/BentonSans-Light.otf");
    }

    @Override
    public int getCount() {
        return mItems.length;
    }

    @Override
    public GoalItem getItem(int position) {
        return mItems[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final GoalItem item = getItem(position);
        ViewHolder holder;
        View view = convertView;

        if (view == null) {
            holder = new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.goals_list_item, parent, false);

            holder.title = (TextView) view.findViewById(R.id.list_item_title);
            Utils.setLayoutFont(mTypefaceNorm, holder.title);

            holder.subTitle = (TextView) view.findViewById(R.id.list_item_subtitle);
            Utils.setLayoutFont(mTypefaceLight, holder.subTitle);

            holder.imageView = (ImageView) view.findViewById(R.id.list_item_image);
            holder.progress = (ProgressBar) view.findViewById(R.id.list_item_progress);
            view.setTag(holder);
        }
        holder = (ViewHolder) view.getTag();
        setDataToHolder(holder, item);
        return view;
    }

    /**
     * Sets new data.
     *
     * @param items The data to set.
     */
    public void setData(GoalItem[] items) {
        mItems = items.clone();
    }

    /**
     * Sets data from GoalItem to HolderView.
     *
     * @param holder The holder to set data to.
     * @param item   The item to get data from.
     */
    private void setDataToHolder(ViewHolder holder, GoalItem item) {
        holder.title.setText(item.getTitle());
        holder.subTitle.setText(Utils.buildListProgressString(mContext, item.getCurrentBalance(), item.getSavingsTarget()));
        holder.progress.setProgress(calculateProgress(holder, item));
        Picasso.with(mContext).load(item.getImageUrl()).placeholder(R.drawable.list_placeholder).into(holder.imageView);
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
