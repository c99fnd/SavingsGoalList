package com.fredde.savingsgoallist.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;

import com.fredde.savingsgoallist.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;

/**
 * Contains helper functions for managing texts.
 */
public class Utils {

    /**
     * Builds a string with the format $<value> saved of $<value> for use in the details view header.
     *
     * @param context
     * @param current
     * @param target
     * @return
     */
    public static String buildListProgressString(Context context, double current, double target) {
        DecimalFormat df = new DecimalFormat("###");
        return "$" + df.format(current) + context.getResources().
                getString(R.string.text_saved_of) + "$" + df.format(target);
    }

    /**
     * Builds a string with the format $<value> of $<value> for use in the details view header.
     *
     * @param context
     * @param current
     * @param target
     * @return
     */
    public static String buildHeaderProgressString(Context context, double current, double target) {
        DecimalFormat df = new DecimalFormat("###");
        return "$" + df.format(current) + context.getResources().
                getString(R.string.text_of) + "$" + df.format(target);
    }


    public static String buildAmountString(double amount) {
        DecimalFormat df = new DecimalFormat("###.##");
        return "$" + df.format(amount);
    }

    /**
     * Sets a given typeface to given textviews.
     *
     * @param tf     The typefont to set.
     * @param params Textviews to set typeface to.
     */
    public static void setLayoutFont(Typeface tf, TextView... params) {
        for (TextView tv : params) {
            tv.setTypeface(tf);
        }
    }

    /**
     * Reads an InputStream to a String.
     *
     * @param stream The stream to read.
     * @return String containing data from stream.
     * @throws java.io.IOException
     */
    public static String InputStreamToString(InputStream stream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
        StringBuilder builder = new StringBuilder();
        for (String line; (line = reader.readLine()) != null; ) {
            builder.append(line).append("\n");
        }
        return builder.toString();
    }
}
