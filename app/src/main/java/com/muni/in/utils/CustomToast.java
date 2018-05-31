package com.muni.in.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by sathish on 7/5/18.
 */

public class CustomToast {


    CustomToast(Context cxt,String message,int duration){
        Toast t = Toast.makeText(cxt,message,duration);
        t.show();

    }
}
