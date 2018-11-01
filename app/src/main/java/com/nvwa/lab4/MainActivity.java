package com.nvwa.lab4;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    static public ArrayList<Task> myTasks;
    static {
        myTasks = new ArrayList<Task>();
        myTasks.add( new Task("Task #1", "Create a todo list") );
        myTasks.add( new Task("Task #2", "Write two tasks") );
        myTasks.add( new Task("Task #3", "Realize that you have completed 3 tasks") );
        myTasks.add( new Task("Task #4", "Have some rest") );
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
