package com.example.kerangkasamplingarea.model;


import com.google.gson.annotations.SerializedName;

public class LoginResponseModel {

    @SerializedName("email")
    private String email;
    @SerializedName("accessToken")
    private String accessToken;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

}