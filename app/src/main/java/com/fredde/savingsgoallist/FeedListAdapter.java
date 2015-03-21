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

public class FeedListAdapter extends BaseAdapter implements ListAdapter {

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
        TextView ammount;
        ImageView avatarImage;

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
            holder.ammount = (TextView) view.findViewById(R.id.feed_item_ammount);
            holder.avatarImage = (ImageView) view.findViewById(R.id.feed_item_avatar_image);
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
        holder.ammount.setText(Utils.buildAmountString(item.getAmount()));
        // Picasso.with(mContext).load(item.getUserId()).placeholder(R.drawable.list_placeholder).into(holder.avatarImage);
    }
}
