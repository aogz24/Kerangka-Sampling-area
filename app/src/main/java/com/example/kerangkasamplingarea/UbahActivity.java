package com.example.kerangkasamplingarea;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.kerangkasamplingarea.apiservice.ApiService;
import com.example.kerangkasamplingarea.client.RetrofitClient;
import com.example.kerangkasamplingarea.model.CreateResponse;
import com.example.kerangkasamplingarea.model.PendataanResponseModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UbahActivity extends AppCompatActivity {

    private Button data;
    private EditText urlFoto;
    private Spinner fasetanamSpinner;
    private ApiService apiService;
    private String accessToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah);
        accessToken = getIntent().getStringExtra("accessToken");
        Spinner faseSpinner = findViewById(R.id.fasetanamSpinner);
        String[] faseOptions = {"Fase vegetatif awal", "Fase vegetatif akhir", "Fase generatif awal", "Fase generatif akhir"};
        ArrayAdapter<String> faseAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, faseOptions);
        faseAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        faseSpinner.setAdapter(faseAdapter);

        data = findViewById(R.id.createButton);
        fasetanamSpinner = findViewById(R.id.fasetanamSpinner);
        urlFoto = findViewById(R.id.fotoInput);


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.navigation_home) {
                    Intent homeIntent = new Intent(UbahActivity.this, MenuActivity.class);
                    homeIntent.putExtra("accessToken", "Bearer "+accessToken);
                    startActivity(homeIntent);
                    return true;
                } else if (itemId == R.id.navigation_profile) {
                    Intent profileIntent = new Intent(UbahActivity.this, ProfileActivity.class);
                    profileIntent.putExtra("accessToken", accessToken);
                    startActivity(profileIntent);
                    return true;
                }
                return false;
            }
        });
        data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValid()) {
                    createData();
                } else {
                    Toast.makeText(UbahActivity.this, "Isian tidak boleh kosong.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public boolean isValid() {
        String selectedFase = fasetanamSpinner.getSelectedItem().toString();
        String url = urlFoto.getText().toString();
        return !TextUtils.isEmpty(selectedFase) && !TextUtils.isEmpty(url);
    }

    private void createData() {
        apiService = RetrofitClient.getClient().create(ApiService.class);
        String fase = fasetanamSpinner.getSelectedItem().toString();
        String url = urlFoto.getText().toString();
        JsonObject createRequest = new JsonObject();
        createRequest.addProperty("faseTanam", fase);
        createRequest.addProperty("foto", url);
        createRequest.addProperty("idInfo", 7);

        Call<PendataanResponseModel> call = apiService.ksa("Bearer " +accessToken, createRequest);

        call.enqueue(new Callback<PendataanResponseModel>() {
            @Override
            public void onResponse(Call<PendataanResponseModel> call, Response<PendataanResponseModel> response) {
                try {
                    if (response.isSuccessful()) {
                        PendataanResponseModel result = response.body();
                        Toast.makeText(UbahActivity.this, "Berhasil menambahkan data", Toast.LENGTH_SHORT).show();

                        System.out.println("berhasil menyimpan");
                        Intent intent = new Intent(UbahActivity.this, MenuActivity.class);
                        intent.putExtra("accessToken", accessToken);
                        startActivity(intent);
                        finish();
                    } else {
                        System.out.println("berhasil menyimpan");
                        Toast.makeText(UbahActivity.this, "Berhasil menambahkan data", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(UbahActivity.this, MenuActivity.class);
                        intent.putExtra("accessToken", accessToken);
                        startActivity(intent);
                        finish();
                    }
                } catch (Exception e) {
                    Toast.makeText(UbahActivity.this, "Exception in onResponse: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    System.out.println("Exception in onResponse: " + e.getMessage());
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<PendataanResponseModel> call, Throwable t) {
                Toast.makeText(UbahActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}