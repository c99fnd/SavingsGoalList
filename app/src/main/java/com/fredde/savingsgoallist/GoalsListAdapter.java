package com.fredde.savingsgoallist;

import android.content.Context;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.fredde.savingsgoallist.data.GoalItem;
import com.fredde.savingsgoallist.http.AvatarUrlFetcher;
import com.fredde.savingsgoallist.utils.Utils;
import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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
     * Map containing user avatar urls.
     */
    private Map<Integer, String> sAvatarMap = new ConcurrentHashMap<Integer, String>();

    /**
     * View holder.
     */
    static class ViewHolder {
        TextView title;
        TextView subTitle;
        ImageView imageView;
        GridView avatarGrid;
        ProgressBar progress;

        int[] userIds;
        public AsyncTask<?, ?, ?> task;

        ViewHolder() {
        }

        ViewHolder(ViewHolder h) {
            title = h.title;
            subTitle = h.subTitle;
            imageView = h.imageView;
            avatarGrid = h.avatarGrid;
            progress = h.progress;
            userIds = h.userIds;
            task = h.task;
        }
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

            holder.avatarGrid = (GridView) view.findViewById(R.id.list_item_avatar_grid);
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
    public void setData(GoalItem[] items) {
        mItems = items.clone();
    }

    public GoalItem[] getData() {
        return mItems;
    }

    /**
     * Sets data from GoalItem to HolderView.
     *
     * @param holder The holder to set data to.
     * @param item   The item to get data from.
     */
    private void prepareHolder(ViewHolder holder, GoalItem item) {
        if (holder.task != null) {
            holder.task.cancel(false);
        }
        holder.title.setText(item.getTitle());
        holder.subTitle.setText(Utils.buildListProgressString(mContext, item.getCurrentBalance(), item.getSavingsTarget()));
        holder.progress.setProgress(calculateProgress(holder, item));

        Picasso.with(mContext).load(item.getImageUrl()).placeholder(R.drawable.list_placeholder).into(holder.imageView);
        holder.userIds = item.getConnectedIds();

        holder.task = new AvatarImageTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new ViewHolder(holder));
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

    /**
     * Responsible for fetching and setting  the avatar image for the feed creating user.
     */
    private class AvatarImageTask extends AsyncTask<ViewHolder, Void, String[]> {
        private ViewHolder mHolder;

        @Override
        protected String[] doInBackground(ViewHolder... params) {
            if (isCancelled()) {
                return null;
            }
            mHolder = params[0];

            String[] urls = new String[mHolder.userIds.length];
            try {
                for (int i = 0; i < mHolder.userIds.length; i++) {
                    int id = mHolder.userIds[i];
                    /* Only fetch the url if needed. */
                    if (sAvatarMap.containsKey(id)) {
                        urls[i] = sAvatarMap.get(id);
                    } else {
                        urls[i] = new AvatarUrlFetcher().fetch(mHolder.userIds[i]);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return urls;
        }

        @Override
        protected void onPostExecute(String[] urls) {
            for (int i = 0; i < urls.length; i++) {
                 /* Store the url in the map for future use. */
                sAvatarMap.put(mHolder.userIds[i], urls[i]);
            }

            mHolder.avatarGrid.setNumColumns(urls.length);
            mHolder.avatarGrid.getLayoutParams().width = calculateGridWidth(urls.length);
            mHolder.avatarGrid.getLayoutParams().height = (int) mContext.getResources().getDimension(R.dimen.goal_list_avatar_image_size);

            AvatarGridAdapter adapter = (AvatarGridAdapter) mHolder.avatarGrid.getAdapter();
            if (adapter == null) {
                adapter = new AvatarGridAdapter();
                mHolder.avatarGrid.setAdapter(adapter);
            }
            adapter.setData(urls);
            adapter.notifyDataSetChanged();
        }

        /**
         * Calculates a new grid with based on grid image size and number of images.
         *
         * @param numImages
         * @return
         */
        private int calculateGridWidth(int numImages) {
            int margin = (int) mContext.getResources().getDimension(R.dimen.goal_list_avatar_image_margin);
            int imageSize = (int) mContext.getResources().getDimension(R.dimen.goal_list_avatar_image_size);
            return (imageSize + margin) * numImages;
        }
    }

    private class AvatarGridAdapter extends BaseAdapter {
        private String[] mData;

        public AvatarGridAdapter() {
            mData = new String[0];
        }

        @Override
        public int getCount() {
            return mData.length;
        }

        @Override
        public Object getItem(int position) {
            return mData[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView;
            if (convertView == null) {
                imageView = (ImageView) LayoutInflater.from(mContext).inflate(R.layout.goals_list_avatar_image, parent, false);
            } else {
                imageView = (ImageView) convertView;
            }
            Picasso.with(mContext).load(mData[position])
                    .placeholder(R.drawable.list_placeholder).into(imageView);
            return imageView;
        }

        public void setData(String[] url) {
            mData = url.clone();
        }
    }
}
