package com.example.bogdanandrei.notekeeper;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.util.Collection;
import java.util.Iterator;

public final class NoteInfo implements Parcelable
{
    private TypeEnum mType;
    private String mTitle;
    private String mText;

    public NoteInfo(TypeEnum Type, String title, String text)
    {
        mType = Type;
        mTitle = title;
        mText = text;
    }

    private NoteInfo(Parcel source)
    {
        mType =  TypeEnum.valueOf(source.readString().toString());
        mTitle = source.readString();
        mText = source.readString();
    }

    public TypeEnum getType() {
        return mType;
    }

    public void setType(TypeEnum Type) {
        mType = Type;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getText() {
        return mText;
    }

    public void setText(String text) {
        mText = text;
    }

    private String getCompareKey()
    {
       return mType + "\n" + mTitle + " | " + mText;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass())
            return false;

        NoteInfo that = (NoteInfo) o;

        return getCompareKey().equals(that.getCompareKey());
    }

    @Override
    public int hashCode() {
        return getCompareKey().hashCode();
    }

    @Override
    public String toString() {
        return getCompareKey();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int i)
    {
        dest.writeString(mType.toString());
        dest.writeString(mTitle);
        dest.writeString(mText);
    }

    public static final Parcelable.Creator<NoteInfo> CREATOR = new Parcelable.Creator<NoteInfo>()
    {
        @Override
        public NoteInfo createFromParcel(Parcel parcel) {
            return new NoteInfo(parcel);
        }

        @Override
        public NoteInfo[] newArray(int size) {
            return new NoteInfo[size];
        }
    };
}
