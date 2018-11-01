package com.nvwa.lab4;

public class Task {
    public String title, desc, picPath;

    Task() {

    }

    Task( String t, String d ) {
        title = t;
        desc = d;
        picPath = null;
    }

    Task(String t, String d, String pic) {
        title = t;
        desc = d;
        picPath = pic;
    }

    @Override
    public String toString() {
        return title;
    }
}