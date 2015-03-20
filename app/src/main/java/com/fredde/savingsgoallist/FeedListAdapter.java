package com.fredde.savingsgoallist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.fredde.savingsgoallist.data.FeedItem;

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
        TextView title;
        TextView subTitle;
        ImageView imageView;
    }

    /**
     * Constructor.
     */
    public FeedListAdapter(Context context) {
        mContext = context;
        mItems = new FeedItem[1];
        FeedItem item = new FeedItem().setUserId(666);
        mItems[0] = item;

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
            holder.title = (TextView) view.findViewById(R.id.list_item_title);
            view.setTag(holder);
        }
        holder = (ViewHolder) view.getTag();
        setDataToHolder(holder, item);
        return view;
    }

    private void setDataToHolder(ViewHolder holder, FeedItem item) {
        holder.title.setText("" + item.getUserId());
    }
}
