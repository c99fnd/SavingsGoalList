package com.fredde.savingsgoallist.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;

import com.fredde.savingsgoallist.R;
import com.fredde.savingsgoallist.data.GoalItem;

import java.text.DecimalFormat;

/**
 * Contains helper functions for managing texts.
 */
public class TextUtils {

    public static String buildListProgressString(Context context, GoalItem item) {
        DecimalFormat df = new DecimalFormat("###");
        return "$" + df.format(item.getCurrentBalance()) + context.getResources().
                getString(R.string.text_saved_of) + "$" + df.format(item.getSavingsTarget());
    }

    public static String buildHeaderProgressString(Context context, GoalItem item) {
        DecimalFormat df = new DecimalFormat("###");
        return "$" + df.format(item.getCurrentBalance()) + context.getResources().
                getString(R.string.text_of) + "$" + df.format(item.getSavingsTarget());
    }

    public static void setLayoutFont(Typeface tf, TextView... params) {
        for (TextView tv : params) {
            tv.setTypeface(tf);
        }
    }
}
