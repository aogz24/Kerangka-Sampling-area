package com.example.kerangkasamplingarea;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kerangkasamplingarea.apiservice.ApiService;
import com.example.kerangkasamplingarea.client.RetrofitClient;
import com.example.kerangkasamplingarea.model.ProfileResponse;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {


    private ImageView profileLogo;
    private TextView idTextView, namaTextView, emailTextView;
    private Button updateProfileButton;
    String accessToken, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        accessToken = getIntent().getStringExtra("accessToken");
        profileLogo = findViewById(R.id.ProfileLogo);
        idTextView = findViewById(R.id.id);
        namaTextView = findViewById(R.id.nama);
        emailTextView = findViewById(R.id.email);
        updateProfileButton = findViewById(R.id.updateprofile);

        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
        Call<ProfileResponse> call = apiService.getProfile("Bearer " +accessToken);

        call.enqueue(new Callback<ProfileResponse>() {
            @Override
            public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {
                if (response.isSuccessful()) {
                    ProfileResponse profile = response.body();
                    idTextView.setText("ID: " + profile.getId());
                    namaTextView.setText("Nama: " + profile.getNama());
                    emailTextView.setText("Email: " + profile.getEmail());
                    email = profile.getEmail();

                } else {
                    String errorMessage = "Gagal mendapatkan profil. Kode: " + response.code();
                    Toast.makeText(ProfileActivity.this, errorMessage, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ProfileResponse> call, Throwable t) {
                String errorMessage = "Terjadi kesalahan: " + t.getMessage();
                Toast.makeText(ProfileActivity.this, errorMessage, Toast.LENGTH_LONG).show();
            }
        });

        Button updateprofile = findViewById(R.id.updateprofile);
        updateprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, UbahProfileActivity.class);
                intent.putExtra("accessToken", accessToken);
                intent.putExtra("email", email);
                startActivity(intent);
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.navigation_home) {
                    Intent homeIntent = new Intent(ProfileActivity.this, MenuActivity.class);
                    homeIntent.putExtra("accessToken", accessToken);
                    startActivity(homeIntent);
                    return true;
                } else if (itemId == R.id.navigation_profile) {
                    Intent profileIntent = new Intent(ProfileActivity.this, ProfileActivity.class);
                    profileIntent.putExtra("accessToken", accessToken);
                    startActivity(profileIntent);
                    return true;
                }
                return false;
            }
        });
    }
}