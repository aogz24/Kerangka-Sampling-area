package com.example.kerangkasamplingarea.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class JadwalResponse {
    @SerializedName("tanggalPendataan")
    private Date tanggalPendataan;

    @SerializedName("segmen")
    private String segmen;

    @SerializedName("bujur")
    private double bujur;

    @SerializedName("lintang")
    private double lintang;

    @SerializedName("nkab")
    private String nkab;

    @SerializedName("nprov")
    private String nprov;

    public Date getTanggalPendataan() {
        return tanggalPendataan;
    }

    public String getSegmen() {
        return segmen;
    }

    public double getBujur() {
        return bujur;
    }

    public double getLintang() {
        return lintang;
    }

    public String getNkab() {
        return nkab;
    }

    public String getNprov() {
        return nprov;
    }
}
