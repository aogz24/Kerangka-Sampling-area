package com.example.kerangkasamplingarea.model;

import com.google.gson.annotations.SerializedName;

public class getProfileResponse2 {
    @SerializedName("id")
    private int id;

    @SerializedName("firstName")
    private String firstName;

    @SerializedName("lastName")
    private String lastName;

    @SerializedName("idKab")
    private int idKab;

    public int getIdKab() {
        return idKab;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

}
