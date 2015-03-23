package com.fredde.savingsgoallist.db;

/**
 * Created by Fredde on 22/03/2015.
 */
public class PostOM {
    private static final String TAG = "PostORM";

    private static final String TABLE_NAME = "post";

    private static final String COMMA_SEP = ", ";

    private static final String COLUMN_ID_TYPE = "INTEGER PRIMARY KEY";
    private static final String COLUMN_ID = "id";

    private static final String COLUMN_TITLE_TYPE = "TEXT";
    private static final String COLUMN_TITLE = "title";

    private static final String COLUMN_PREVIEW_TYPE = "TEXT";
    private static final String COLUMN_PREVIEW = "preview";

    private static final String COLUMN_BODY_TYPE = "TEXT";
    private static final String COLUMN_BODY = "body";

    private static final String COLUMN_URL_TYPE = "TEXT";
    private static final String COLUMN_URL = "url";

    private static final String COLUMN_DATE_TYPE = "TEXT";
    private static final String COLUMN_DATE = "pubdate";


    public static final String SQL_CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_ID + " " + COLUMN_ID_TYPE + COMMA_SEP +
                    COLUMN_TITLE + " " + COLUMN_TITLE_TYPE + COMMA_SEP +
                    COLUMN_PREVIEW + " " + COLUMN_PREVIEW_TYPE + COMMA_SEP +
                    COLUMN_BODY + " " + COLUMN_BODY_TYPE + COMMA_SEP +
                    COLUMN_URL + " " + COLUMN_URL_TYPE + COMMA_SEP +
                    COLUMN_DATE + " " + COLUMN_DATE_TYPE +
                    ")";

    public static final String SQL_DROP_TABLE =
            "DROP TABLE IF EXISTS " + TABLE_NAME;
}