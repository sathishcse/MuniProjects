package com.muni.in.data;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by sathish on 5/5/2018.
 */

public class SharedPreference {
    public Context mContext;
    public final String MyPREFERENCES = "MyPrefs" ;
    public SharedPreferences sharedPref;

    public SharedPreference(Context cxt){
        this.mContext = cxt;
        sharedPref = mContext.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
    }
    public void setLoginDetails(String token,int userid){
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("TOKEN", token);
        editor.putInt("USER_ID", userid);
        editor.commit();
    }
    public String getAccessToken(){
        String token = sharedPref.getString("TOKEN", "No auth token");
        return token;
    }

    public int getUserId(){
        int userid = sharedPref.getInt("USER_ID",0);
        return userid;
    }

    public boolean clear(){
        sharedPref = mContext.getSharedPreferences(MyPREFERENCES,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.clear();
        editor.commit();
        return true;
    }
}
