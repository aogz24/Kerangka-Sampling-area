package com.example.kerangkasamplingarea;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.kerangkasamplingarea.apiservice.ApiService;
import com.example.kerangkasamplingarea.client.RetrofitClient;
import com.example.kerangkasamplingarea.model.JadwalResponse;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JadwalActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private JadwalAdapter jadwalAdapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jadwal);
        recyclerView = findViewById(R.id.recycleview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        String accessToken = getIntent().getStringExtra("accessToken");

        fetchData(accessToken);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.navigation_home) {
                    Intent homeIntent = new Intent(JadwalActivity.this, MenuActivity.class);
                    homeIntent.putExtra("accessToken", accessToken);
                    startActivity(homeIntent);
                    return true;
                } else if (itemId == R.id.navigation_profile) {
                    Intent profileIntent = new Intent(JadwalActivity.this, ProfileActivity.class);
                    profileIntent.putExtra("accessToken", accessToken);
                    startActivity(profileIntent);
                    return true;
                }
                return false;
            }
        });
    }
    private void fetchData(String token) {
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);

        Call<List<JadwalResponse>> call = apiService.getJadwal("Bearer " + token);
        call.enqueue(new Callback<List<JadwalResponse>>() {
            @Override
            public void onResponse(Call<List<JadwalResponse>> call, Response<List<JadwalResponse>> response) {
                if (response.isSuccessful()) {
                    List<JadwalResponse> jadwalList = response.body();
                    jadwalAdapter = new JadwalAdapter(jadwalList);
                    recyclerView.setAdapter(jadwalAdapter);
                } else {
                    Toast.makeText(JadwalActivity.this, "Error: " + response.message(), Toast.LENGTH_LONG);
                }
            }

            @Override
            public void onFailure(Call<List<JadwalResponse>> call, Throwable t) {
                Toast.makeText(JadwalActivity.this, "Error: " + t.getMessage(),Toast.LENGTH_LONG);
            }
        });
    }
}