package com.nvwa.lab4;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class TaskInfoFragment extends Fragment implements View.OnClickListener {

    public static final int REQUEST_IMAGE_CAPTURE = 1;
    private String mCurrentPhotoPath;
    private Task mDisplayTask;

    public TaskInfoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated( Bundle savedInstanceState ) {
        super.onActivityCreated(savedInstanceState);

        Intent intent = getActivity().getIntent();

        Task receivedTask = intent.getParcelableExtra( MainActivity.taskExtra );
        if (receivedTask != null)
            displayTask(receivedTask);

        ( (Button) getActivity().findViewById(R.id.takePhoto) ).setOnClickListener(this);
    }


    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState ) {
        // Inflate the layout for this fragment
        return inflater.inflate( R.layout.fragment_task_info, container, false );
    }

    public void displayTask( Task task ) {
        View displayedTaskView = getActivity().findViewById( R.id.displayFragment );
        displayedTaskView.setVisibility( View.VISIBLE );
        ((TextView)getActivity().findViewById( R.id.titleTextView ) ).setText( task.title );
        ((TextView)getActivity().findViewById( R.id.descTextView  ) ).setText( task.desc  );
        ImageView taskImage = (ImageView) getActivity().findViewById( R.id.taskPhoto );
        if ( task.picPath != null && !task.picPath.isEmpty() ) {
            Bitmap imageBitmap = PicUtils.decodePic( task.picPath, 10 );
            taskImage.setImageBitmap( imageBitmap );
        } else {
            taskImage.setImageBitmap( null );
        }
        mDisplayTask = task;
    }

    @Override
    public void onClick( View v ) {
        Intent takePictureIntent = new Intent( MediaStore.ACTION_IMAGE_CAPTURE );
        if (takePictureIntent.resolveActivity( getActivity().getPackageManager() ) != null ) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch(IOException ex) {

            }

            if ( photoFile != null ) {
                Uri photoUri = FileProvider.getUriForFile( getActivity(), "com.nvwa.lab4.fileprovider", photoFile );
                takePictureIntent.putExtra( MediaStore.EXTRA_OUTPUT, photoUri );
                startActivityForResult( takePictureIntent, REQUEST_IMAGE_CAPTURE );
            }
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat( "yyyyMMdd_HHmmss" ).format( new Date() );
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir( Environment.DIRECTORY_PICTURES );
        File image = File.createTempFile( imageFileName, ".jpg", storageDir );
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    @Override
    public void onActivityResult( int requestCode, int resultCode, Intent data ) {
        if ( requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK ) {
            mDisplayTask.addPicPath( mCurrentPhotoPath );
            ImageView taskImage = (ImageView) getActivity().findViewById( R.id.taskPhoto );
            Bitmap imageBitmap = PicUtils.decodePic( mDisplayTask.picPath, taskImage.getWidth(), taskImage.getHeight() );
            taskImage.setImageBitmap( imageBitmap );
        }
    }
}
