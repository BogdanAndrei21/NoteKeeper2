package com.example.bogdanandrei.notekeeper;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.bogdanandrei.notekeeper.Data.DatabaseHelper;
import com.example.bogdanandrei.notekeeper.Data.NotesContract;

import java.util.Arrays;
import java.util.List;

public class NoteActivity extends AppCompatActivity {

    public static final String NOTE_POSITION = "com.example.bogdanandrei.NOTE_POSITION";
    public static final int POSITION_NOT_SET = -1;
    private NoteInfo mNote;
    private boolean mIsNewNote;
    private Spinner mSpinnerTypes;
    private EditText mTextNoteTitle;
    private EditText mTextNoteText;
    private int mNotePosition;
    private boolean mIsCancelling;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mSpinnerTypes = (Spinner) findViewById(R.id.spinner_Types);


        List<TypeEnum> types = Arrays.asList(TypeEnum.values());

        ArrayAdapter<TypeEnum> adapterTypes = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, types);
        adapterTypes.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerTypes.setAdapter(adapterTypes);

        readDisplayStateValues();

        mTextNoteTitle = (EditText) findViewById(R.id.text_note_title);
        mTextNoteText = (EditText) findViewById(R.id.text_note_text);

        if(!mIsNewNote)
            displayNote(mSpinnerTypes, mTextNoteTitle, mTextNoteText);
    }

    private void displayNote(Spinner spinnerTypes, EditText textNoteTitle, EditText textNoteText)
    {
        List<TypeEnum> types = Arrays.asList(TypeEnum.values()); // get all the values from TypeEnum
        int typeIndex = types.indexOf(mNote.getType());
        spinnerTypes.setSelection(typeIndex);

        textNoteTitle.setText(mNote.getTitle());
        textNoteText.setText(mNote.getText());
    }

    private void readDisplayStateValues()
    {
        Intent intent = getIntent();
        int position = intent.getIntExtra(NOTE_POSITION, POSITION_NOT_SET);
        mIsNewNote = position == POSITION_NOT_SET;

        if(mIsNewNote)
        {
           createNewNote();
        }
        else
        {
            //TODO: aici nu cred ca fac prea bine
            //mNote = DataManager.getInstance().getNotes().get(position);
            DatabaseHelper helper = new DatabaseHelper(this);
            SQLiteDatabase db = helper.getReadableDatabase();
            String[] projection = { NotesContract.NotesEntry.COLUMN_TITLE,
                    NotesContract.NotesEntry.COLUMN_TEXT,
                    NotesContract.NotesEntry.COLUMN_TYPE };
            Cursor cursor = db.query(NotesContract.NotesEntry.TABLE_NAME, projection, null, null, null, null, null);

            int i = 0;
            while(cursor.moveToNext())
            {
                if(i == position)
                {
                    mNote = new NoteInfo(TypeEnum.HomeStuffs, cursor.getString(0), cursor.getString(1));
                    break;
                }
                else
                    i++;
            }
        }

    }

    private void createNewNote()
    {
        DataManager dataManager = DataManager.getInstance();
        mNotePosition = dataManager.createNewNote();
        mNote = dataManager.getNotes().get(mNotePosition);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_note, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_send_mail)
        {
            sendEmail();
            return true;
        }
        else if (id == R.id.action_cancel)
        {
            mIsCancelling = true;
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private void sendEmail()
    {
        TypeEnum type = (TypeEnum) mSpinnerTypes.getSelectedItem();
        String subject = mTextNoteTitle.getText().toString();
        String message = "About '"+type+"': \nDon't forget!!!  "+mTextNoteText.getText().toString();

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("message/rfc2822");
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, message);
        startActivity(intent);
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        if(mIsCancelling)
        {
            if(mIsNewNote)
                DataManager.getInstance().removeNote(mNotePosition);
            else
                storePreviousNoteValues();
        }
        else
        {
            //TODO: save in db
            saveNote();
        }
    }

    private void storePreviousNoteValues()
    {
        //CourseInfo course = DataManager.getInstance().getCourse(mOriginalNoteCourseId);
        //mNote.setCourse(course);
        //mNote.setTitle(mOriginalNoteTitle);
        //mNote.setText(mOriginalNoteText);
    }

    private void saveNote()
    {
        mNote.setType((TypeEnum) mSpinnerTypes.getSelectedItem());
        mNote.setTitle(mTextNoteTitle.getText().toString());
        mNote.setText(mTextNoteText.getText().toString());

    }

}
