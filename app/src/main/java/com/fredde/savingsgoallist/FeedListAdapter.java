package com.fredde.savingsgoallist;

import android.content.Context;
import android.os.AsyncTask;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.fredde.savingsgoallist.data.FeedItem;
import com.fredde.savingsgoallist.http.AvatarUrlFetcher;
import com.fredde.savingsgoallist.utils.Utils;
import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

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
     * Map containing avatar url strings.
     */
    private static Map sAvatarMap = new HashMap<Integer, String>();

    /**
     * View holder.
     */
    static class ViewHolder {
        TextView message;
        TextView amount;
        TextView timestamp;
        ImageView avatarImage;
        ImageView badgeImage;
        int userId;
        AsyncTask<?, ?, ?> task;
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
        prepareHolder(holder, item);
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

    /**
     * Prepares a ViewHolder by adding data from the {@Feeditem}.
     *
     * @param holder The ViewHolder to prepare.
     * @param item   The item to get data from.
     */
    private void prepareHolder(ViewHolder holder, FeedItem item) {
        if (holder.task != null) {
            holder.task.cancel(false);
        }
        holder.message.setText(Html.fromHtml(item.getMessage()));
        holder.amount.setText(Utils.buildAmountString(item.getAmount()));
        holder.timestamp.setText(item.getTimeStamp());
        holder.badgeImage.setImageResource(getBadgeResId(item.getType()));
        holder.userId = item.getUserId();
        Picasso.with(mContext).load(getBadgeResId(item.getType())).into(holder.badgeImage);

        final int key = holder.userId;
        if (sAvatarMap.containsKey(key)) {
              /* Check map for avatar url. */
            String url = (String) sAvatarMap.get(key);
            Picasso.with(mContext).load(url)
                    .placeholder(R.drawable.list_placeholder).into(holder.avatarImage);
        } else {
            holder.avatarImage.setVisibility(View.INVISIBLE);
            holder.task = new AvatarImageTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, holder);
        }
    }

    private int getBadgeResId(String type) {
        switch (type) {
            case "roundup":
                return R.drawable.rule_roundup;
            default:
                return R.drawable.rule_penalty;
        }
    }

    /**
     * Responsible for fetching and setting  the avatar image for the feed creating user.
     */
    private class AvatarImageTask extends AsyncTask<ViewHolder, Void, String> {
        private ViewHolder mHolder;

        @Override
        protected String doInBackground(ViewHolder... params) {
            if (isCancelled()) {
                return null;
            }
            mHolder = params[0];
            String url = null;
            try {
                url = new AvatarUrlFetcher().fetch(mHolder.userId);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return url;
        }

        @Override
        protected void onPostExecute(String url) {
            mHolder.avatarImage.setVisibility(View.VISIBLE);
            /* Store the url in the map for future use. */
            sAvatarMap.put(mHolder.userId, url);
            Picasso.with(mContext).load(url)
                    .placeholder(R.drawable.list_placeholder).into(mHolder.avatarImage);
        }
    }

    /**
     * Responsible for fetching a rule for the feed id.
     */
    private class SavingsRuleTask extends AsyncTask<ViewHolder, Void, String> {
        private ViewHolder mHolder;

        @Override
        protected String doInBackground(ViewHolder... params) {
            mHolder = params[0];
            String url = null;
            try {
                url = new AvatarUrlFetcher().fetch(mHolder.userId);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return url;
        }

        @Override
        protected void onPostExecute(String url) {
            mHolder.avatarImage.setVisibility(View.VISIBLE);
            /* Store the url in the map for future use. */
            sAvatarMap.put(mHolder.userId, url);
            Picasso.with(mContext).load(url)
                    .placeholder(R.drawable.list_placeholder).into(mHolder.avatarImage);
        }
    }
}