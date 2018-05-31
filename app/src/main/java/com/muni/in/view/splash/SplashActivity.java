package com.muni.in.view.splash;

import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.muni.in.R;
import com.muni.in.data.SharedPreference;
import com.muni.in.view.home.HomeActivity;
import com.muni.in.view.login.LoginActivity;

public class SplashActivity extends AppCompatActivity {

    private SharedPreference sharedPreference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         if (Build.VERSION.SDK_INT > 16) {
             requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        setContentView(R.layout.activity_splash);
        sharedPreference = new SharedPreference(this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.e("TAG",sharedPreference.getAccessToken());
                if(sharedPreference.getAccessToken().equalsIgnoreCase("No auth token")) {
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    overridePendingTransition(R.anim.slide_enter, R.anim.slide_exit);
                    finish();
                }else{
                    startActivity(new Intent(SplashActivity.this, HomeActivity.class));
                    overridePendingTransition(R.anim.slide_enter, R.anim.slide_exit);
                    finish();
                }
            }
        },3000);
    }
}
