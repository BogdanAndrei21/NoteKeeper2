package com.example.bogdanandrei.notekeeper;

import java.util.ArrayList;
import java.util.List;

public class DataManager
{
    private static DataManager ourInstance = null;

    private List<NoteInfo> mNotes = new ArrayList<>();

    private DataManager()
    {

    }

    public static DataManager getInstance() {
        if(ourInstance == null) {
            ourInstance = new DataManager();
            ourInstance.initializeExampleNotes();
        }
        return ourInstance;
    }

    private void initializeExampleNotes()
    {
        final DataManager dm = getInstance();

        mNotes.add(new NoteInfo(TypeEnum.Hangout,"Boardgameees","Cheama-l la boardgames pe Eva Stefan sambata"));
        mNotes.add(new NoteInfo(TypeEnum.Others,"1 aprilie","Nu uita de 1 aprilie"));
        mNotes.add(new NoteInfo(TypeEnum.Money,"Money is life","Incearca sa nu cheltui prea mult in urmatoarea perioada"));
    }

    public List<NoteInfo> getNotes()
    {
        ArrayList<NoteInfo> notes = new ArrayList<>();
        for(NoteInfo note:mNotes)
        {
                notes.add(note);
        }

        return notes;
    }

    public int createNewNote()
    {
        NoteInfo note = new NoteInfo(null, null, null);
        mNotes.add(note);
        return mNotes.size() - 1;
    }

    public void removeNote(int index)
    {
        mNotes.remove(index);
    }
}
