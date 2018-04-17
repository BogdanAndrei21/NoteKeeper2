package com.example.bogdanandrei.notekeeper;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.bogdanandrei.notekeeper.Data.DatabaseHelper;
import com.example.bogdanandrei.notekeeper.Data.NotesContract;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class NoteListActivity extends AppCompatActivity {

    private ArrayAdapter<NoteInfo> mAdapterNotes;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_list);

        //db:
        File dbFile = this.getDatabasePath("Notes.db");
        if(!dbFile.exists())
        {
            DatabaseHelper helper = new DatabaseHelper(this);
            SQLiteDatabase db = helper.getReadableDatabase();
            CreateNote();
        }
        //CreateNote();
        //readData();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                startActivity(new Intent(NoteListActivity.this, NoteActivity.class));
            }
        });

        initializeDisplayContent();
    }

    private void initializeDisplayContent()
    {
        final ListView listNotes = (ListView) findViewById(R.id.list_notes);

        List<NoteInfo> notes = new ArrayList<>();//DataManager.getInstance().getNotes();
        DatabaseHelper helper = new DatabaseHelper(this);
        SQLiteDatabase db = helper.getReadableDatabase();
        String[] projection = { NotesContract.NotesEntry.COLUMN_TITLE,
                NotesContract.NotesEntry.COLUMN_TEXT,
                NotesContract.NotesEntry.COLUMN_TYPE };

        cursor = db.query(NotesContract.NotesEntry.TABLE_NAME, projection, null, null, null, null, null);

        while(cursor.moveToNext())
        {
            NoteInfo noteInfo = new NoteInfo(TypeEnum.HomeStuffs, cursor.getString(0), cursor.getString(1));
            notes.add(noteInfo);
        }
        //cursor.close();

        mAdapterNotes = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, notes);

        listNotes.setAdapter(mAdapterNotes);

        listNotes.setOnItemClickListener(new OnItemClickListener()
        {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                Intent intent = new Intent(NoteListActivity.this, NoteActivity.class);
                intent.putExtra(NoteActivity.NOTE_POSITION, i);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        mAdapterNotes.notifyDataSetChanged(); //pt save
    }

    private void CreateNote()
    {

        DatabaseHelper helper = new DatabaseHelper(this);
        SQLiteDatabase db = helper.getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put(NotesContract.NotesEntry.COLUMN_TITLE, "Titlu22");
        values.put(NotesContract.NotesEntry.COLUMN_TEXT, "TEEEEEEXT 22");
        values.put(NotesContract.NotesEntry.COLUMN_TYPE, "TIP 22");
        long note_id = db.insert(NotesContract.NotesEntry.TABLE_NAME, null, values);
    }

    private void readData()
    {
        /*
        DatabaseHelper helper = new DatabaseHelper(this);
        SQLiteDatabase db = helper.getReadableDatabase();
        String[] projection = { NotesContract.NotesEntry.COLUMN_TITLE,
                NotesContract.NotesEntry.COLUMN_TEXT,
                NotesContract.NotesEntry.COLUMN_TYPE };

        Cursor c = db.query(NotesContract.NotesEntry.TABLE_NAME, projection, null, null, null, null, null);
        //int i = c.getCount();
        //Log.d("Record count", String.valueOf(i));

        String rowContent = "";
        while(c.moveToNext())
        {
            for(int i = 0; i < 3; i++)
            {
                NoteInfo noteInfo = new NoteInfo(TypeEnum.HomeStuffs, c.getString(0), c.getString(1));
            }
        }
        */
    }



}
