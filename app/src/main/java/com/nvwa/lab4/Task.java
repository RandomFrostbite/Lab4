package com.nvwa.lab4;

import android.os.Parcel;
import android.os.Parcelable;

public class Task implements Parcelable {
    public String title, desc, picPath;

    Task() {

    }

    Task( String t, String d ) {
        title = t;
        desc = d;
        picPath = null;
    }

    Task( String t, String d, String pic ) {
        title = t;
        desc = d;
        picPath = pic;
    }

    protected Task( Parcel in ) {
        title = in.readString();
        desc = in.readString();
        picPath = in.readString();
    }

    void addPicPath( String pPath ) {
        picPath = pPath;
    }

    public static final Creator<Task> CREATOR = new Creator<Task>() {
        @Override
        public Task createFromParcel( Parcel in ) {
            return new Task(in);
        }

        @Override
        public Task[] newArray( int size ) {
            return new Task[size];
        }
    };

    @Override
    public String toString() {
        return title;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel( Parcel dest, int flags ) {
        dest.writeString(title);
        dest.writeString(desc);
        dest.writeString(picPath);
    }
}