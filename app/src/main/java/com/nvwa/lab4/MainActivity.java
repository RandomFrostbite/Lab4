package com.nvwa.lab4;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;

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

    public void addClick(View view) {
        String task_title = ( (EditText) findViewById(R.id.taskTitle ) ).getText().toString();
        String task_desc  = ( (EditText) findViewById(R.id.taskDesc  ) ).getText().toString();

        if ( task_desc.isEmpty() && task_title.isEmpty() )
            myTasks.add( new Task( getResources().getString(R.string.defTitle), getResources().getString(R.string.defDesc) ) );
        else {
            if ( task_title.isEmpty() )
                task_title = getResources().getString(R.string.defTitle);
            if ( task_desc.isEmpty() )
                task_desc = getResources().getString(R.string.misDesc);
            myTasks.add( new Task( task_title, task_desc ) );
        }

        TaskListFragment taskFr = (TaskListFragment) getSupportFragmentManager().findFragmentById(R.id.taskFragment);

        ArrayAdapter<Task> taskAdapter = (ArrayAdapter<Task>) taskFr.getListAdapter();

        taskAdapter.notifyDataSetChanged();

        ((EditText) findViewById(R.id.taskTitle)).setText(null);
        ((EditText) findViewById(R.id.taskDesc)).setText(null);

        View focusedView = getCurrentFocus();
        if ( focusedView != null ) {
            InputMethodManager im = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
