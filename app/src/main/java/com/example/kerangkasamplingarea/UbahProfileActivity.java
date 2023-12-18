package com.example.kerangkasamplingarea;

import static android.opengl.ETC1.isValid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kerangkasamplingarea.apiservice.ApiService;
import com.example.kerangkasamplingarea.client.RetrofitClient;
import com.example.kerangkasamplingarea.model.LoginResponseModel;
import com.example.kerangkasamplingarea.model.ProfileResponse;
import com.example.kerangkasamplingarea.model.UserProfile;
import com.example.kerangkasamplingarea.model.getProfileResponse;
import com.example.kerangkasamplingarea.model.getProfileResponse2;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UbahProfileActivity extends AppCompatActivity {

    String email, accessToken;
    EditText fname, lname, idKab;
    Button tombol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah_profile);
        accessToken = getIntent().getStringExtra("accessToken");
        email = getIntent().getStringExtra("email");
        fname = findViewById(R.id.fname);
        lname = findViewById(R.id.lname);
        idKab = findViewById(R.id.idKab);
        tombol = findViewById(R.id.tombolupdate);
        fname = findViewById(R.id.fname);
        lname = findViewById(R.id.lname);
        idKab = findViewById(R.id.idKab);

        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
        Call<getProfileResponse> call = apiService.getProfilebyemail(email);

        call.enqueue(new Callback<getProfileResponse>() {
            @Override
            public void onResponse(Call<getProfileResponse> call, Response<getProfileResponse> response) {
                if (response.isSuccessful()) {
                    getProfileResponse profile = response.body();
                    if (profile.getFirstName() != null) {
                        fname.setText(profile.getFirstName());
                    }
                    if (profile.getLastName() != null) {
                        lname.setText(profile.getLastName());
                    }
                } else {
                    String errorMessage = "Gagal mendapatkan profil. Kode: " + response.code();
                    Toast.makeText(UbahProfileActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<getProfileResponse> call, Throwable t) {
                String errorMessage = "Terjadi kesalahan: " + t.getMessage();
                Toast.makeText(UbahProfileActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });

        tombol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValid()) {
                    updateUser();
                } else {
                    Toast.makeText(UbahProfileActivity.this, "Invalid email address", Toast.LENGTH_SHORT).show();
                }
            }
        });


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.navigation_home) {
                    Intent homeIntent = new Intent(UbahProfileActivity.this, MenuActivity.class);
                    homeIntent.putExtra("accessToken", accessToken);
                    startActivity(homeIntent);
                    return true;
                } else if (itemId == R.id.navigation_profile) {
                    Intent profileIntent = new Intent(UbahProfileActivity.this, ProfileActivity.class);
                    profileIntent.putExtra("accessToken", accessToken);
                    startActivity(profileIntent);
                    return true;
                }
                return false;
            }
        });
    }

    public boolean isValid() {
        String nama1 = fname.getText().toString();
        String nama2 = lname.getText().toString();
        String kab = idKab.getText().toString();
        return !TextUtils.isEmpty(nama1) && !TextUtils.isEmpty(nama2) && !TextUtils.isEmpty(kab);
    }

    private void updateUser() {
        String nama1 = fname.getText().toString();
        String nama2 = lname.getText().toString();
        String kab = idKab .getText().toString();

        JsonObject updateRequest = new JsonObject();
        updateRequest.addProperty("idKab", kab);
        updateRequest.addProperty("firstName", nama1);
        updateRequest.addProperty("lastName", nama2);

        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);

        Call<getProfileResponse2> call = apiService.updateUser(email, updateRequest);

        call.enqueue(new Callback<getProfileResponse2>() {
            @Override
            public void onResponse(Call<getProfileResponse2> call, Response<getProfileResponse2> response) {
                try {
                    if (response.isSuccessful()) {
                        getProfileResponse2 result = response.body();
                        Toast.makeText(UbahProfileActivity.this, "Update Successful", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(UbahProfileActivity.this, MenuActivity.class);
                        intent.putExtra("accessToken", accessToken);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(UbahProfileActivity.this, "Gagal update. Error: " + response.code(), Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(UbahProfileActivity.this, "Exception in onResponse: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<getProfileResponse2> call, Throwable t) {
                Toast.makeText(UbahProfileActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}