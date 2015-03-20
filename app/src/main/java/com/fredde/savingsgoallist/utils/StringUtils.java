package com.fredde.savingsgoallist.utils;

import android.content.Context;

import com.fredde.savingsgoallist.R;
import com.fredde.savingsgoallist.data.GoalItem;

import java.text.DecimalFormat;

/**
 * Strings utils
 */
public class StringUtils {

    public static String buildProgressString(Context context, GoalItem item) {

        DecimalFormat df = new DecimalFormat("###");
        return df.format(item.getCurrentBalance()) + context.getResources().getString(R.string.text_saved_of) + df.format(item.getSavingsTarget());
    }
}
