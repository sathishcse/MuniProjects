package com.muni.in.model.auth;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sathish on 7/5/18.
 */

public class AuthRequest {
    @SerializedName("username")
    String username;
    @SerializedName("password")
    String password;

    public AuthRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
