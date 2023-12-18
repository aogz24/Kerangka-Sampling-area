package com.example.kerangkasamplingarea.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CreateResponse {

    @SerializedName("id")
    private int id;

    @SerializedName("tanggalpendataan")
    private String tanggalPendataan;

    @SerializedName("lintang")
    private double lintang;

    @SerializedName("bujur")
    private double bujur;

    @SerializedName("segmen")
    private String segmen;

    @SerializedName("kabupaten")
    private Kabupaten kabupaten;

    public int getId() {
        return id;
    }

    public String getTanggalPendataan() {
        return tanggalPendataan;
    }

    public double getLintang() {
        return lintang;
    }

    public double getBujur() {
        return bujur;
    }

    public String getSegmen() {
        return segmen;
    }

    public Kabupaten getKabupaten() {
        return kabupaten;
    }

    public static class Kabupaten {

        @SerializedName("idKab")
        private int idKab;

        @SerializedName("namaKab")
        private String namaKab;

        @SerializedName("userProfileList")
        private List<UserProfile> userProfileList;

        public int getIdKab() {
            return idKab;
        }

        public String getNamaKab() {
            return namaKab;
        }

        public List<UserProfile> getUserProfileList() {
            return userProfileList;
        }
    }

    public static class UserProfile {

        @SerializedName("id")
        private int id;

        @SerializedName("firstName")
        private String firstName;

        @SerializedName("lastName")
        private String lastName;

        @SerializedName("kabupaten")
        private Kabupaten kabupaten;

        public int getId() {
            return id;
        }

        public String getFirstName() {
            return firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public Kabupaten getKabupaten() {
            return kabupaten;
        }
    }
}


