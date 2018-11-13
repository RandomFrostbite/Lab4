package com.nvwa.lab4;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

import static com.nvwa.lab4.MainActivity.DESC;
import static com.nvwa.lab4.MainActivity.NUM_TASKS;
import static com.nvwa.lab4.MainActivity.PIC;
import static com.nvwa.lab4.MainActivity.TASK;
import static com.nvwa.lab4.MainActivity.TASKS_FILE;
import static com.nvwa.lab4.MainActivity.myTasks;

public class TaskInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_info_activity);
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveChanges();
    }

    @Override
    protected void onStop() {
        super.onStop();
        saveChanges();
    }

    public void saveChanges() {
        TaskInfoFragment frag = (TaskInfoFragment)getSupportFragmentManager().findFragmentById(R.id.displayFragment);
        SharedPreferences tasks = getSharedPreferences( TASKS_FILE, MODE_PRIVATE );
        SharedPreferences.Editor editor = tasks.edit();
        editor.clear();
        editor.putInt( NUM_TASKS, myTasks.size() );
        for ( Integer i = 0; i < myTasks.size(); i++ ) {
            if ( frag.mDisplayTask.title.equals(myTasks.get(i).title) && frag.mDisplayTask.desc.equals(myTasks.get(i).desc) ) {
                editor.putString( TASK + i.toString(), frag.mDisplayTask.title );
                editor.putString( DESC + i.toString(), frag.mDisplayTask.desc );
                editor.putString( PIC  + i.toString(), frag.mDisplayTask.picPath );
            } else {
                editor.putString( TASK + i.toString(), myTasks.get(i).title );
                editor.putString( DESC + i.toString(), myTasks.get(i).desc );
                editor.putString( PIC  + i.toString(), myTasks.get(i).picPath );
            }
        }
        editor.apply();
    }
}
