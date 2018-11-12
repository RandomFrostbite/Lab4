package com.nvwa.lab4;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.support.v4.app.Fragment;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements DeleteDialog.NoticeDialogListener {

    private int listItemPosition = -1;
    public static final String taskExtra = "Task";
    static public ArrayList<Task> myTasks;
    static {
        myTasks = new ArrayList<Task>();
        myTasks.add( new Task("Task1", "Create a todo list" ) );
        myTasks.add( new Task("Task2", "Write two tasks" ) );
        myTasks.add( new Task("Task3", "Realize that you have completed 3 tasks" ) );
        myTasks.add( new Task("Task4", "Have some rest" ) );
    }
    public static final String TASKS_FILE = "com.nvwa.lab4.TasksFile";
    public static final String NUM_TASKS = "NumOfTasks";
    public static final String TASK = "task_";
    public static final String DESC = "desc_";
    public static final String PIC = "pic_";

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        restoreTasks();

        final TaskListFragment taskFr = (TaskListFragment) getSupportFragmentManager().findFragmentById( R.id.taskFragment );
        final ArrayAdapter<Task> taskAdapter = (ArrayAdapter<Task>) taskFr.getListAdapter();

        taskFr.getListView().setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick( AdapterView<?> parent, View view, int position, long id ) {
                Toast.makeText( getApplicationContext(), "Item selected", Toast.LENGTH_LONG ).show();
                if ( getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ) {
                    TaskInfoFragment frag = (TaskInfoFragment)getSupportFragmentManager().findFragmentById(R.id.displayFragment);
                    frag.displayTask( (Task)parent.getItemAtPosition(position) );
                } else
                    startSecondActivity( parent, position );
            }
        });

        taskFr.getListView().setOnItemLongClickListener( new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick( AdapterView<?> parent, View view, int position, long id ) {
                Toast.makeText( getApplicationContext(),"Item long clicked.", Toast.LENGTH_LONG ).show();
                DialogFragment newFragment = DeleteDialog.newInstance();
                newFragment.show( getSupportFragmentManager(), "DeleteDialogTag" );
                listItemPosition = position;
                return true;
            }
        });
    }

    @Override
    protected void  onDestroy() {
        super.onDestroy();
        if ( !saveTasks() ) {
            Toast.makeText(getApplicationContext(), "Save failed!", Toast.LENGTH_LONG).show();
        }
        saveTasksToFile();
    }

    private void restoreTasks() {
        SharedPreferences tasks = getSharedPreferences( TASKS_FILE, MODE_PRIVATE );
        int numOfTasks = tasks.getInt( "NumOfTasks", 0 );
        if ( numOfTasks != 0 ) {
            myTasks.clear();
            for ( Integer i = 0; i < numOfTasks; i++ ) {
                String title = tasks.getString( TASK + i.toString(), "0" );
                String desc  = tasks.getString( DESC + i.toString(), "0" );
                String picPath = tasks.getString( PIC + i.toString(), "" );
                Task tmp = new Task( title, desc, picPath );
                myTasks.add(tmp);
            }
        }
        //restoreTasksFromFile();
    }


    private void saveTasksToFile() {
        String filename = "myTasks.txt";
        FileOutputStream outputStream;

        try {
           outputStream = openFileOutput( filename, Context.MODE_PRIVATE );
           BufferedWriter writer = new BufferedWriter( new FileWriter( outputStream.getFD() ) );
            String delim = ":";

           for ( Integer i = 0; i < myTasks.size(); i++ ) {
               Task tmp = myTasks.get(i);
               String line = tmp.title + delim + tmp.desc + delim + tmp.picPath;
               writer.write( line );
               writer.newLine();
           }
           writer.close();
        } catch ( IOException ex ) {
            ex.printStackTrace();
        }
    }

    private void startSecondActivity( AdapterView<?> parent, int position ) {
        Intent intent = new Intent(this, TaskInfoActivity.class );
        Task tmp = (Task)parent.getItemAtPosition(position);
        intent.putExtra( taskExtra, tmp );
        startActivity(intent);
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

        ((EditText) findViewById( R.id.taskTitle ) ).setText(null);
        ((EditText) findViewById( R.id.taskDesc  ) ).setText(null);

        View focusedView = getCurrentFocus();
        if ( focusedView != null ) {
            InputMethodManager im = (InputMethodManager)getSystemService( Context.INPUT_METHOD_SERVICE );
            im.hideSoftInputFromWindow( view.getWindowToken(), 0 );
        }
    }

    @Override
    public void onDialogPositiveClick( DialogFragment dialog ) {
        if (listItemPosition != -1) {
            myTasks.remove(listItemPosition);
            TaskListFragment taskFr = (TaskListFragment)getSupportFragmentManager().findFragmentById(R.id.taskFragment);
            ArrayAdapter<Task> taskAdapter = (ArrayAdapter<Task>) taskFr.getListAdapter();
            taskAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onDialogNegativeClick( DialogFragment dialog ) {
        View v = findViewById( R.id.addBtn );
        Snackbar.make( v,"Delete canceled", Snackbar.LENGTH_LONG).setAction("Retry?", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment newFragment = DeleteDialog.newInstance();
                newFragment.show( getSupportFragmentManager(), "DeleteDialogTag" );
            }
        }).show();
    }

    private boolean saveTasks() {
        SharedPreferences tasks = getSharedPreferences( TASKS_FILE, MODE_PRIVATE );
        SharedPreferences.Editor editor = tasks.edit();
        editor.clear();
        editor.putInt( NUM_TASKS, myTasks.size() );
        for ( Integer i = 0; i < myTasks.size(); i++ ) {
            editor.putString( TASK + i.toString(), myTasks.get(i).title );
            editor.putString( DESC + i.toString(), myTasks.get(i).desc );
            editor.putString( PIC  + i.toString(), myTasks.get(i).picPath );
        }

        return editor.commit();
    }
}
