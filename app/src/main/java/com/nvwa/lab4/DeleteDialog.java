package com.nvwa.lab4;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class DeleteDialog extends DialogFragment {

    public DeleteDialog() {};

    static DeleteDialog newInstance() {
        return new DeleteDialog();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(getResources().getString(R.string.dialog_title))
                .setPositiveButton(getResources().getString(R.string.dialog_OK), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                })
                .setNegativeButton(getResources().getString(R.string.dialog_Cancel), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        return builder.create();
    }
}
