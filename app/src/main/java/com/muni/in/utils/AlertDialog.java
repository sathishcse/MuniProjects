package com.muni.in.utils;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;

/**
 * Created by sathish on 25/5/18.
 */

public class AlertDialog {
    private Context mContext;
    private android.support.v7.app.AlertDialog.Builder builder;
    public AlertDialog(Context context,String title,String message){
        this.mContext = context;
        builder = new android.support.v7.app.AlertDialog.Builder(mContext);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(false);
        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                //((Activity)mContext).finish();
            }
        });



    }

    public void show(){
        android.support.v7.app.AlertDialog alert = builder.create();
        alert.show();
    }
}
