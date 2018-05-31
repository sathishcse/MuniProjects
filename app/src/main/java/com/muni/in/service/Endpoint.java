package com.muni.in.service;


import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

import com.google.gson.JsonElement;
import com.muni.in.model.restaurant.Restaurant;
import com.muni.in.model.auth.AuthRequest;
import com.muni.in.model.auth.AuthResponse;
import com.muni.in.model.restaurant.requestRestaurant;

import java.util.List;


public interface Endpoint {


    @GET("resturants")
    Call<List<Restaurant>> getRestaurants(@Header("Authorization") String authToken);

    @POST("muniusers/login")
    Call<AuthResponse> authenticate(@Body AuthRequest request);

    @POST("resturants")
    Call<JsonElement> addRestaurant( @Header("Authorization") String authToken, @Body requestRestaurant request);

    @PUT("resturants/{id}")
    Call<JsonElement> updateRestaurant(@Header("Authorization") String authTocken,@Body requestRestaurant request,@Path("id") int id);

    //@GET("resturants/{id}")
    //Call<Restaurant> getRestaurant(@Header("Authorization") String authtoken, @Path("id") int id);

    @Multipart
    @POST("Containers/muniuploads/upload")
    Call<JsonElement> uploadImage(@Header("Authorization") String authtoken, @Part MultipartBody.Part image, @Part("name") RequestBody name);

}