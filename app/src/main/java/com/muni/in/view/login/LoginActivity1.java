package com.muni.in.view.login;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.muni.in.R;
import com.muni.in.data.SharedPreference;
import com.muni.in.model.auth.AuthRequest;
import com.muni.in.model.restaurant.Restaurant;
import com.muni.in.service.Client;
import com.muni.in.service.listener.ResponseListener;
import com.muni.in.view.home.HomeActivity;

import java.util.List;

public class LoginActivity1 extends AppCompatActivity implements ResponseListener{

    private EditText edit_user;
    private EditText edit_pwd;
    private Button but_login;
    private String struser,strpwd;
    private Context context;
    private ProgressDialog dialog;
    private SharedPreference sharedPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login1);

        context = LoginActivity1.this;
        sharedPreference = new SharedPreference(context);
        edit_user = (EditText)findViewById(R.id.edit_username);
        edit_pwd = (EditText)findViewById(R.id.edit_pwd);
        but_login = (Button)findViewById(R.id.but_login);
        but_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                struser = edit_user.getText().toString().trim();
                strpwd = edit_pwd.getText().toString().trim();
                if(struser.isEmpty()){
                    Toast.makeText(context,"Enter Username",Toast.LENGTH_SHORT).show();
                }else if(strpwd.isEmpty()){
                    Toast.makeText(context,"Enter Password",Toast.LENGTH_SHORT).show();
                }else{
                    dialog = new ProgressDialog(context);
                    dialog.setMessage("please wait");
                    dialog.show();
                    new Client(context,LoginActivity1.this).authentication(new AuthRequest(struser,strpwd));
                }
            }
        });
    }


    @Override
    public void onSuccess(String listener, String response) {
        dialog.dismiss();
        startActivity(new Intent(this, HomeActivity.class));
        finish();
    }

    @Override
    public void onSuccess(String listener, List<Restaurant> response) {

    }

    @Override
    public void onFailure(String message) {
        dialog.dismiss();

    }


}
