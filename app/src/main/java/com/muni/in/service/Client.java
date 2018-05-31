package com.muni.in.service;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.muni.in.R;
import com.muni.in.model.auth.AuthRequest;
import com.muni.in.model.auth.AuthResponse;
import com.muni.in.model.restaurant.Restaurant;
import com.muni.in.model.restaurant.requestRestaurant;
import com.muni.in.service.listener.ResponseListener;
import com.muni.in.utils.AlertDialog;
import com.muni.in.utils.Connectivity;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import com.muni.in.data.SharedPreference;
import com.muni.in.view.home.HomeActivity;
import com.muni.in.view.login.LoginActivity;


public class Client {

    private final String TAG = "MUNI-APP";
    private final String BASE_URL = "https://muniapi.herokuapp.com/api/";
    private Endpoint endpoint;
    private boolean isNetworkAvailable;
    private Context context;
    private ResponseListener responseListener;
    private SharedPreference sharedPreferences;
    private ProgressDialog progressDialog;

    public Client(Context context, ResponseListener listener) {
        this.context = context;
        this.isNetworkAvailable = Connectivity.isConnected(context);
        this.responseListener = listener;
        this.sharedPreferences = new SharedPreference(context);
        progressDialog = new ProgressDialog(context);
        progressDialog.setProgressStyle(R.style.spinner_style);
        progressDialog.setMessage("loading ...");
        progressDialog.setCancelable(false);
        initRetro(BASE_URL);
    }

    private void initRetro(String baseUrl) {
        Gson gson = new GsonBuilder()
                .create();


        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(1, TimeUnit.MINUTES)
                .addInterceptor(interceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();

        endpoint = retrofit.create(Endpoint.class);
    }

    public void authentication(AuthRequest authRequest) {
        if (isNetworkAvailable) {
            progressDialog.show();
            Call<AuthResponse> authResponse = endpoint.authenticate(authRequest);
            authResponse.enqueue(new Callback<AuthResponse>() {
                @Override
                public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                    Gson gson = new Gson();
                    progressDialog.dismiss();
                    if (response.code() == 200) {
                        sharedPreferences.setLoginDetails(response.body().getAccesstoken(),
                                response.body().getUserId());
                        responseListener.onSuccess("login", gson.toJson(response.body()));
                    } else if (response.code() == 401) {
                        //sharedPreferences.clear();
                        responseListener.onSuccess("login Failed", gson.toJson(response.body()));
                    } else {
                        responseListener.onFailure("Login Failed");
                    }
                }

                @Override
                public void onFailure(Call<AuthResponse> call, Throwable t) {
                    Log.e("Failed", "" + t);
                    progressDialog.dismiss();
                }
            });
        } else {
            Log.d("Error", "Network error");
            progressDialog.dismiss();
            AlertDialog dialog = new AlertDialog(context,"Network issue",
                    "Check your internet connection");
            dialog.show();
            //((Activity)context).finish();
        }
    }

    public void getRestaurants(String accessTocken) {

        if (isNetworkAvailable) {
            progressDialog.show();
            final Call<List<Restaurant>> restaurantResponse = endpoint.getRestaurants(accessTocken);
            restaurantResponse.enqueue(new Callback<List<Restaurant>>() {
                @Override
                public void onResponse(Call<List<Restaurant>> call, Response<List<Restaurant>> response) {
                    Gson gson = new Gson();
                    progressDialog.dismiss();
                    if (response.code() == 200) {
                        responseListener.onSuccess("restaurantList", response.body());
                    } else if (response.code() == 401) {
                        sharedPreferences.clear();
                        Intent i = new Intent(context, LoginActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        context.startActivity(i);
                        ((Activity) context).overridePendingTransition(R.anim.slide_enter, R.anim.slide_exit);
                    } else {
                        responseListener.onFailure("Error");
                    }
                }

                @Override
                public void onFailure(Call<List<Restaurant>> call, Throwable t) {
                    Log.d("Error", "throwable" + t);
                    progressDialog.dismiss();
                    AlertDialog dialog = new AlertDialog(context,"Network issue",
                            "Check your internet connection");
                    dialog.show();
                    //((Activity)context).finish();
                }
            });
        } else {
            Log.d("Error", "Network error");
            AlertDialog dialog = new AlertDialog(context,"Network issue",
                    "Check your internet connection");
            dialog.show();
            //((Activity)context).finish();
        }
    }

    public void addRestaurant(String accesstoken, requestRestaurant request) {
        if (isNetworkAvailable) {
            progressDialog.show();
            Call<JsonElement> reqRestaurant = endpoint.addRestaurant(accesstoken, request);
            reqRestaurant.enqueue(new Callback<JsonElement>() {
                @Override
                public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                    progressDialog.dismiss();
                    if (response.code() == 200) {
                        Log.d("body",""+ response.body().toString());
                        responseListener.onSuccess("restaurant added",response.body().toString());
                    } else if (response.code() == 401) {
                        sharedPreferences.clear();
                        Intent i = new Intent(context, LoginActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        context.startActivity(i);
                        ((Activity) context).overridePendingTransition(R.anim.slide_enter, R.anim.slide_exit);
                    } else {
                        responseListener.onFailure("Error");
                    }
                }

                @Override
                public void onFailure(Call<JsonElement> call, Throwable t) {
                    progressDialog.dismiss();
                }
            });
        }else{
            AlertDialog dialog = new AlertDialog(context,"Network issue",
                    "Check your internet connection");
            dialog.show();
        }
    }


    /*public void getRestaurant(String accesstoken,int id){
        if(isNetworkAvailable){
            progressDialog.show();
            Call<Restaurant> resdata = endpoint.getRestaurant(accesstoken,id);
            resdata.enqueue(new Callback<Restaurant>() {
                @Override
                public void onResponse(Call<Restaurant> call, Response<Restaurant> response) {
                    progressDialog.dismiss();
                    if(response.code() == 200){
                        responseListener.onSuccess("singleRes",response.body().toString());
                    }else if(response.code() == 401){
                        sharedPreferences.clear();
                        Intent i = new Intent(context, LoginActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        context.startActivity(i);
                        ((Activity) context).overridePendingTransition(R.anim.slide_enter, R.anim.slide_exit);
                    } else {
                        responseListener.onFailure("Error");
                    }
                }

                @Override
                public void onFailure(Call<Restaurant> call, Throwable t) {
                    progressDialog.dismiss();
                    t.printStackTrace();
                }
            });

        }else{
            AlertDialog dialog = new AlertDialog(context,"Network issue",
                    "Check your internet connection");
            dialog.show();
        }
    }*/

    public void updateRestaurant(String accesstoken,requestRestaurant request,int id){
        if(isNetworkAvailable){
            progressDialog.show();
            Log.e("TAG","Res id : "+id);
            Call<JsonElement> resdata = endpoint.updateRestaurant(accesstoken,request,id);
            resdata.enqueue(new Callback<JsonElement>() {
                @Override
                public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                    progressDialog.dismiss();
                    if(response.code() == 200){
                        responseListener.onSuccess("updateRes",response.body().toString());
                    }else if(response.code() == 401) {
                        Intent i = new Intent(context,LoginActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        context.startActivity(i);
                        ((Activity) context).overridePendingTransition(R.anim.slide_enter, R.anim.slide_exit);
                    }else {
                        Log.e("TAG",response.body().toString());
                        responseListener.onFailure("Error");
                    }
                }

                @Override
                public void onFailure(Call<JsonElement> call, Throwable t) {
                    t.printStackTrace();
                }
            });

        }else{
            AlertDialog dialog = new AlertDialog(context,"Network issue",
                    "Check your internet connection");
            dialog.show();
        }
    }
    public void uploadImage(String accesstoken, File imgFile) {
        //Log.e(TAG, "****" + imgFile);
        if (isNetworkAvailable) {
            progressDialog.show();
            //RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), imgFile);
            RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), imgFile);
            MultipartBody.Part uploadfile = MultipartBody.Part.createFormData("image", imgFile.getName(), requestBody);
            RequestBody name = RequestBody.create(MediaType.parse("text/plain"), imgFile.getName());
            Call<JsonElement> upload_req = endpoint.uploadImage(accesstoken, uploadfile, name);
            upload_req.enqueue(new Callback<JsonElement>() {
                @Override
                public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                    Gson gson = new Gson();
                    progressDialog.dismiss();
                    if (response.code() == 200) {
                        responseListener.onSuccess("image uploaded", response.body().toString());
                    } else if (response.code() == 401) {
                        sharedPreferences.clear();
                        Intent i = new Intent(context, LoginActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        context.startActivity(i);
                        ((Activity) context).overridePendingTransition(R.anim.slide_enter, R.anim.slide_exit);
                    } else {
                        responseListener.onFailure("Error");
                    }
                }

                @Override
                public void onFailure(Call<JsonElement> call, Throwable t) {
                    progressDialog.dismiss();
                    t.printStackTrace();
                }
            });
        }else{
            AlertDialog dialog = new AlertDialog(context,"Network issue",
                    "Check your internet connection");
            dialog.show();
        }

    }


}