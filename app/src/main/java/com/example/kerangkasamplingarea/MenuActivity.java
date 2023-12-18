package com.example.kerangkasamplingarea;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        ImageButton tambah = findViewById(R.id.tambahButton);
        ImageButton jadwal = findViewById(R.id.jadwalButton);
        ImageButton ubah = findViewById(R.id.ubahButton);
        ImageButton info = findViewById(R.id.tentangButton);

        String accessToken = getIntent().getStringExtra("accessToken");

        tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, TambahActivity.class);
                intent.putExtra("accessToken", accessToken);
                startActivity(intent);
            }
        });
        jadwal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, JadwalActivity.class);
                intent.putExtra("accessToken", accessToken);
                startActivity(intent);
            }
        });
        ubah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, UbahActivity.class);
                intent.putExtra("accessToken", accessToken);
                startActivity(intent);
            }
        });
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, InfoActivity.class);
                startActivity(intent);
            }
        });


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.navigation_home) {
                    Intent homeIntent = new Intent(MenuActivity.this, MenuActivity.class);
                    homeIntent.putExtra("accessToken", accessToken);
                    startActivity(homeIntent);
                    return true;
                } else if (itemId == R.id.navigation_profile) {
                    Intent profileIntent = new Intent(MenuActivity.this, ProfileActivity.class);
                    profileIntent.putExtra("accessToken", accessToken);
                    startActivity(profileIntent);
                    return true;
                }
                return false;
            }
        });
    }
}