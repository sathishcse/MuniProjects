package com.muni.in.model.auth;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sathish on 7/5/18.
 */

public class AuthResponse {
    @SerializedName("id")
    String Accesstoken;
    @SerializedName("userId")
    int userId;
    @SerializedName("message")
    String message;

    public String getAccesstoken() {
        return Accesstoken;
    }

    public void setAccesstoken(String accesstoken) {
        Accesstoken = accesstoken;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
