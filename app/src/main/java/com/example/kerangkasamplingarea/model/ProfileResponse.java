package com.example.kerangkasamplingarea.model;

import com.google.gson.annotations.SerializedName;

public class ProfileResponse {

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String nama;

    @SerializedName("email")
    private String email;

    @SerializedName("password")
    private String password;

    @SerializedName("role")
    private String role;

    public int getId() {
        return id;
    }

    public String getNama() {
        return nama;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }
}

