package com.rosshambrick.android.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class DialogFactory {
    public static DialogFragment createOkOnly(final Context context, final int title, final int message) {
        return new DialogFragment(){
            @Override
            public Dialog onCreateDialog(Bundle savedInstanceState) {
                return new AlertDialog.Builder(context)
                        .setTitle(title)
                        .setMessage(message)
                        .setPositiveButton(android.R.string.ok, null)
                        .create();
            }
        };
    }

    public static DialogFragment createOkCancel(final Context context, final int title, final int message, final DialogInterface.OnClickListener okListener, final DialogInterface.OnClickListener cancelListener) {
        return new DialogFragment(){
            @Override
            public Dialog onCreateDialog(Bundle savedInstanceState) {
                return new AlertDialog.Builder(context)
                        .setTitle(title)
                        .setMessage(message)
                        .setPositiveButton(android.R.string.ok, okListener)
                        .setNegativeButton(android.R.string.cancel, cancelListener)
                        .create();
            }
        };
    }
}