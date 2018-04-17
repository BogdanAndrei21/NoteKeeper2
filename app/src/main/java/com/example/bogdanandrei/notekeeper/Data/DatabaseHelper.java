package com.example.bogdanandrei.notekeeper.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.bogdanandrei.notekeeper.Data.NotesContract.NotesEntry;

public class DatabaseHelper extends SQLiteOpenHelper
{
    //Constants for db name and version
    private static final String DATABASE_NAME = "Notes.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NOTES_CREATE =
            "CREATE TABLE " + NotesEntry.TABLE_NAME + " (" +
                    NotesEntry._ID + " INTEGER PRIMARY KEY, " +
                    NotesEntry.COLUMN_TITLE + " TEXT, " +
                    NotesEntry.COLUMN_TEXT + " TEXT default CURRENT_TIMESTAMP, " +
                    NotesEntry.COLUMN_TYPE + " TEXT )";


    public DatabaseHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(TABLE_NOTES_CREATE);
        //seed(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + NotesEntry.TABLE_NAME);
        onCreate(db);
    }
}
