package com.example.youtubeapimvvm.utils;

import android.content.Context;
import android.content.DialogInterface;

import com.example.youtubeapimvvm.R;

import androidx.appcompat.app.AlertDialog;


public class AlertMessage {
    // Context
    Context _context;
    // Constructor
    public AlertMessage(Context context){
        this._context = context;

    }
    public  static void alert(final Context context,String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message)
                .setCancelable(false)
                .setNegativeButton(context.getApplicationContext().getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
