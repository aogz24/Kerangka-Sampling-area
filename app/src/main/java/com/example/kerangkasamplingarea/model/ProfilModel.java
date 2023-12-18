package com.example.kerangkasamplingarea.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProfilModel {
    @SerializedName("id")
    private int id;

    @SerializedName("firstName")
    private String firstName;

    @SerializedName("lastName")
    private String lastName;

    @SerializedName("idKab")
    private int idKab;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getIdKab() {
        return idKab;
    }

    public void setIdKab(int idKab) {
        this.idKab = idKab;
    }
}
