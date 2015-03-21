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
 * Util class containing various static helper functions.
 */
public class Utils {

    /**
     * Builds a string with the format $<current> saved of $<target>.
     *
     * @param context The context.
     * @param current First value.
     * @param target  Second value.
     * @return The requested string.
     */
    public static String buildListProgressString(Context context, double current, double target) {
        DecimalFormat df = new DecimalFormat("#");
        return "$" + df.format(current) + context.getResources().
                getString(R.string.text_saved_of) + "$" + df.format(target);
    }

    /**
     * Builds a string with the format $<current> of $<target>.
     *
     * @param context The context.
     * @param current First value.
     * @param target  Second value.
     * @return The requested string.
     */
    public static String buildHeaderProgressString(Context context, double current, double target) {
        DecimalFormat df = new DecimalFormat("#");
        return "$" + df.format(current) + context.getResources().
                getString(R.string.text_of) + "$" + df.format(target);
    }

    /**
     * Builds a string with the format $<value>.
     *
     * @param value The value to create string from
     * @return The requested string
     */
    public static String buildAmountString(double value) {
        DecimalFormat df = new DecimalFormat("#.##");
        return "$" + df.format(value);
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
