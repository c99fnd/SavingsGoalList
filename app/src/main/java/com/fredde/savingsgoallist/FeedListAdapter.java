package com.fredde.savingsgoallist;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.fredde.savingsgoallist.data.FeedItem;
import com.fredde.savingsgoallist.utils.Utils;
import com.squareup.picasso.Picasso;

public class FeedListAdapter extends BaseAdapter implements ListAdapter {

    /**
     * Context
     */
    private final Context mContext;

    /**
     * Data
     */
    private FeedItem[] mItems;

    /**
     * View holder.
     */
    static class ViewHolder {
        TextView message;
        TextView amount;
        TextView timestamp;
        ImageView avatarImage;
        ImageView badgeImage;

    }

    /**
     * Constructor.
     */
    public FeedListAdapter(Context context) {
        mContext = context;
        mItems = new FeedItem[0];
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
        final FeedItem item = mItems[position];
        ViewHolder holder;
        View view = convertView;

        if (view == null) {
            holder = new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.details_feed_list_item, parent, false);
            holder.message = (TextView) view.findViewById(R.id.feed_item_message);
            holder.amount = (TextView) view.findViewById(R.id.feed_item_ammount);
            holder.timestamp = (TextView) view.findViewById(R.id.feed_item_time);
            holder.avatarImage = (ImageView) view.findViewById(R.id.feed_item_avatar);
            holder.badgeImage = (ImageView) view.findViewById(R.id.feed_item_badge);
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
    public void setData(FeedItem[] items) {
        mItems = items.clone();
    }

    private void setDataToHolder(ViewHolder holder, FeedItem item) {
        holder.message.setText(Html.fromHtml(item.getMessage()));
        holder.amount.setText(Utils.buildAmountString(item.getAmount()));
        holder.timestamp.setText(item.getTimeStamp());
        holder.badgeImage.setImageResource(getBadgeResId(item.getType()));
        Picasso.with(mContext).load("http://qapital-ios-testtask.herokuapp.com/avatars/johan.jpg")
                .placeholder(R.drawable.list_placeholder).into(holder.avatarImage);
    }

    private int getBadgeResId(String type) {
        switch (type) {
            case "guilty":
                return R.drawable.rule_penalty;
            default:
                return R.drawable.rule_roundup;
        }

    }
}
