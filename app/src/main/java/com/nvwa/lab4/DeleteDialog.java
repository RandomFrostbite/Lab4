package com.nvwa.lab4;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class DeleteDialog extends DialogFragment {

    public interface NoticeDialogListener {
        public void onDialogPositiveClick( DialogFragment dialog );
        public void onDialogNegativeClick( DialogFragment dialog );
    }

    NoticeDialogListener mListener;

    public DeleteDialog() {}

    static DeleteDialog newInstance() {
        return new DeleteDialog();
    }

    @Override
    public Dialog onCreateDialog( Bundle savedInstanceState ) {
        AlertDialog.Builder builder = new AlertDialog.Builder( getActivity() );
        builder.setMessage( getResources().getString( R.string.dialog_title ) )
                .setPositiveButton( getResources().getString( R.string.dialog_OK ), new DialogInterface.OnClickListener() {
                    public void onClick( DialogInterface dialog, int id ) {
                        mListener.onDialogPositiveClick( DeleteDialog.this );
                    }
                })
                .setNegativeButton( getResources().getString( R.string.dialog_Cancel ), new DialogInterface.OnClickListener() {
                    public void onClick( DialogInterface dialog, int id ) {
                        mListener.onDialogNegativeClick( DeleteDialog.this );
                    }
                });
        return builder.create();
    }
    /*
    @Override
    @TargetApi(Build.VERSION_CODES.LOLLIPOP_MR1)
    public void onAttach(Activity activity) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP_MR1) {
            super.onAttach(activity);
            try {
                mListener = (NoticeDialogListener) activity;
            } catch (ClassCastException e){
                throw new ClassCastException( activity.toString() + " must implement NoticeDialogListener");
            }
        }
    }
    */
    @Override
    public void onAttach( Context context ) {
        super.onAttach(context);
        Activity activity = getActivity();
        try {
            mListener = (NoticeDialogListener) activity;
        } catch ( ClassCastException e ){
            throw new ClassCastException( activity.toString() + " must implement NoticeDialogListener" );
        }
    }

}
