package com.example.kerangkasamplingarea.apiservice;

import com.example.kerangkasamplingarea.model.CreateResponse;
import com.example.kerangkasamplingarea.model.JadwalResponse;
import com.example.kerangkasamplingarea.model.LoginResponseModel;
import com.example.kerangkasamplingarea.model.PendataanResponseModel;
import com.example.kerangkasamplingarea.model.ProfileResponse;
import com.example.kerangkasamplingarea.model.RegisterResponseModel;
import com.example.kerangkasamplingarea.model.getProfileResponse;
import com.example.kerangkasamplingarea.model.getProfileResponse2;
import com.google.gson.JsonObject;

import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService {
    @POST("login")
    Call<LoginResponseModel> loginUser(@Body JsonObject loginRequest);

    @POST("register")
    Call<RegisterResponseModel> regisUser(@Body JsonObject loginRequest);

    @GET("ksa/informasiksa/jadwalKsa")
    Call<List<JadwalResponse>> getJadwal(@Header("Authorization") String token);

    @GET("findByToken/profile")
    Call<ProfileResponse> getProfile(@Header("Authorization") String token);

    @POST("ksa/informasiksa/create")
    Call<CreateResponse> createksa(
            @Header("Authorization") String token,
            @Body JsonObject loginRequest
    );

    @POST("ksa/pendataanksa/create")
    Call<PendataanResponseModel> ksa(
            @Header("Authorization") String token,
            @Body JsonObject ksaRequest
    );

    @GET("profile/{email}")
    Call<getProfileResponse> getProfilebyemail(@Path("email") String email);

    @PUT("update/{email}")
    Call<getProfileResponse2> updateUser(
            @Path("email") String email,
            @Body JsonObject updateRequest
    );

}

