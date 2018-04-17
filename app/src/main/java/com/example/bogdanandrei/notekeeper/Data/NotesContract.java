package com.example.bogdanandrei.notekeeper.Data;

import android.provider.BaseColumns;

public final class NotesContract
{
    public static final class NotesEntry implements BaseColumns {
        // Table name
        public static final String TABLE_NAME = "Notes";
        //column (field) names
        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_TITLE = "TITLE";
        public static final String COLUMN_TEXT = "TEXT";
        public static final String COLUMN_TYPE = "TYPE";
    }
}
