package com.muni.in.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sathish on 5/5/2018.
 */

public class User {
    public String getAccesstoken() {
        return accesstoken;
    }

    public void setAccesstoken(String accesstoken) {
        this.accesstoken = accesstoken;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @SerializedName("id")
    String accesstoken;

    @SerializedName("userId")
    String userId;

}
