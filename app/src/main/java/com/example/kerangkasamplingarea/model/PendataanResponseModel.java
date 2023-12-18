package com.example.kerangkasamplingarea.model;

import com.google.gson.annotations.SerializedName;

public class PendataanResponseModel {
    @SerializedName("id")
    private int id;

    @SerializedName("faseTanam")
    private String faseTanam;

    @SerializedName("foto")
    private String foto;

    @SerializedName("idInfo")
    private int idInfo;

    public PendataanResponseModel(int id, String faseTanam, String foto, int idInfo) {
        this.id = id;
        this.faseTanam = faseTanam;
        this.foto = foto;
        this.idInfo = idInfo;
    }

    public PendataanResponseModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFaseTanam() {
        return faseTanam;
    }

    public void setFaseTanam(String faseTanam) {
        this.faseTanam = faseTanam;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public int getIdInfo() {
        return idInfo;
    }

    public void setIdInfo(int idInfo) {
        this.idInfo = idInfo;
    }
}
