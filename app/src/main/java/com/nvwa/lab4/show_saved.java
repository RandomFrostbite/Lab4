package com.nvwa.lab4;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class show_saved extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_saved);

        TextView txt = (TextView) findViewById(R.id.data);

        String filename = "myTasks.txt";
        FileInputStream inputStream;
        StringBuilder contents = new StringBuilder();
        try {
            inputStream = openFileInput(filename);
            BufferedReader reader = new BufferedReader( new FileReader( inputStream.getFD() ) );
            String line;
            while ( (line = reader.readLine() ) != null ) {
                contents.append(line);
                contents.append("\n");
            }
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        txt.setText(contents);
    }
}
